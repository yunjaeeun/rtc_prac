package moja.socket_pjt.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moja.socket_pjt.domain.user.model.response.UserSearchResponse;
import moja.socket_pjt.domain.user.service.UserServiceV1;
import moja.socket_pjt.security.JWTProvider;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "V1 User API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserControllerV1 {

    private final UserServiceV1 userServiceV1;

    public UserSearchResponse searchUser(
            @PathVariable("name") String name,
            @RequestHeader("Authorization") String authString
    ) {
        String token = JWTProvider.extractToken(authString);
        String user = JWTProvider.getUserFromToken(token);

        return userServiceV1.searchUser(name, user);
    }
}
