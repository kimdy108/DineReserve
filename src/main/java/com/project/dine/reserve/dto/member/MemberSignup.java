package com.project.dine.reserve.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignup {
    private String memberID;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
}
