package moja.socket_pjt.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moja.socket_pjt.common.exception.CustomException;
import moja.socket_pjt.common.exception.ErrorCode;
import moja.socket_pjt.domain.model.request.CreateUserRequest;
import moja.socket_pjt.domain.model.request.LoginRequest;
import moja.socket_pjt.domain.model.response.CreateUserResponse;
import moja.socket_pjt.domain.model.response.LoginResponse;
import moja.socket_pjt.domain.repository.UserRepository;
import moja.socket_pjt.domain.repository.entity.User;
import moja.socket_pjt.domain.repository.entity.UserCredentials;
import moja.socket_pjt.security.Hasher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final Hasher hasher;

    @Transactional(transactionManager = "createUserTransactionManager")
    public CreateUserResponse createUser(CreateUserRequest request) {
        Optional<User> user = userRepository.findByName(request.name());

        if (user.isPresent()) {
            log.error("USER_ALREADY_EXISTS : {}", request.name());
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }
         try {
             User newUser = this.newUser(request.name());
             UserCredentials newCredentials = this.newUserCredentials(request.password(), newUser);
             newUser.setCredentials(newCredentials);

             User savedUser = this.userRepository.save(newUser);

             if (savedUser == null) {
                throw new CustomException(ErrorCode.USER_SAVED_FAILED);
             }

         } catch (Exception e) {
             throw new CustomException(ErrorCode.USER_SAVED_FAILED);
         }

        return new CreateUserResponse(request.name());
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> user = userRepository.findByName(request.name());

        if (user.isPresent()) {
            log.error("USER_NOT_EXISTS : {}", request.name());
            throw new CustomException(ErrorCode.USER_NOT_EXISTS);
        }

        user.map(u -> {
            String hashedValue = hasher.getHashingValue(request.password());

            if (!u.getCredentials().getHashedPassword().equals(hashedValue)) {

            }
        })

        return new LoginResponse(ErrorCode.SUCCESS, "Token");
    }



    private User newUser(String name) {
        User newUser = User.builder()
                .name(name)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();

        return newUser;
    }

    private UserCredentials newUserCredentials(String password, User user) {

        String hashedValue = hasher.getHashingValue(password);

        UserCredentials cre = UserCredentials
                .builder()
                .user(user)
                .hashedPassword(hashedValue)
                .build();

        return cre;
    }
}


