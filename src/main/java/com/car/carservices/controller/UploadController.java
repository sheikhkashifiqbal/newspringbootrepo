package com.car.carservices.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/upload")
public class UploadController {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file,
                                    @RequestParam(value = "filename", required = false) String filename) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "file is required"));
            }
            String finalName = (filename == null || filename.isBlank())
                    ? addTimestamp(Objects.requireNonNull(file.getOriginalFilename()))
                    : filename;

            Path dir = Paths.get("src/main/resources/static/images");
            Files.createDirectories(dir);

            try (InputStream in = file.getInputStream()) {
                Files.copy(in, dir.resolve(finalName), StandardCopyOption.REPLACE_EXISTING);
            }
            return ResponseEntity.ok(Map.of("filename", finalName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    private String addTimestamp(String original) {
        int dot = original.lastIndexOf('.');
        String base = dot > -1 ? original.substring(0, dot) : original;
        String ext  = dot > -1 ? original.substring(dot) : "";
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        return base + "-" + ts + ext;
    }
}
