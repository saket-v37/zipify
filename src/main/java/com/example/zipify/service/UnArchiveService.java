package com.example.zipify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.undercouch.bson4jackson.BsonFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnArchiveService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void importDataFromDump(MultipartFile multipartFile, String collectionName) {
        try {
            File file = new File("src/main/resources/targetFile.tmp");
            try (OutputStream os = new FileOutputStream(file)) {
                os.write(multipartFile.getBytes());
            }
            List<Document> documents = readBsonDump(file);
            mongoTemplate.getCollection(collectionName).insertMany(documents);
            // todo: check : tmp remove
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private List<Document> readBsonDump(File file) throws IOException {
        List<Document> documents = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper(new BsonFactory());
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            while (fileInputStream.available() > 0) {
                Document document = objectMapper.readValue(fileInputStream, Document.class);
                documents.add(document);
            }
        }
        return documents;
    }
}
