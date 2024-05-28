package com.npt.anis.ANIS.IdPhoto.controller;

import com.npt.anis.ANIS.IdPhoto.entity.IdPhoto;
import com.npt.anis.ANIS.IdPhoto.service.IdPhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final IdPhotoService idPhotoService;

    // 이미지 저장
    // 어드민이 학생의 사진을 업로드할 때 사용
    @PostMapping("/upload")
    public ResponseEntity<IdPhoto> uploadImage(@RequestParam("studentId") String studentId, @RequestParam("file") MultipartFile file) {
        try {
            log.info("file: {}", file.getOriginalFilename());
            IdPhoto image = idPhotoService.saveImage(studentId, file);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 이미지 조회
    @GetMapping("/{studentId}")
    public ResponseEntity<IdPhoto> getImageMetadata(@PathVariable("studentId") String studentId) {
        IdPhoto image = idPhotoService.getImageMetadata(studentId);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    // 이미지 다운로드
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable("fileName") String fileName) {
        try {
            byte[] data = idPhotoService.getImage(fileName);
            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 이미지 삭제
    @DeleteMapping("/{studentId}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("studentId") String studentId) {
        try {
            idPhotoService.deleteImage(studentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 이미지 수정
    @PutMapping("/")
    public ResponseEntity<HttpStatus> updateImage(@RequestParam("studentId") String studentId, @RequestParam("file") MultipartFile file) {
        try {
            idPhotoService.updateImage(studentId, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
