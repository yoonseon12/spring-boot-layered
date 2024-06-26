package io.study.springbootlayered.api.member.application;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.study.springbootlayered.api.member.domain.dto.MemberSigninDto;
import io.study.springbootlayered.web.jwt.JwtProvider;
import io.study.springbootlayered.web.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSigninService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public MemberSigninDto.Info signin(final MemberSigninDto.Command request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);
        TokenInfo token = jwtProvider.createToken(authentication);

        return new MemberSigninDto.Info(token.getAccessToken(), token.getRefreshToken());
    }
}
