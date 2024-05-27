package com.npt.anis.ANIS.IdPhoto.service;

import com.npt.anis.ANIS.IdPhoto.entity.IdPhoto;
import com.npt.anis.ANIS.IdPhoto.repository.IdPhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdPhotoServiceImpl implements IdPhotoService {
    private final IdPhotoRepository idPhotoRepository;
    private static final String UPLOAD_DIR = System.getProperty("user.home") + "/db/anis/";
    @Override
    public IdPhoto saveImage(String studentID, MultipartFile file) throws IOException {
        // 저장할 디렉토리 설정
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 저장
//        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(studentID + ".png");
        Files.copy(file.getInputStream(), filePath);

        // 이미지 엔티티 저장
        IdPhoto image = new IdPhoto();
        image.setName(studentID);
        image.setPath(filePath.toString());
        return idPhotoRepository.save(image);
    }

    public byte[] getImage(String studentId) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(studentId + ".png");
        return Files.readAllBytes(filePath);
    }

    public IdPhoto getImageMetadata(String studentId) {
        return idPhotoRepository.findByName(studentId);
    }
}
