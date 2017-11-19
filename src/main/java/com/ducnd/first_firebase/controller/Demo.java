package com.ducnd.first_firebase.controller;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.internal.FirebaseAppStore;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;

@RestController
public class Demo {

    private Storage storage;

    @Autowired
    public Demo() {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            InputStream inputStream = new FileInputStream(new File("image.png"));
            BlobInfo blobInfo =
                    BlobInfo.newBuilder(bucket.getName(), "image.png")
                            .setContentType("image/png")
                            .build();

            File file = new DefaultResourceLoader().getResource("firstproject1994-firebase-adminsdk-qn2kc-66400afe59.json").getFile();
            FileInputStream serviceAccount =
                    new FileInputStream(file);
            storage = StorageOptions.newBuilder()
                    .setProjectId("firstproject1994")
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();
        } catch (IOException e) {
            storage = null;
        }

    }

    @PostMapping(value = "/postImage")
    public String getAppName(
            @RequestParam(value = "image") MultipartFile image
    ) {
        try {
            BlobInfo blobInfo =
                    BlobInfo.newBuilder(StorageClient.getInstance().bucket(),
                            RandomStringUtils.random(30, "1234567890lkasjfhlkasfjalkjdfh34568798alskdjfh796ftasdoa8soifuglajksdbfafaoisdulfkh"))
                            .setContentType(image.getContentType())
                            .build();
            WriteChannel writeChannel = storage.writer(blobInfo);
            byte[] b = new byte[1024];
            InputStream inputStream = image.getInputStream();
            int le = inputStream.read(b);
            while (le >= 0) {
                writeChannel.write(ByteBuffer.wrap(b, 0, le));
                le = inputStream.read(b);
            }
            inputStream.close();
            writeChannel.close();

//            File file1 = new File("hihi.png");
//            FileOutputStream stream = new FileOutputStream(file1);
//            stream.write(
//                    StorageClient.getInstance().bucket().get("image.png").getContent()
//            );
//            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "First Project Firebase";
    }

    @GetMapping(value = "/getImage",  produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(
            @RequestParam(value = "image") String image
    ) {
        return StorageClient.getInstance().bucket().get(image).getContent();
    }
}
