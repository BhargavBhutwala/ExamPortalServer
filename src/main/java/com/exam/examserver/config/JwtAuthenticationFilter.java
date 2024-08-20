package com.exam.examserver.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.examserver.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                //1. Get the JWT token
        
                final String requestTokenHeader = request.getHeader("Authorization");

                System.out.println(requestTokenHeader);

                String username = null;
                String jwtToken = null;

                if (requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {

                    jwtToken = requestTokenHeader.substring(7);

                    try {
                        username = this.jwtTokenHelper.getUsernameFromToken(jwtToken);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Enable to get Jwt token");
                    }catch (ExpiredJwtException e){
                        System.out.println("Jwt token expired");
                    }catch (MalformedJwtException e){
                        System.out.println("Invalid Jwt");
                    }
                    

                }else{
                    System.out.println("Jwt token does not start with Bearer");
                }

                //2. Validate the JWT token

                if (username != null && SecurityContextHolder.getContext().getAuthentication()==null){

                    final UserDetails userDetailsService =  this.userDetailsService.loadUserByUsername(username);

                    if (this.jwtTokenHelper.validateToken(jwtToken, userDetailsService)) {
                        //token is valid

                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetailsService,null,userDetailsService.getAuthorities());
                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(token);
                    }else{
                        System.out.println("Invalid JWT token");
                    }
                }else{
                    System.out.println("Username is null or context is not null");
                }

                filterChain.doFilter(request, response);
    }

}
