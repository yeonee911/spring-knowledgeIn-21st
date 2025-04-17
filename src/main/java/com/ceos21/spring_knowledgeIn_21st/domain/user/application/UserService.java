package com.ceos21.spring_knowledgeIn_21st.domain.user.application;

import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dto.request.LoginRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dto.request.SignupRequest;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode;
import com.ceos21.spring_knowledgeIn_21st.global.jwt.JwtUtil;
import com.ceos21.spring_knowledgeIn_21st.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional
    public String login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        // 3. 인증 성공 -> UserDetails 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // 4. JWT 토큰 발급
        String token = jwtUtil.createToken(userDetails.getUsername(), userDetails.getRole().name());
        return token;
    }
}