package com.example.moduleapi.response;

import com.example.modulecommon.enums.CodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private String resultCode;
    private String resultMessage;
    private T info;


    public CommonResponse(CodeEnum codeEnum) {
        setResultCode(codeEnum.getCode());
        setResultMessage(codeEnum.getMessage());
    }

    public CommonResponse(T info) {
        setResultCode(CodeEnum.SUCCESS.getCode());
        setResultMessage(CodeEnum.SUCCESS.getMessage());
        setInfo(info);
    }

    public CommonResponse(CodeEnum codeEnum, T info) {
        setResultCode(codeEnum.getCode());
        setResultMessage(codeEnum.getMessage());
        setInfo(info);
    }
}
