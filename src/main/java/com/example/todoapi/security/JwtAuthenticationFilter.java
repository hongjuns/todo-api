package com.example.todoapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 리퀘스트에서 토큰 가져오기.
            String token = parseBearerToken(request);
            log.info("Filter is running...");
            // 토큰 검사하기. JWT이므로 인가 서버에 요청 하지 않고도 검증 가능.
            if (token != null && !token.equalsIgnoreCase("null")) {
                // userId 가져오기. 위조 된 경우 예외 처리 된다.
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID : " + userId );
                // 인증 완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }else{
                String path = request.getServletPath();
                if(!path.contains("auth")) {
                    log.warn("couldn't find bearer string, will ignore the header");
                    throw new JwtException("token not found");
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting username from token", e);
            throw new JwtException("유효하지 않은 토큰");
        } catch (ExpiredJwtException e) {
            logger.warn("the token is expired and not valid anymore", e);
            throw new JwtException("토큰 기한 만료");
        } catch (Exception e){
            logger.error("Could not set user authentication in security context" + e.getMessage());
            throw new JwtException("Exception Error");
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken (HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return  null;
    }

}
