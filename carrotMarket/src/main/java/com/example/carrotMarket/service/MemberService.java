package com.example.carrotMarket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.carrotMarket.dto.MemberResDto;
import com.example.carrotMarket.dto.UpdateProfileReqDto;
import com.example.carrotMarket.entity.member.Member;
import com.example.carrotMarket.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public MemberResDto getMyProfile(Long id) {
		Member findMember = memberRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
		);

		return new MemberResDto(findMember);
	}

	@Transactional
	public void updateMyProfile(Long id, UpdateProfileReqDto reqDto) {
		Member findMember = memberRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
		);

		findMember.updateProfile(reqDto.getName(), reqDto.getNickName(), reqDto.getPhoneNum(), reqDto.getAddress(),
			reqDto.getLatitude(), reqDto.getLongitude());
	}

	public MemberResDto findByKakaoId(Long kakaoId) {
		Member findMember = memberRepository.findByKakaoId(kakaoId).orElseThrow(
				() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
		);
		return new MemberResDto(findMember);
	}

}
