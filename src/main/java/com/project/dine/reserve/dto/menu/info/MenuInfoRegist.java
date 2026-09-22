package com.project.dine.reserve.dto.menu.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoRegist {
    private UUID menuCategoryUUID;
    private String menuInfoName;
    private int menuInfoPrice;
    private String menuInfoDescription;
    private boolean menuInfoOrder;
    private boolean menuInfoVisible;

    private MultipartFile menuInfoImg;
}
