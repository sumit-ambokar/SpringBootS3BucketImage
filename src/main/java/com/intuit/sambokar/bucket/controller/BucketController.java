package com.intuit.sambokar.bucket.controller;

import com.amazonaws.util.IOUtils;
import com.intuit.sambokar.bucket.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/storage/")
public class  BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @GetMapping(value = "/getFile", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getFile(@RequestParam String fileName) throws IOException {
        InputStream in = this.amazonClient.getFileFromCloudFront(fileName);
        if (in == null) in = this.amazonClient.getFileFromS3Bucket(fileName);
        return IOUtils.toByteArray(in);
    }

    @GetMapping("/getAllFileNames")
    public ArrayList<Map<String,String>> getAllFileNames() {
        ArrayList<String> fileNamesList =  this.amazonClient.getAllImageNames();
        ArrayList<Map<String,String>> result = new ArrayList<>();
        for (String fName : fileNamesList){
            HashMap<String, String> map = new HashMap<>();
            map.put("id", fName);
            result.add(map);
        }
        return result;
    }


    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestParam(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }



}
