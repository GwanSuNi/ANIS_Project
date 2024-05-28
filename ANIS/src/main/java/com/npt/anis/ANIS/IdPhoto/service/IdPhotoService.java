package com.npt.anis.ANIS.IdPhoto.service;
import com.npt.anis.ANIS.IdPhoto.entity.IdPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

public interface IdPhotoService {
    // 사진 경로 저장
    IdPhoto saveImage(String studentId, MultipartFile file) throws IOException;

    // 사진 조회
    byte[] getImage(String studentId) throws IOException;

    // 사진 메타데이터 조회
    IdPhoto getImageMetadata(String studentId);

    // 사진 삭제
    void deleteImage(String studentId);

    // 사진 변경
    void updateImage(String studentId, MultipartFile file) throws IOException;
}
