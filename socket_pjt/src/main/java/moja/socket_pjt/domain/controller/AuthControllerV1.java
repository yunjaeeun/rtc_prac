package moja.socket_pjt.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import moja.socket_pjt.domain.model.request.CreateUserRequest;
import moja.socket_pjt.domain.model.request.LoginRequest;
import moja.socket_pjt.domain.model.response.CreateUserResponse;
import moja.socket_pjt.domain.model.response.LoginResponse;
import moja.socket_pjt.domain.service.AuthService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API", description = "V1 Auth API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerV1 {
    private final AuthService authService;

    @Operation(
            summary = "새로운 유저를 생성합니다.",
            description = "새로운 유저 생성"
    )
    @PostMapping("/create-user")
    public CreateUserResponse createUser(
           @RequestBody @Valid CreateUserRequest request
    ) {
        return authService.createUser(request);
    }

    @Operation(
            summary = "로그인 처리",
            description = "로그인을 진행합니다."
    )
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody @Valid LoginRequest request
            ) {
        return authService.login(request);
    }

    @Operation(
            summary = "get user name",
            description = "token을 기반으로 user를 가져옵니다."
    )
    @GetMapping("/get-user-name/{token}")
    public String getUserFromToken(
            @PathVariable("token") String token
    ) {
        return authService.getUserFromToken(token);
    }
}
