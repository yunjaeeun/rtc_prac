package moja.socket_pjt.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moja.socket_pjt.common.exception.ErrorCode;
import moja.socket_pjt.domain.repository.UserRepository;
import moja.socket_pjt.domain.user.model.response.UserSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceV1 {

    private final UserRepository userRepository;

    public UserSearchResponse searchUser(String name, String user) {
        List<String> names = userRepository.findNameByNameMatch(name, user);

        return new UserSearchResponse(ErrorCode.SUCCESS, names);
    }
}
