package com.Aptech.bugtrackingservice.Configs;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

@Component
public class RsaKeyUtil {

    // @Value("${jwt.private-key}")
    // private Resource privateKeyResource;

    @Value("${jwt.public-key}")
    private Resource publicKeyResource;

    // public RSAPrivateKey getPrivateKey() throws Exception {
    // String key = readKeyFromResource(privateKeyResource);
    // return (RSAPrivateKey) KeyFactory.getInstance("RSA")
    // .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
    // }

    public RSAPublicKey getPublicKey() throws Exception {
        String key = readKeyFromResource(publicKeyResource);
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
    }

    private String readKeyFromResource(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
        }
    }

}
