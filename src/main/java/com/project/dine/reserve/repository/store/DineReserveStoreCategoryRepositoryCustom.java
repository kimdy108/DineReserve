package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.dto.store.category.StoreCategoryList;
import com.project.dine.reserve.dto.store.category.StoreCategoryListAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DineReserveStoreCategoryRepositoryCustom {
    Page<StoreCategoryList> findStoreCategoryListPage(String categoryName, Long offset, int limit, Pageable pageable);

    List<StoreCategoryListAll> findStoreCategoryListAll();
}
