package com.example.zipify.controller;

import com.example.zipify.service.UnArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {

    @Autowired
    private UnArchiveService unArchiveService;


    @PostMapping("/unarchive")
    public void unarchive(@RequestBody MultipartFile multipartFile) {
        unArchiveService.importDataFromDump(multipartFile, "unarchive");
    }


}
