package com.project.dine.reserve.service.member;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.member.DineReserveGuest;
import com.project.dine.reserve.dto.constant.error.GuestErrorCode;
import com.project.dine.reserve.repository.member.DineReserveGuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final DineReserveGuestRepository dineReserveGuestRepository;

    public DineReserveGuest getGuest(UUID guestUUID) {
        return dineReserveGuestRepository.findByGuestUUID(guestUUID)
                .orElseThrow(() -> new DineReserveException(GuestErrorCode.NO_GUEST));
    }
}
