package vn.nguyenanhtuan.eventapp.service;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.dto.request.AuthenticateRequest;
import vn.nguyenanhtuan.eventapp.dto.request.IntrospectRequest;
import vn.nguyenanhtuan.eventapp.dto.response.AuthenticateResponse;
import vn.nguyenanhtuan.eventapp.dto.response.IntrospectResponse;
import vn.nguyenanhtuan.eventapp.entity.User;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.reposiroty.InvalidTokenRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.UserRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {
    UserRepository userRepository;
    InvalidTokenRepository invalidTokenRepository;
    @NonFinal
    @Value("${jwt.secretKey}")
    private String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        var user = userRepository.findByEmail(request.getEmail());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new GlobalException(ErrorCode.UNAUTHENTICATED);


        String token = generateToken(user);

        return AuthenticateResponse.builder()
                .token(token)
                .success(true)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isCheck = true;

        try {
            verify(token);// xác thực không phải là refresh
        } catch (GlobalException appException) {
            isCheck = false;
        }

        return IntrospectResponse.builder()
                .verify(isCheck)
                .build();
    }

    public SignedJWT verify(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean isValid = signedJWT.verify(verifier);

        Date expireDate = signedJWT.getJWTClaimsSet().getExpirationTime();


        String id_token = signedJWT.getJWTClaimsSet().getJWTID();

        if (!isValid || (expireDate.before(new Date())))
            throw new GlobalException(ErrorCode.UNAUTHENTICATED);

        if (invalidTokenRepository.existsById(id_token)) {
            log.info("invalidated token");
            throw new GlobalException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("anh-tuan")
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRole().getId())
                .claim("uid", user.getId())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

}


