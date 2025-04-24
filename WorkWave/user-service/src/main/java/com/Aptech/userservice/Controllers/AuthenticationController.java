// package com.Aptech.userservice.Controllers;

// import java.text.ParseException;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.Aptech.userservice.Dtos.Request.AuthenticationRequest;
// import com.Aptech.userservice.Dtos.Request.VerifyTokenRequest;
// import com.Aptech.userservice.Dtos.Response.ApiResponse;
// import com.Aptech.userservice.Dtos.Response.AuthenticationResponse;
// import com.Aptech.userservice.Dtos.Response.VerifyTokenResponse;
// import com.Aptech.userservice.Services.Interfaces.AuthenticationService;
// import com.nimbusds.jose.JOSEException;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;

// @RestController
// @RequestMapping("/auth")
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class AuthenticationController {
// AuthenticationService authenticationService;

// @PostMapping("/login")
// public ApiResponse<AuthenticationResponse> login(@RequestBody
// AuthenticationRequest request) {
// AuthenticationResponse response =
// authenticationService.Authenticate(request);
// return ApiResponse.<AuthenticationResponse>builder()
// .status("SUCCESS")
// .data(response)
// .build();
// }

// @PostMapping("/verifytoken")
// public ApiResponse<VerifyTokenResponse> verifyToken(@RequestBody
// VerifyTokenRequest request)
// throws JOSEException, ParseException {
// VerifyTokenResponse response = authenticationService.VerifyToken(request);
// return ApiResponse.<VerifyTokenResponse>builder()
// .status("SUCCESS")
// .data(response)
// .build();
// }
// }
