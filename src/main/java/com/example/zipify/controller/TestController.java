package com.example.zipify.controller;

import com.example.zipify.service.MongoRestoreService;
import com.example.zipify.service.UnArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    private UnArchiveService unArchiveService;

    @Autowired
    private MongoRestoreService mongoRestoreService;


    @PostMapping("/unarchive")
    public void unarchive(@RequestBody MultipartFile multipartFile) {
        unArchiveService.importDataFromDump(multipartFile, "unarchive");
    }


    @PostMapping("/restore")
    public ResponseEntity<String> restoreBsonFile(@RequestParam("filePath") String filePath,
                                                  @RequestParam("databaseName") String databaseName,
                                                  @RequestParam("collectionName") String collectionName) {
        try {
            // databaseName: core
            // collectionName: quantitySchemaDto
            //filePath: src/main/resources/quantitySchemaDto.bson
            mongoRestoreService.restore(filePath, databaseName, collectionName);
            return ResponseEntity.ok("Restore operation completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to restore BSON file.");
        }
    }


}
