//package com.example.app.config.filter;
//
//import com.example.app.controller.response.Response;
//import com.example.app.exception.ErrorCode;
//import com.example.app.exception.SampleApplicationException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class AuthorityFilter extends OncePerRequestFilter {
//    private final ObjectMapper objectMapper = new ObjectMapper ();
//
//    @Override
//    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
//        try {
//            if ("application/json".equals (request.getContentType ())) filterChain.doFilter (new AuthorityRequestWrapper (request), response);
//            else filterChain.doFilter (request, response);
//        } catch (SampleApplicationException e) {
//            exceptionResponse (response, e);
//        } catch (Exception e) {
//            exceptionResponse (response, new SampleApplicationException(ErrorCode.INTERNAL_SERVER_ERROR));
//        }
//    }
//
//    private void exceptionResponse (HttpServletResponse response, SampleApplicationException e) throws IOException {
////        ResultDTO resultDTO = new ResultDTO (e.getCode (), e.getMessage ());
////        String responseBody = objectMapper.writeValueAsString (resultDTO);
////        response.setStatus (200);
////        response.setContentType ("application/json");
////        response.setContentLength (responseBody.length ());
////        response.getWriter ().println (responseBody);
//        response.setContentType("application/json");
//        response.setStatus(ErrorCode.INVALID_TOKEN.getStatus().value());
//        response.getWriter().write(Response.error(ErrorCode.INVALID_TOKEN.name()).toStream());
//    }
//}