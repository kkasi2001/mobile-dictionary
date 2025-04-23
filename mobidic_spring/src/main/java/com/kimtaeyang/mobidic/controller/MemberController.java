package com.kimtaeyang.mobidic.controller;

import com.kimtaeyang.mobidic.dto.*;
import com.kimtaeyang.mobidic.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.kimtaeyang.mobidic.code.GeneralResponseCode.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
@Tag(name = "사용자 관련 서비스", description = "닉네임 및 패스워드 변경, 회원탈퇴 등")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/detail")
    public ResponseEntity<GeneralResponse<MemberDto>> getMemberDetail(
            @RequestParam @Valid String uId
    ) {
        return GeneralResponse.toResponseEntity(OK,
                memberService.getMemberDetailById(UUID.fromString(uId)));
    }

    @PatchMapping("/nckchn/{memberId}")
    public ResponseEntity<GeneralResponse<UpdateNicknameDto.Response>> updateMemberNickname(
            @PathVariable String memberId,
            @RequestBody @Valid UpdateNicknameDto.Request request
    ) {
        return GeneralResponse.toResponseEntity(OK,
                memberService.updateMemberNickname(UUID.fromString(memberId), request));
    }

    @PatchMapping("/pschn/{memberId}")
    public ResponseEntity<GeneralResponse<UpdatePasswordDto.Response>> updateMemberPassword(
            @PathVariable String memberId,
            @RequestBody @Valid UpdatePasswordDto.Request request,
            HttpServletRequest httpServletRequest
    ){
        String token = httpServletRequest.getHeader("Authorization").substring(7);

        return GeneralResponse.toResponseEntity(OK,
                memberService.updateMemberPassword(UUID.fromString(memberId), request, token));
    }

    @PatchMapping("/withdraw/{memberId}")
    public ResponseEntity<GeneralResponse<WithdrawMemberDto.Response>> withdrawMember(
            @PathVariable String memberId,
            HttpServletRequest request
    ){
        String token = request.getHeader("Authorization").substring(7);

        return GeneralResponse.toResponseEntity(OK,
                memberService.withdrawMember(token, UUID.fromString(memberId)));
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<GeneralResponse<WithdrawMemberDto.Response>> deleteMember(
            @PathVariable String memberId,
            HttpServletRequest servletRequest
    ){
        String token = servletRequest.getHeader("Authorization").substring(7);

        return GeneralResponse.toResponseEntity(OK,
                memberService.deleteMember(token, UUID.fromString(memberId)));
    }
}
