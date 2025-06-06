package com.Aptech.userservice.Services.Implement;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Aptech.userservice.Configs.JwtTokenProvider;
import com.Aptech.userservice.Configs.RefreshTokenStore;
import com.Aptech.userservice.Dtos.Request.JwtResponse;
import com.Aptech.userservice.Dtos.Request.LoginRequest;
import com.Aptech.userservice.Dtos.Request.RegisterRequest;
import com.Aptech.userservice.Entitys.UserGlobalRole;
import com.Aptech.userservice.Entitys.Users;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Repositorys.UserGlobalRoleRepository;
import com.Aptech.userservice.Repositorys.UserRepository;
import com.Aptech.userservice.Services.Interfaces.IAuthService;
import com.Aptech.userservice.enums.RoleEnum;
import com.Aptech.userservice.event.KafkaProducerService;
import com.aptech.common.event.user.UserCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final JwtTokenProvider jwt;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final RefreshTokenStore refreshTokenStore;
    private final KafkaProducerService kafkaProducerService;
    private final UserGlobalRoleRepository userGlobalRoleRepository;

    @Override
    public void register(RegisterRequest req) {
        if (userRepo.ExistsByEmail(req.getEmail()) == 1) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        String userId = UUID.randomUUID().toString();
        userRepo.save(Users.builder()
                .userId(userId)
                .userName(req.getUserName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .isActive(true)
                .createdAt(new Date())
                .build());

        userGlobalRoleRepository.save(
                new UserGlobalRole(userId, RoleEnum.GENERAL_USER.getRoleId(), LocalDateTime.now()));

        UserCreatedEvent event = new UserCreatedEvent(
                userId,
                req.getEmail(),
                req.getUserName());
        kafkaProducerService.send("user-events", event);
    }

    @Override
    public JwtResponse login(LoginRequest req) throws Exception {
        Users user = userRepo.FindByEmail(req.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String accessToken = jwt.generateToken(user.getUserId(), 15 * 60 * 1000);
        String refreshToken = jwt.generateToken(user.getUserId(), 7 * 24 * 3600 * 1000);
        refreshTokenStore.save(user.getUserId(), refreshToken);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(String token) throws Exception {
        if (!jwt.validate(token))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        String uid = jwt.getUserIdFromToken(token);
        String saved = refreshTokenStore.get(uid);
        if (!token.equals(saved))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        String newAccess = jwt.generateToken(uid, 15 * 60 * 1000);
        String newRefresh = jwt.generateToken(uid, 7 * 24 * 3600 * 1000);
        refreshTokenStore.save(uid, newRefresh);
        return new JwtResponse(newAccess, newRefresh);
    }

    @Override
    public void logout(String userId) {
        refreshTokenStore.remove(userId);
    }

    @Override
    public Users getProfile(String token) throws Exception {
        String uid = jwt.getUserIdFromToken(token);
        return userRepo.findById(uid).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

}
