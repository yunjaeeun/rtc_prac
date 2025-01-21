package moja.socket_pjt.domain.user.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import moja.socket_pjt.common.exception.ErrorCode;

import java.util.List;

@Schema(description = "User 검색 리스트")
public record UserSearchResponse(
        @Schema(description = "error code")
        ErrorCode description,

        @Schema(description = "이름")
        List<String> name
) {}