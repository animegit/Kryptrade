package com.Animesh.Kryptrade.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.List;

public class JWTTokenvalidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt= request.getHeader(JWTConstant.JWT_HEADER);
        if(jwt!=null){
            jwt=jwt.substring(7);
            try{

                SecretKey key= Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());
                Claims claims=Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> grantedAuthorities= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication auth=new UsernamePasswordAuthenticationToken(email,null,grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication((auth));
            }catch (Exception e){
                throw  new RuntimeException("Invalid token...");
            }
        }
        filterChain.doFilter(request,response);
    }
}