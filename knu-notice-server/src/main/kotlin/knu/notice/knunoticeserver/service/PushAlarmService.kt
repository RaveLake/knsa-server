package knu.notice.knunoticeserver.service

import com.google.api.core.ApiFuture
import com.google.api.core.ApiFutureCallback
import com.google.api.core.ApiFutures
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.*
import knu.notice.knunoticeserver.domain.PushStatus
import knu.notice.knunoticeserver.repository.CategoryRepository
import knu.notice.knunoticeserver.repository.DeviceRepository
import knu.notice.knunoticeserver.repository.NoticeRepository
import knu.notice.knunoticeserver.repository.PushStatusRepository
import knu.notice.knunoticeserver.util.getLogger
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.math.max

@Service
class PushAlarmService(
    private val noticeRepository: NoticeRepository,
    private val deviceRepository: DeviceRepository,
    private val pushStatusRepository: PushStatusRepository,
    private val categoryRepository: CategoryRepository,
    private val firebaseApp: FirebaseApp,
    private val firebaseAppExecutor: ListeningExecutorService,
) {

    companion object {
        val logger = getLogger()
    }

    private val fcm = FirebaseMessaging.getInstance(firebaseApp)

    @Async
    fun sendMessage() {
        val categoryList = categoryRepository.findAll()
        val mappingName = hashMapOf<String, String>()
        // 학과 code와 이름을 매핑하기 위한 HashMap
        for (category in categoryList) {
            mappingName[category.code] = category.department
        }
        // PushStatus에서 마지막으로 보낸 공지사항의 번호를 가져온다.
        val prevStatus = pushStatusRepository.getById(1)
        // 이후에 새로 들어온 모든 공지를 가져온다.
        val newNoticeList = noticeRepository.getAllByIdGreaterThan(prevStatus.noticeId)
        // 그룹 별로 묶어서 탐색한다.
        val noticeGroup = newNoticeList.groupBy { notice -> notice.code }
        val firebaseMessaging = FirebaseMessaging.getInstance()
        var lastNoticeId = 0L

        for (key in noticeGroup.keys) {
            // 일반 공지로 보내야 될 디바이스 정보를 저장한다.
            val sendUserIdList = mutableListOf<String>()
            // 키워드 공지로 보내야 될 디바이스 정보를 저장한다.
            val keywordDeviceList = mutableListOf<Message>()
            // 현재 공지사항의 code를 구독한 사람들의 정보를 가져온다.
            val subscribeDeviceList = deviceRepository.getAllBySubscriptionsLike(key)
            for (notice in noticeGroup[key]!!) {
                lastNoticeId = max(lastNoticeId, notice.id)
                next@ for (device in subscribeDeviceList) {
                    // 키워드가 없는 디바이스나 키워드 알림을 꺼놓은 디바이스는 스킵한다.
                    if (!device.alarmSwitchKey || device.keywords == "")
                        continue

                    val keywords = device.keywords.split("+", " ")
                    for (keyword in keywords) {
                        // 키워드가 있으면 키워드 알림을 보내고 다음 기기로 넘어간다.
                        if (notice.title.contains(keyword)) {
                            logger.info("PushAlarm: add keyword id ${device.id}")
                            keywordDeviceList.add(
                                Message.builder().setNotification(
                                    Notification(
                                        "설정된 키워드를 가진 공지가 올라왔어요!",
                                        "${mappingName[key]}: $keyword"
                                    )
                                ).setToken(device.id).build()
                            )
                            continue@next
                        }
                    }

                    // 키워드를 다 찾아도 없는 디바이스는 일반 알림 공지 보내도록 id를 list에 추가한다.
                    if (device.alarmSwitchSub) {
                        logger.info("PushAlarm: add subscription id ${device.id}")
                        sendUserIdList.add(device.id)
                    }
                }
            }

            // 리스트에 추가된 id들을 multicast로 한 번에 알림을 보낸다.
            if (sendUserIdList.isNotEmpty()) {
                val subscriptionResponse = fcm.sendMulticastAsync(
                    MulticastMessage.builder().setNotification(Notification("새 공지가 올라왔어요!", mappingName[key]))
                        .addAllTokens(sendUserIdList).build()
                )
                addCallback(subscriptionResponse)
            }
            if (keywordDeviceList.isNotEmpty()) {
                val keywordResponse = fcm.sendAllAsync(keywordDeviceList)
                addCallback(keywordResponse)
            }
        }

        // 가장 마지막 공지를 PushStatus에 다시 저장한다.
        pushStatusRepository.save(PushStatus(1, lastNoticeId, LocalDateTime.now()))
    }

    fun addCallback(response: ApiFuture<BatchResponse>) {
        ApiFutures.addCallback(response, object : ApiFutureCallback<BatchResponse> {
            override fun onFailure(t: Throwable) {
                logger.error("ERROR: ${t.message}")
            }

            override fun onSuccess(result: BatchResponse) {
                for (responseMessage in result.responses) {
                    if (responseMessage.isSuccessful)
                        logger.info("INFO: success send message ${responseMessage.messageId}")
                    else
                        logger.info("INFO: fail send message ${responseMessage.messageId}")
                }
            }

        }, firebaseAppExecutor)
    }
}