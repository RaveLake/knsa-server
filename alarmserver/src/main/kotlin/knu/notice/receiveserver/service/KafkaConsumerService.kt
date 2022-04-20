package knu.notice.receiveserver.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import knu.notice.receiveserver.domain.MessageInfo
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService(
    firebaseApp: FirebaseApp,
) {

    private val fcm = FirebaseMessaging.getInstance(firebaseApp)
    private val objectMapper = ObjectMapper()

    @KafkaListener(topics = ["alarm"], groupId = "alarm")
    fun receiveAlarm(message: String) {
        val jsonMessage = objectMapper.readValue<MessageInfo>(message)
        val sendMessage = Message.builder().setNotification(Notification(jsonMessage.title, jsonMessage.body))
            .setToken(jsonMessage.id).build()
        val response = fcm.send(sendMessage)
    }
}