//package com.Aptech.userservice.Services.Implement;
//
//import java.text.ParseException;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.StringJoiner;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import com.Aptech.userservice.Dtos.Request.AuthenticationRequest;
//import com.Aptech.userservice.Dtos.Request.VerifyTokenRequest;
//import com.Aptech.userservice.Dtos.Response.AuthenticationResponse;
//import com.Aptech.userservice.Dtos.Response.RoleResponse;
//import com.Aptech.userservice.Dtos.Response.UserRessponseForLogin;
//import com.Aptech.userservice.Dtos.Response.VerifyTokenResponse;
//import com.Aptech.userservice.Exceptions.AppException;
//import com.Aptech.userservice.Exceptions.ErrorCode;
//import com.Aptech.userservice.Mapper.UserMapper;
//import com.Aptech.userservice.Repositorys.UserRepository;
//import com.Aptech.userservice.Services.Interfaces.AuthenticationService;
//import com.nimbusds.jose.JOSEException;
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@RequiredArgsConstructor
//@Slf4j
//public class AuthenticationServiceImplement implements AuthenticationService {
//
//    UserRepository userRepository;
//    UserMapper userMapper;
//
////    JwtEncoder jwtEncoder;
////    JwtDecoder jwtDecoder;
//
//    @Override
//    public AuthenticationResponse Authenticate(AuthenticationRequest request) {
//        var result = userRepository.getUserByUserName(request.getUsername());
//        if (result == null) {
//            throw new AppException(ErrorCode.USER_NOT_EXISTED);
//        }
//        UserRessponseForLogin user = userMapper.toUserResponseForLogin(result);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
//        boolean authen = passwordEncoder.matches(request.getPassword(), result.getPassword());
//        if (!authen) {
//            new AppException(ErrorCode.UNAUTHENTICATED);
//        }
//        var token = generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(token)
//                .login(authen)
//                .build();
//    }
//
//    @Override
//    public VerifyTokenResponse VerifyToken(VerifyTokenRequest request) throws JOSEException, ParseException {
//        try {
//            String token = request.getToken();
//            Jwt decodedJwt = jwtDecoder.decode(token);
//            Date expirationTime = Date.from(decodedJwt.getExpiresAt());
//
//            boolean isValid = expirationTime.after(new Date());
//
//            return VerifyTokenResponse.builder().valid(isValid).build();
//        } catch (JwtException e) {
//            return VerifyTokenResponse.builder().valid(false).build();
//        }
//    }
//
//    private String generateToken(UserRessponseForLogin userRessponseForLogin) {
//        Instant now = Instant.now();
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .subject(userRessponseForLogin.getUserName())
//                .issuer("workwave.com")
//                .issuedAt(now)
//                .expiresAt(now.plus(1, ChronoUnit.HOURS))
//                .claim("scope", builScope(userRessponseForLogin))
//                .build();
//        log.info("scope:{}", builScope(userRessponseForLogin));
//        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }
//
//    private String builScope(UserRessponseForLogin userResponse) {
//        StringJoiner stringJoiner = new StringJoiner(" ");
//
//        if (!CollectionUtils.isEmpty(userResponse.getRoles())) {
//            userResponse.getRoles()
//                    .stream()
//                    .map(RoleResponse::getRoleName)
//                    .forEach(stringJoiner::add);
//        }
//
//        return stringJoiner.toString();
//    }
//
//}
