package com.example.carrotMarket.controller;

import com.example.carrotMarket.dto.MemberResDto;
import com.example.carrotMarket.entity.member.Member;
import com.example.carrotMarket.service.KakaoService;
import com.example.carrotMarket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.example.carrotMarket.util.MemberInfo.securityLoginWithoutLoginForm;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoController {

    private final KakaoService ks;
    private final MemberService memberService;

    /**
     * 카카오 로그인을 통해 code를 query string으로 받아오면, 코드를 통해 토큰, 토큰을 통해 사용자 정보를 얻어와 db에 해당 사용자가 존재하는지 여부를
     * 파악해 존재할 때는 로그인, 없을 땐 회원가입 페이지로 넘어가게 해줌.
     */
//    @GetMapping("/login/kakao")
//    public ResponseEntity<Object> getCI(@RequestParam String code, HttpServletRequest request) throws IOException {
//        log.info("code = " + code);
//
//        // 액세스 토큰과 유저정보 받기
//        String access_token = ks.getToken(code);
//        Map<String, Object> userInfo = ks.getUserInfo(access_token);
//
//        log.info("userInfo = {}", userInfo.values());
//
//        Long kakaoId = Long.parseLong((String) userInfo.get("id"));
//        MemberResDto member = memberService.findByKakaoId(kakaoId);
//
//        log.info("kakaoId = {}", kakaoId);
//
//
//    }


}
