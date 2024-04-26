package com.jisu.requestbody;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CacheBodyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CacheBodyHttpServletRequestWrapper cachedBodyHttpServletRequest = new CacheBodyHttpServletRequestWrapper(request);
        filterChain.doFilter(cachedBodyHttpServletRequest, response);
    }
}
