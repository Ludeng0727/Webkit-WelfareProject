package webkit.welfare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try {
            // 요청 정보에서 토큰 값 파싱
            String token = parserBearerToken(request);

            if(token != null && !token.equalsIgnoreCase("null")) {
                // 토큰 값으로 유저 찾기
                String userId = tokenProvider.validateAndGetUserId(token);

                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
                        AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }

        } catch (Exception e) {
            throw new IOException("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parserBearerToken(HttpServletRequest request) {
        // 요청 정보 중 토큰 정보 가져오기 ("Baerer xxxxxx.....")
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && !bearerToken.startsWith("Bearer ")) {
            bearerToken = "Bearer ".concat(bearerToken);
        }

        // 가져온 토큰 정보 필터링 후 반환
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
}
