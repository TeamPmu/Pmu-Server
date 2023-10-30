package org.pmu.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.pmu.domain.user.dto.request.UserReissueRequestDto;
import org.pmu.domain.user.dto.request.UserSignUpRequestDto;
import org.pmu.domain.user.dto.response.UserAuthResponseDto;
import org.pmu.domain.user.service.AuthService;
import org.pmu.global.common.BaseResponse;
import org.pmu.global.common.SuccessCode;
import org.pmu.global.common.UserId;
import org.pmu.global.config.jwt.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@Controller
public class AuthApiController {
    private final AuthService authService;

    @GetMapping("/signin")
    public ResponseEntity<BaseResponse<?>> signUp(@RequestHeader("Authorization") final String token) {
        final UserAuthResponseDto userAuthResponseDto = authService.signIn(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.of(SuccessCode.OK, userAuthResponseDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<?>> signUp(@RequestHeader("Authorization") final String token,
                                                  @RequestBody final UserSignUpRequestDto userSignUpRequestDto) {
        final UserAuthResponseDto userAuthResponseDto = authService.signUp(token, userSignUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.of(SuccessCode.CREATED, userAuthResponseDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<?>> reissue(@RequestHeader("Authorization") final String refreshToken,
                                                   @RequestBody final UserReissueRequestDto userReissueRequestDto) {
        final Token issuedToken = authService.reissue(refreshToken, userReissueRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.of(SuccessCode.OK, issuedToken));
    }

    @PatchMapping("/signout")
    public ResponseEntity<BaseResponse<?>> signOut(@UserId final Long userId) {
        authService.signOut(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.of(SuccessCode.OK, null));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<BaseResponse<?>> withdraw(@UserId final Long userId) {
        authService.withdraw(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.of(SuccessCode.OK, null));
    }
}
