package moja.socket_pjt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import moja.socket_pjt.common.constants.Constants;
import moja.socket_pjt.common.exception.CustomException;
import moja.socket_pjt.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component
public class JWTProvider {
    private static String secretKey;
    private static String refreshSecretKey;
    private static long tokenTimeForMinute;
    private static long refreshTokenTimeForMinute;

    @Value("${token.secret-key}")
    public void setSecretKey(String secretKey) {
        JWTProvider.secretKey = secretKey;
    }

    @Value("${token.refresh-secret-key}")
    public void setRefreshSecretKey(String refreshSecretKey) {
        JWTProvider.refreshSecretKey = refreshSecretKey;
    }

    @Value("${token.token-time}")
    public void setTokenTime(long tokenTime) {
        JWTProvider.tokenTimeForMinute = tokenTime;
    }

    @Value("${token.refresh-token-time}")
    public void setRefreshTokenTime(long refreshTokenTime) {
        JWTProvider.tokenTimeForMinute = refreshTokenTime;
    }

    public static String createToken(String name) {
        return JWT.create()
                .withSubject(name)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenTimeForMinute * Constants.ON_MINUTE_TO_MILLIS))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public static String createRefreshToken(String name) {
        return JWT.create()
                .withSubject(name)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenTimeForMinute * Constants.ON_MINUTE_TO_MILLIS))
                .sign(Algorithm.HMAC256(refreshSecretKey));
    }

    public static DecodedJWT checkTokenForRefresh(String token) {
        try {
            DecodedJWT decoded = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            log.error("token must be expired : {}", decoded.getSubject());
            throw new CustomException(ErrorCode.ACCESS_TOKEN_IS_NOT_EXPIRED);
        } catch (AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
            throw new CustomException(ErrorCode.TOKEN_IS_INVALID);
        } catch (TokenExpiredException e) {
            return JWT.decode(token);
        }
    }

    public static DecodedJWT decodeAccessToken(String token) {
        return decodeTokenAfterVerify(token, secretKey);
    }

    public static DecodedJWT decodeRefreshToken(String token) {
        return decodeTokenAfterVerify(token, refreshSecretKey);
    }

    private static DecodedJWT decodeTokenAfterVerify(String token, String key) {
        try {
            return JWT.require(Algorithm.HMAC256(key)).build().verify(token);
        } catch (AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
            throw new CustomException(ErrorCode.TOKEN_IS_INVALID);
        }catch (TokenExpiredException e) {
            throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
        }
    }

    public static DecodedJWT decodedJWT(String token) {
        return JWT.decode(token);
    }

    public static String extractToken(String header) {
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }else {
            throw new IllegalArgumentException("Invalid Auth Header");
        }
    }

    public static String getUserFromToken(String token) {
        DecodedJWT jwt = decodedJWT(token);
        return jwt.getSubject();
    }

}
