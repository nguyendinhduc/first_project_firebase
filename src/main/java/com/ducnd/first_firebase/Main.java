package com.ducnd.first_firebase;

import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.cloud.StorageClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Main implements ServletContextListener {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            File file = new DefaultResourceLoader().getResource("firstproject1994-firebase-adminsdk-qn2kc-66400afe59.json").getFile();
            FileInputStream serviceAccount =
                    new FileInputStream(file);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://firstproject1994.firebaseio.com")
                    .setStorageBucket("firstproject1994.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);

            serviceAccount.close();
            System.out.println("ahihi ok");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
