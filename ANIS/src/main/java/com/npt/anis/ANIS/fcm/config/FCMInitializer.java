package com.npt.anis.ANIS.fcm.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FCMInitializer {
    @Value("${fcm.firebase_config_path}")
    private String firebaseConfigPath;

    @PostConstruct // 빈 객체가 생성되고 의존성 주입이 완료된 후에 초기화가 실행될 수 있도록 @PostConstruct 어노테이션을 사용
    public void initialize() throws IOException {
        ClassPathResource resource = new ClassPathResource(firebaseConfigPath);

        try (InputStream is = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    // 공식문서였지만 구식인듯?
//    @PostConstruct
//    public void initialize() {
//        try {
//            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(googleCredentials)
//                    .build();
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);
//                log.info("Firebase application has been initialized");
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
}
