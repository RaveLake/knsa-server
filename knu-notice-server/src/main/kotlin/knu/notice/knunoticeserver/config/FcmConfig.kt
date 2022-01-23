package knu.notice.knunoticeserver.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import knu.notice.knunoticeserver.domain.FireBaseKeyPath
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FcmConfig {
    @Bean
    fun firebaseApp(): FirebaseApp {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(ClassPathResource(FireBaseKeyPath).inputStream)).build()
        return FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseAppExecutor(): ListeningExecutorService = MoreExecutors.newDirectExecutorService()
}