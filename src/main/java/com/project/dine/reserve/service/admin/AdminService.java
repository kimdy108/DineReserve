package com.project.dine.reserve.service.admin;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.admin.DineReserveAdmin;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.admin.*;
import com.project.dine.reserve.dto.constant.admin.AdminRole;
import com.project.dine.reserve.dto.constant.error.AdminErrorCode;
import com.project.dine.reserve.dto.constant.error.AuthErrorCode;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.repository.admin.DineReserveAdminRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final DineReserveAdminRepository dineReserveAdminRepository;
    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;

    @Transactional
    public void adminRegist(AdminRegist adminRegist) {
        dineReserveAdminRepository.findByAdminID(adminRegist.getAdminID()).ifPresent(a -> {
            throw new DineReserveException(AdminErrorCode.EXIST_ID);
        });

        DineReserveStoreInfo dineReserveStoreInfo = null;
        if (!AdminRole.ADMIN.equals(adminRegist.getAdminRole())) {
            dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(adminRegist.getStoreUUID())
                    .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));
        }

        DineReserveAdmin dineReserveAdmin = DineReserveAdmin.create(adminRegist, passwordEncoder.encode(adminRegist.getAdminPassword()), dineReserveStoreInfo);
        dineReserveAdminRepository.save(dineReserveAdmin);
    }

    @Transactional
    public void adminUpdate(AdminUpdate adminUpdate) {
        DineReserveAdmin dineReserveAdmin = dineReserveAdminRepository.findByAdminUUID(adminUpdate.getAdminUUID())
                .orElseThrow(() -> new DineReserveException(AdminErrorCode.NO_ADMIN));

        dineReserveAdmin.update(adminUpdate);
    }

    @Transactional
    public void adminDelete(UUID adminUUID) {
        DineReserveAdmin dineReserveAdmin = dineReserveAdminRepository.findByAdminUUID(adminUUID)
                .orElseThrow(() -> new DineReserveException(AdminErrorCode.NO_ADMIN));

        dineReserveAdmin.updateUseFlag(false);
    }

    @Transactional
    public void adminUpdatePassword(AdminUpdatePassword adminUpdatePassword) {
        DineReserveAdmin dineReserveAdmin = dineReserveAdminRepository.findByAdminUUID(adminUpdatePassword.getAdminUUID())
                .orElseThrow(() -> new DineReserveException(AdminErrorCode.NO_ADMIN));

        if (!passwordEncoder.matches(adminUpdatePassword.getOldPassword(), dineReserveAdmin.getAdminPassword())) throw new DineReserveException(AuthErrorCode.NOT_MATCH_PASSWORD);

        dineReserveAdmin.updatePassword(passwordEncoder.encode(adminUpdatePassword.getNewPassword()));
    }

    public Page<AdminList> adminListPage(String searchType, String searchValue, Long offset, int limit) {
        if (searchType == null) searchType = "";
        if (searchValue == null) searchValue = "";

        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return dineReserveAdminRepository.findAdminListPage(searchType, searchValue, offset, limit, pageable);
    }

    public AdminInfo adminInfo(UUID adminUUID) {
        DineReserveAdmin dineReserveAdmin = dineReserveAdminRepository.findByAdminUUID(adminUUID)
                .orElseThrow(() -> new DineReserveException(AdminErrorCode.NO_ADMIN));

        return AdminInfo.create(dineReserveAdmin);
    }
}
