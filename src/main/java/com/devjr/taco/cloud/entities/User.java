package com.devjr.taco.cloud.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.devjr.taco.cloud.security.SecurityConfig;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true) //AccessLevel.PRIVATE
public class User implements UserDetails{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 4, max = 50, message = "Username must be at least 4 characters long")
    private final String username;
    @NotNull
    //@Size(min = 6, max = 50, message = "Password must be at least 6 characters long")
    private final String password;
    @NotNull
    @Size(min = 3, message = "Fullname must be at least 3 characters long")
    private final String fullname;
    @NotBlank(message = "Street is required")
    private final String street;
    @NotBlank(message = "City is required")
    private final String city;
    @NotBlank(message = "State is required")
    private final String state;
    @NotBlank(message = "Zip code is required")
    private final String zip;
    private final String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Arrays.asList(new SimpleGrantedAuthority(SecurityConfig.ERole.ROLE_USER.toString()));
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

}
