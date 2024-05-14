package com.example.zipify.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MongoRestoreService {

    public void restore(String filePath, String databaseName, String collectionName) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "mongorestore",
                "--db", databaseName,
                "--collection", collectionName,
                "--drop", // Drop existing collection before restore
                filePath);

        Process process = processBuilder.start();
        int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while waiting for mongorestore process", e);
        }

        if (exitCode != 0) {
            throw new RuntimeException("mongorestore process exited with non-zero status: " + exitCode);
        }
    }

}
