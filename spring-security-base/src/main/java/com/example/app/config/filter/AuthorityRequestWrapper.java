//package com.example.app.config.filter;
//
//import com.example.app.dto.UserDto;
//import com.example.app.exception.ErrorCode;
//import com.example.app.exception.SampleApplicationException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ReadListener;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author : hzn
// * @date : 2023/02/23
// * @description :
// */
//public class AuthorityRequestWrapper extends HttpServletRequestWrapper {
//    private final List<String> authParameters = List.of ("userSn", "userCmmnSn");
//    private final ObjectMapper objectMapper = new ObjectMapper ();
//    private ServletInputStream body;
//
//    public AuthorityRequestWrapper (HttpServletRequest request) {
//        super (request);
//        try {
//            body = getInputStream (request);
//        } catch (SampleApplicationException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new SampleApplicationException (ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public ServletInputStream getInputStream () {
//        return body;
//    }
//
//    private ServletInputStream getInputStream (HttpServletRequest request) throws IOException {
//        // RequestBody 권한 체크
//        BufferedReader br = new BufferedReader (new InputStreamReader (request.getInputStream (), StandardCharsets.UTF_8));
//        String line;
//        StringBuilder sb = new StringBuilder ();
//        while ((line = br.readLine ()) != null) {
//            sb.append (line);
//        }
//        String body = sb.toString ().replaceAll ("<", "&lt;").replaceAll (">", "&gt;");
//        br.close ();
//
//        Object obj = objectMapper.readValue (body, Object.class);
//        if (!ObjectUtils.isEmpty (obj)) {
//            UserDto customUserDetails = (UserDto) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
//            if (obj instanceof Map) {
//                Map<String, Object> bodyMap = (Map) obj;
//                for (String authParameter : authParameters) {
//                    if (bodyMap.containsKey (authParameter)) {
//                        checkAuthority (authParameter, bodyMap.get (authParameter), customUserDetails);
//                    }
//                }
//            } else if (obj instanceof List) {
//                List<Map<String, Object>> bodyList = (List) obj;
//                bodyList.stream ().forEach (m -> {
//                    for (String authParameter : authParameters) {
//                        if (m.containsKey (authParameter)) {
//                            checkAuthority (authParameter, m.get (authParameter), customUserDetails);
//                        }
//                    }
//                });
//            }
//        } else {
//            body = "";
//        }
//
//        ByteArrayInputStream bais = new ByteArrayInputStream (body.getBytes (StandardCharsets.UTF_8));
//        ServletInputStream sis = new ServletInputStream () {
//            @Override
//            public int read () throws IOException {
//                return bais.read ();
//            }
//
//            @Override
//            public void setReadListener (ReadListener readListener) {
//            }
//
//            @Override
//            public boolean isReady () {
//                return false;
//            }
//
//            @Override
//            public boolean isFinished () {
//                return false;
//            }
//        };
//        return sis;
//    }
//
//    private void checkAuthority (String parameterName, Object value, UserDto customUserDetails) {
//        if (!ObjectUtils.isEmpty (parameterName) && !ObjectUtils.isEmpty (value) && customUserDetails != null)
//            if ((authParameters.get (0).equals (parameterName) && !customUserDetails.getUsername ().equals (value))
//                    || (authParameters.get (1).equals (parameterName) && !customUserDetails.getUsername ().equals (value)))
//                throw new SampleApplicationException (ErrorCode.INVALID_PERMISSION);
//    }
//}