package com.project.dine.reserve.service.admin;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.admin.DineReserveAdmin;
import com.project.dine.reserve.dto.auth.admin.*;
import com.project.dine.reserve.dto.constant.admin.AdminRole;
import com.project.dine.reserve.dto.constant.error.AdminErrorCode;
import com.project.dine.reserve.dto.constant.error.AuthErrorCode;
import com.project.dine.reserve.repository.admin.DineReserveAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.project.dine.reserve.util.Common.EMPTY_SEQ;
import static com.project.dine.reserve.util.Common.EMPTY_UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final DineReserveAdminRepository dineReserveAdminRepository;

    @Transactional
    public void adminRegist(AdminRegist adminRegist) {
        dineReserveAdminRepository.findByAdminID(adminRegist.getAdminID()).ifPresent(a -> {
            throw new DineReserveException(AdminErrorCode.EXIST_ID);
        });

        Long storeSeq = EMPTY_SEQ;
        UUID storeUUID = EMPTY_UUID;
        if (!AdminRole.ADMIN.equals(adminRegist.getAdminRole())) {
            // todo 매장 찾기..
        }

        DineReserveAdmin dineReserveAdmin = DineReserveAdmin.create(adminRegist, passwordEncoder.encode(adminRegist.getAdminPassword()), storeSeq, storeUUID);
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
