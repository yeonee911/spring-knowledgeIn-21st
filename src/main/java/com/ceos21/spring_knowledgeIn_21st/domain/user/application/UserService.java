package com.ceos21.spring_knowledgeIn_21st.domain.user.application;

import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.auth.dto.request.SigninRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.auth.dto.request.SignupRequest;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.jwt.JwtUtil;
import com.ceos21.spring_knowledgeIn_21st.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode.DUPLICATE_EMAIL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public void register(SignupRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }
        userRepository.save(request.toEntity(passwordEncoder));
    }
}