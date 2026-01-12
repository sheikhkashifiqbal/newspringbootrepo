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

    // Works on Azure Linux App Service (persistent)
    private static final String UPLOAD_BASE_DIR =
            Optional.ofNullable(System.getenv("UPLOAD_DIR")).orElse("/home/uploads/images");

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

            Path dir = Paths.get(UPLOAD_BASE_DIR);
            Files.createDirectories(dir);

            try (InputStream in = file.getInputStream()) {
                Files.copy(in, dir.resolve(finalName), StandardCopyOption.REPLACE_EXISTING);
            }

            // Frontend should use: {BASE_URL}/images/{filename}
            return ResponseEntity.ok(Map.of(
                    "filename", finalName,
                    "url", "/images/" + finalName
            ));
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
