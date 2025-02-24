package com.attendance.oauth.service;
import com.attendance.entity.Professor;
import com.attendance.entity.Student;
import com.attendance.entity.User;
import com.attendance.oauth.jwt.JwtService;
import com.attendance.oauth.model.dto.AuthenticationRequest;
import com.attendance.oauth.model.dto.AuthenticationResponse;
import com.attendance.oauth.model.dto.RegisterRequest;
import com.attendance.oauth.model.entity.Token;
import com.attendance.oauth.repository.TokenRepository;
import com.attendance.repository.ProfessorRepository;
import com.attendance.repository.StudentRepository;
import com.attendance.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        var user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = new User();
        if(request.getRa() != null){

            Student student = studentRepository.findByRa(request.getRa())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            student.getUsername(),
                            request.getPassword()
                    )
            );

            user.setId(student.getId());
            user.setUsername(student.getRa());
            user.setPassword(student.getPassword());
            user.setRole(student.getRole());
        }else if(request.getUsername() != null){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            user = repository.findByUsername(request.getUsername())
                    .orElseThrow();

        }else if (request.getEmail() != null){
            User userSaved;
            Student student = studentRepository.findByEmail(request.getEmail())
                    .orElse(null);

            if(student == null){
                Professor professor = professorRepository.findByEmail(request.getEmail())
                        .orElse(null);
                userSaved = professor;
            }else{
                userSaved = student;
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userSaved.getUsername(),
                            request.getPassword()
                    )
            );

            user = userSaved;
//            user.setId(userSaved.getId());
//            user.setUsername(userSaved.getUsername());
//            user.setPassword(userSaved.getPassword());
//            user.setRole(userSaved.getRole());
        }

        Student student;

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            var user = this.repository.findByUsername(userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), null);
            }
        }
    }
}