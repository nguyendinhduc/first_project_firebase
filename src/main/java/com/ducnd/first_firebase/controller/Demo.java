package com.ducnd.first_firebase.controller;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.internal.FirebaseAppStore;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.ByteBuffer;

@RestController
public class Demo {

    @GetMapping("/")
    public String getAppName() {
        Bucket bucket = StorageClient.getInstance().bucket();
        try {
            InputStream inputStream = new FileInputStream(new File("image.png"));
            BlobInfo blobInfo =
                    BlobInfo.newBuilder(bucket.getName(), "image.png")
                            .setContentType("image/png")
                            .build();

            File file = new DefaultResourceLoader().getResource("firstproject1994-firebase-adminsdk-qn2kc-66400afe59.json").getFile();
            FileInputStream serviceAccount =
                    new FileInputStream(file);
            Storage storage = StorageOptions.newBuilder()
                    .setProjectId("firstproject1994")
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();
            WriteChannel writeChannel = storage.writer(blobInfo);
            byte[] b = new byte[1024];
            int le = inputStream.read(b);
            while (le >= 0) {
                writeChannel.write(ByteBuffer.wrap(b, 0, le));
                le = inputStream.read(b);
            }
            inputStream.close();
            writeChannel.close();
            writeChannel.close();

            File file1 = new File("hihi.png");
            FileOutputStream stream = new FileOutputStream(file1);
            stream.write(
                    StorageClient.getInstance().bucket().get("image.png").getContent()
            );
            stream.close();
            System.out.println(
                    "entry: " + StorageClient.getInstance().bucket().listAcls().get(0).getEntity().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "First Project Firebase";
    }
}
