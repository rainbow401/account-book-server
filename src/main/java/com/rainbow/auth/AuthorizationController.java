package com.rainbow.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rainbow.auth.dto.LoginDTO;
import com.rainbow.config.AuthProperties;
import com.rainbow.service.TokenStoreService;
import com.rainbow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthProperties authProperties;

    private final TokenStoreService tokenStoreService;

    private final UserService userService;

    @PostMapping("/login")
    public Boolean login(LoginDTO dto) {
        return userService.login(dto);
    }

    @GetMapping("/check")
    public Boolean checkToken(@RequestHeader("Authorization") String token) {
        Algorithm algorithm = Algorithm.HMAC256(authProperties.getSecret()); //use more secure key
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(authProperties.getIssuer())
                .build();
        DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
        return true;
    }
}
