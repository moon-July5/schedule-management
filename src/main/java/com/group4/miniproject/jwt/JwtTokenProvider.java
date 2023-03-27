package com.group4.miniproject.jwt;

import com.group4.miniproject.dto.PrincipalDto;
import com.group4.miniproject.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

// 유저 정보로 JWT 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
  @Value("${jwt.token.access-token-secret-key}")
  private String access_token_secret_key;

  @Value("${jwt.token.access-token-expire-length}")
  private long access_token_expire_time;

  private final UserDetailsService userDetailsService;

  /**
   * 적절한 설정을 통해 Access 토큰을 생성하여 반환
   * @param authentication
   * @return access token
   */
  public String generateAccessToken(Authentication authentication) {
    Claims claims = Jwts.claims().setSubject(authentication.getName());
    //PrincipalDto principalDto = (PrincipalDto) authentication.getPrincipal();
//    claims.put("auth", appUserRoles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));
//    claims.put("auth", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","))); // jwt키에 role추가시 사용. 문자열(콤마구분)
//    claims.put("auth", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())); // jwt키에 role추가시 사용. 배열형태
//    System.out.println("-----------------------------");
//    System.out.println(authentication.getName());
//    System.out.println("-----------------------------");

    Date now = new Date();
    Date expiresIn = new Date(now.getTime() + access_token_expire_time);

    return Jwts.builder()
        .setClaims(claims)
            //.setSubject(principalDto.getAccountId())
        .setIssuedAt(now)
        .setExpiration(expiresIn)
        .signWith(SignatureAlgorithm.HS256, access_token_secret_key)
        .compact();
  }

  /**
   * Access 토큰으로부터 클레임을 만들고, 이를 통해 UserAccount 객체를 생성하여 Authentication 객체를 반환
   * @param access_token
   * @return
   */
  public Authentication getAuthenticationByAccessToken(String access_token) {
    String userPrincipal = Jwts.parser().setSigningKey(access_token_secret_key).parseClaimsJws(access_token).getBody().getSubject();
    PrincipalDto userDetails = (PrincipalDto) userDetailsService.loadUserByUsername(userPrincipal);
    
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /**
   * http 헤더로부터 bearer 토큰을 가져옴.
   * @param req
   * @return
   */
  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  /**
   * Access 토큰을 검증
   * @param token
   * @return
   */
  public boolean validateAccessToken(String token) {
    try {
      Jwts.parser().setSigningKey(access_token_secret_key).parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
      throw new CustomException("Error on Access Token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
