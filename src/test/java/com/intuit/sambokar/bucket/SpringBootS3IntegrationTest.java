package com.intuit.sambokar.bucket;

import com.amazonaws.util.IOUtils;
import com.intuit.sambokar.bucket.controller.BucketController;
import com.intuit.sambokar.bucket.service.AmazonClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootS3bucketApplication.class)
public class SpringBootS3IntegrationTest {
    @Autowired
    private BucketController bucketController;

    @Autowired
    private AmazonClient amazonClient;

    @Test
    public void whenImageIsUploaded_thenGetBackUrl() {
        File file = new File("src/test/resources/homer.jpg");
        MultipartFile multipartFile = null;
        try {
            FileInputStream input = new FileInputStream(file);
            multipartFile = new MockMultipartFile("file",
                    file.getName(), "text/plain", IOUtils.toByteArray(input));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.when(amazonClient.uploadFile(multipartFile)).thenReturn("http://someurl.com");
        String testUrl = bucketController.uploadFile(multipartFile);
        Assert.assertEquals("http://someurl.com", testUrl);
    }


    @Test
    public void whenGetImage_thenGetBackJpeg() {
        File file = new File("src/test/resources/homer.jpg");
        InputStream input = null;
        byte[] image = null;
        byte[] result = null;
        try {
            input = new FileInputStream(file);
            image = IOUtils.toByteArray(input);
            Mockito.when(amazonClient.getFileAsByteArrayFromS3Bucket("someurl")).thenReturn(image);
            result = bucketController.getFile("someurl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(Arrays.equals(image, result));
    }
}