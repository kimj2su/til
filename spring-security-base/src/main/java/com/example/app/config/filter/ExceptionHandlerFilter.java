//package com.example.app.config.filter;
//
//import com.example.app.controller.response.Response;
//import com.example.app.exception.ErrorCode;
//import com.example.app.exception.UserJwtDifferentException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.MediaType;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class ExceptionHandlerFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        try{
//            filterChain.doFilter(request, response);
//        }catch (ExpiredJwtException e){
//            //토큰의 유효기간 만료
//            setErrorResponse(response, ErrorCode.INVALID_TOKEN.getStatus().value(), ErrorCode.INVALID_TOKEN.getMessage());
//        }catch (JwtException e){
//            //유효하지 않은 토큰
//            setErrorResponse(response, ErrorCode.INVALID_TOKEN.getStatus().value(), ErrorCode.INVALID_TOKEN.getMessage());
//        }catch (UserJwtDifferentException e){
//            //중복로그인 & 로그아웃
//            setErrorResponse(response, e.getCode(), e.getMessage());
//        }
//    }
//    private void setErrorResponse(HttpServletResponse response, int errorCode, String message){
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.setContentType("application/json");
//        response.setStatus(ErrorCode.INVALID_TOKEN.getStatus().value());
//
////        response.setStatus(200);
////        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
////        ResultDTO resultDTO = new ResultDTO ();
////        resultDTO.setCode (errorCode);
////        resultDTO.setMessage (message);
//        try{
//            response.getWriter().write(Response.error(ErrorCode.INVALID_TOKEN.name()).toStream());
////            response.getWriter().write(objectMapper.writeValueAsString(resultDTO));
//        }catch (IOException ex){
//            ex.printStackTrace();
//        }
//    }
//}
