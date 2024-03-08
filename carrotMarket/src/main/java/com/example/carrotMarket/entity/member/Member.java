package com.example.carrotMarket.entity.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member implements UserDetails {
    @Id@GeneratedValue
    private Long id;
    private String name;
    private String nickName;
    private String phoneNum;
    private String address;
    private Integer latitude;
    private Integer longitude;
    private String loginId;
    private Long kakaoId;

    public Member(Long id, String name, String nickName, String phoneNum, String address, Integer latitude,
        Integer longitude, String loginId, Long kakaoId) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.loginId = loginId;
        this.kakaoId = kakaoId;
    }

    public void updateProfile(String name, String nickName, String phoneNum, String address, Integer latitude, Integer longitude) {
        this.name = name;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
