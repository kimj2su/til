package com.example.demo.domain.auth.model.response;

import com.example.demo.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 생성 response")
public record LoginResponse(
    @Schema(description = "error code")
    ErrorCode description,

    @Schema(description = "jwt token")
    String token
) {} 
