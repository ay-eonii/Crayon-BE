package com.user.presentation;

import com.user.application.usecase.UserManageUsecase;
import com.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.user.application.dto.response.UserResponseDTO.Response;
import static com.user.presentation.constant.ResponseMessage.SUCCESS_LOGIN;
import static com.user.presentation.constant.ResponseMessage.SUCCESS_REISSUE_TOKEN;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ManagerController {

    private final UserManageUsecase userManageUsecase;

    @PostMapping(value = "/login/{code}")
    @Operation(summary = "카카오 로그인 및 회원가입")
    public ResponseDto<Response> authenticate(@PathVariable String code) {
        Response response = userManageUsecase.authenticate(code);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 리프레시 API")
    public ResponseDto<Response> reissue(@RequestHeader("Authorization-refresh") String refreshToken) {
        Response response = userManageUsecase.reissueToken(refreshToken);

        return ResponseDto.of(OK.value(), SUCCESS_REISSUE_TOKEN.getMessage(), response);
    }
}
