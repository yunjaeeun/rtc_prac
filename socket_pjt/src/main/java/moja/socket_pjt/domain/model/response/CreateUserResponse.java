package moja.socket_pjt.domain.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "유저 생성 응답")
public record CreateUserResponse(
        @Schema(description = "성공 유무")
        @NotBlank
        @NotNull
        String code

) {}