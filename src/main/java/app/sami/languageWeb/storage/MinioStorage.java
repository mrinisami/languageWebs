package app.sami.languageWeb.storage;

import app.sami.languageWeb.error.exceptions.MinioPutUriException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@AllArgsConstructor
public class MinioStorage implements Storage{


    private String bucketName;


    private MinioClient minioClient;

    @Override
    public String getUploadPresignedUrl(String name) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .method(Method.PUT)
                    .object(name)
                            .expiry(1, TimeUnit.DAYS)
                    .build());
        } catch (Exception e) {
            throw new MinioPutUriException();
        }
    }

    @Override
    public String getDeletePresignedUrl(String name){
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .method(Method.DELETE)
                    .object(name)
                    .expiry(30, TimeUnit.MINUTES)
                    .build());
        } catch (Exception e) {
            throw new MinioPutUriException();
        }
    }
    @Override
    public String getDownloadPresignedUrl(String name){
        try{
            Map<String, String> reqParams = new HashMap<String, String>();
            reqParams.put("response-content-disposition", "attachment");
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(name)
                    .method(Method.GET)
                            .expiry(30, TimeUnit.MINUTES)
                            .extraQueryParams(reqParams)
                    .build());
        }
        catch (Exception e){
            throw new MinioPutUriException();
        }
    }
}
