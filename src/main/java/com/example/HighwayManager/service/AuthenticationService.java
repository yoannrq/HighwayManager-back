package com.example.HighwayManager.service;

import com.example.HighwayManager.config.CookieConfig;
import com.example.HighwayManager.dto.AuthRequestDTO;
import com.example.HighwayManager.dto.AuthResponseDTO;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final CookieConfig cookieConfig;

    public AuthResponseDTO authenticate(AuthRequestDTO request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou mot de passe incorrect"));


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email ou mot de passe incorrect");
        }

        // Token creation
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        // Cookie creation and configuration
        Cookie jwtCookie = new Cookie(cookieConfig.getCookieName(), token);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(cookieConfig.getMaxAge() / 1000);
        jwtCookie.setHttpOnly(cookieConfig.isSecureAndHttpOnly());
        jwtCookie.setSecure(cookieConfig.isSecureAndHttpOnly());

        response.addCookie(jwtCookie);

        return new AuthResponseDTO(user.getEmail(), user.getRole().getName());
    }

    public void logout(HttpServletResponse response) {
        // Creating an expired cookie to overwrite the existing cookie
        Cookie cookie = new Cookie(cookieConfig.getCookieName(), "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(cookieConfig.isSecureAndHttpOnly());
        cookie.setSecure(cookieConfig.isSecureAndHttpOnly());

        response.addCookie(cookie);
    }
}