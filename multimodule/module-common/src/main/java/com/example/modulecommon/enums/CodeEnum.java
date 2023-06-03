package com.example.modulecommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS("200", "success"),
    UNKNOWN_ERROR("9999", "unknow error");

    private final String code;
    private final String message;
}
