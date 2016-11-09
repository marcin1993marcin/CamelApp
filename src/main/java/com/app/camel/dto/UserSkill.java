package com.app.camel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class UserSkill {
    private Integer skillId;
    private Integer userId;
    private Integer level;
    private String note;
}
