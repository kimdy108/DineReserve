package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.dto.store.info.StoreInfoList;
import com.project.dine.reserve.dto.store.info.StoreInfoListAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DineReserveStoreInfoRepositoryCustom {
    Page<StoreInfoList> findStoreInfoListPage(String searchType, String searchValue, String categoryUUID, String storeStatus, Long offset, int limit, Pageable pageable);

    List<StoreInfoListAll> findStoreInfoListAll();
}
