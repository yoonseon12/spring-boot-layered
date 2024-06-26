package io.study.springbootlayered.api.member.domain.validation;

import org.springframework.stereotype.Component;

import io.study.springbootlayered.api.member.domain.dto.MemberSignupDto;
import io.study.springbootlayered.api.member.domain.repository.MemberQueryRepository;
import io.study.springbootlayered.web.exception.ApiException;
import io.study.springbootlayered.web.exception.error.MemberErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberQueryRepository memberQueryRepository;

    public void signinValidate(final MemberSignupDto.Command request) {
        validateDuplicateNickname(request.getNickname());
        validationDuplicateEmail(request.getEmail());
    }

    private void validateDuplicateNickname(final String nickname) {
        if (memberQueryRepository.existsByNickname(nickname)) {
            throw new ApiException(MemberErrorCode.CONFLICT_DUPLICATE_NICKNAME);
        }
    }

    private void validationDuplicateEmail(final String email) {
        if (memberQueryRepository.existsByEmail(email)) {
            throw new ApiException(MemberErrorCode.CONFLICT_DUPLICATE_EMAIL);
        }
    }
}
