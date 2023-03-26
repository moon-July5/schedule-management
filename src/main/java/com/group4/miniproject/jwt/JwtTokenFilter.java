package com.group4.miniproject.jwt;

import com.group4.miniproject.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Request 이전에 1회 작동할 필터
public class JwtTokenFilter extends OncePerRequestFilter {
  private JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    String token = jwtTokenProvider.resolveToken(request);
    try {
      if (token != null && jwtTokenProvider.validateAccessToken(token)) {
        Authentication auth = jwtTokenProvider.getAuthenticationByAccessToken(token);
        SecurityContextHolder.getContext().setAuthentication(auth); // 정상 토큰이면 SecurityContext에 저장
      }
    } catch (CustomException e) {
      SecurityContextHolder.clearContext();
      response.sendError(e.getHttpStatus().value(), e.getMessage());
      return;
    }

    filterChain.doFilter(request, response);
  }
}