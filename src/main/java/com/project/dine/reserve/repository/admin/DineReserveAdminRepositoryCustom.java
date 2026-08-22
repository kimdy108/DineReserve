package com.project.dine.reserve.repository.admin;

import com.project.dine.reserve.dto.auth.admin.AdminList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DineReserveAdminRepositoryCustom {
    Page<AdminList> findAdminListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable);
}
