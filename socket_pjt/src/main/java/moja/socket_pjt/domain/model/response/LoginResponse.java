package moja.socket_pjt.domain.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import moja.socket_pjt.common.exception.ErrorCode;

@Schema(description = "로그인 응답")
public record LoginResponse(
        @Schema(description = "error code")
        ErrorCode description,

        @Schema(description = "token")
        String token
) {}