package com.project.dine.reserve.service.component;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.system.DineReserveFile;
import com.project.dine.reserve.dto.constant.error.SystemErrorCode;
import com.project.dine.reserve.repository.system.DineReserveFileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final LoggerService loggerService;

    private final DineReserveFileRepository dineReserveFileRepository;

    @Value("${dinereserve.filePath}")
    private String filePath;

    public DineReserveFile insertFile(MultipartFile multipartFile, String pathName, String fileUsage) {
        if (multipartFile.isEmpty()) return null;

        String fullPathName = filePath + "/" + pathName + "/" + fileUsage;
        File dirInfo = new File(fullPathName);

        if (!dirInfo.exists()) dirInfo.mkdirs();

        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + "." + extension;

        try {
            File saveFile = new File(fullPathName + "/" + saveFileName);
            multipartFile.transferTo(saveFile);
        } catch (Exception e) {
            loggerService.writeLogger("error", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        DineReserveFile dineReserveFile = DineReserveFile.create(uuid, multipartFile.getOriginalFilename(), saveFileName, multipartFile.getSize(), multipartFile.getContentType(), extension, pathName, fileUsage);
        dineReserveFileRepository.save(dineReserveFile);

        return dineReserveFile;
    }

    public DineReserveFile updateFile(UUID fileUUID, MultipartFile multipartFile, String pathName, String fileUsage) {
        deleteFile(fileUUID);
        return insertFile(multipartFile, pathName, fileUsage);
    }

    public void deleteFile(UUID fileUUID) {
        DineReserveFile dineReserveFile = dineReserveFileRepository.findByFileUUID(fileUUID)
                .orElseThrow(() -> new DineReserveException(SystemErrorCode.NO_FILE));

        try {
            Path path = Paths.get(filePath + "/" + dineReserveFile.getFilePath() + "/" + dineReserveFile.getFileUsage() + "/" + dineReserveFile.getFileSaveName());
            Files.delete(path);
            dineReserveFileRepository.delete(dineReserveFile);
        } catch (Exception e) {
            loggerService.writeLogger("error", "FILE NOT FOUND : " + e.getMessage());
        }
    }
}
