package com.Aptech.userservice.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.core.io.Resource;

@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENPOINTS = { "/auth/login", "/auth/verifytoken", "/user/createuser" };

    @Value("${jwt.private-key}")
    private Resource privateKeyResource;

    @Value("${jwt.public-key}")
    private Resource publicKeyResource;

    @SuppressWarnings({ "deprecation", "removal" })
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests(
//                request -> request.requestMatchers(PUBLIC_ENPOINTS).permitAll()
//                        // .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
//                        .anyRequest().authenticated());
//
//        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
//                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                .authenticationEntryPoint(new JwtAuthenticationEntrypoint()));
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        return httpSecurity.build();
//    }

//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedConverter.setAuthorityPrefix("ROLE_");
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedConverter);
//        return jwtConverter;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder() {
//        RSAKey rsaKey = new RSAKey.Builder(getPublicKey()).privateKey(getPrivateKey()).build();
//        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
//    }
//
//    private RSAPublicKey getPublicKey() {
//        try {
//            byte[] keyBytes = readKey(publicKeyResource);
//            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//            return (RSAPublicKey) kf.generatePublic(spec);
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi khi tải Public Key", e);
//        }
//    }
//
//    private RSAPrivateKey getPrivateKey() {
//        try {
//            byte[] keyBytes = readKey(privateKeyResource);
//            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//            return (RSAPrivateKey) kf.generatePrivate(spec);
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi khi tải Private Key", e);
//        }
//    }
//
//    private byte[] readKey(Resource resource) throws IOException {
//        return Base64.getDecoder().decode(new String(Files.readAllBytes(Paths.get(resource.getURI())))
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replace("-----END PRIVATE KEY-----", "")
//                .replaceAll("\\s", ""));
//    }
}
