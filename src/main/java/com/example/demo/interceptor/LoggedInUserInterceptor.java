package com.example.demo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggedInUserInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoggedInUserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getJwtToken(request);
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("jwtTest")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            logger.info("login:{}", jwt.getClaim("login").asString());
            logger.info("name:{}", jwt.getClaim("name").asString());
            User user = new User();
            user.setLogin(jwt.getClaim("login").asString());
            user.setName(jwt.getClaim("name").asString());
            request.setAttribute("user", user);
            return true;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new RuntimeException(exception);
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization.startsWith("Bearer")) {
            return authorization.substring("Bearer".length()).trim();
        }
        throw new RuntimeException("토큰 없음");
    }

}
