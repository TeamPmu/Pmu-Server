package org.pmu.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.pmu.domain.user.dto.request.UserSignUpRequestDto;
import org.pmu.domain.user.dto.response.UserAuthResponseDto;
import org.pmu.domain.user.service.AuthService;
import org.pmu.global.common.BaseResponse;
import org.pmu.global.common.SuccessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@Controller
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<?>> signUp(@RequestHeader("Authorization") final String token,
                                                  @RequestBody final UserSignUpRequestDto userSignUpRequestDto) {
        final UserAuthResponseDto userAuthResponseDto = authService.signUp(token, userSignUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.of(SuccessCode.CREATED, userAuthResponseDto));
    }
}
