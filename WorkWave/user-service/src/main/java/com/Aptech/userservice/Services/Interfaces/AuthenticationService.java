package com.Aptech.userservice.Services.Interfaces;

import java.text.ParseException;

import com.Aptech.userservice.Dtos.Request.AuthenticationRequest;
import com.Aptech.userservice.Dtos.Request.VerifyTokenRequest;
import com.Aptech.userservice.Dtos.Response.AuthenticationResponse;
import com.Aptech.userservice.Dtos.Response.VerifyTokenResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    AuthenticationResponse Authenticate(AuthenticationRequest request);

    VerifyTokenResponse VerifyToken(VerifyTokenRequest request) throws JOSEException, ParseException;
}
