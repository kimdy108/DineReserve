package com.project.dine.reserve.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdate {
    private UUID memberUUID;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String memberDescription;
}
