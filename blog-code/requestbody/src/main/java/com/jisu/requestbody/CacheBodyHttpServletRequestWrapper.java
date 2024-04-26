package com.jisu.requestbody;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CacheBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    // private ByteArrayOutputStream cachedBytes;
    //
    // public RequestBodyHttpServletRequestWrapper(HttpServletRequest request) {
    //     super(request);
    // }
    //
    // @Override
    // public ServletInputStream getInputStream() throws IOException {
    //     if (cachedBytes == null)
    //         cacheInputStream();
    //
    //     return new CachedServletInputStream(cachedBytes.toByteArray());
    // }
    //
    // @Override
    // public BufferedReader getReader() throws IOException{
    //     return new BufferedReader(new InputStreamReader(getInputStream()));
    // }
    //
    // private void cacheInputStream() throws IOException {
    //     /* Cache the inputstream in order to read it multiple times. For
    //      * convenience, I use apache.commons IOUtils
    //      */
    //     cachedBytes = new ByteArrayOutputStream();
    //     IOUtils.copy(super.getInputStream(), cachedBytes);
    // }
    //
    //
    // /* An input stream which reads the cached request body */
    // private static class CachedServletInputStream extends ServletInputStream {
    //
    //     private final ByteArrayInputStream buffer;
    //
    //     public CachedServletInputStream(byte[] contents) {
    //         this.buffer = new ByteArrayInputStream(contents);
    //     }
    //
    //     @Override
    //     public int read() {
    //         return buffer.read();
    //     }
    //
    //     @Override
    //     public boolean isFinished() {
    //         return buffer.available() == 0;
    //     }
    //
    //     @Override
    //     public boolean isReady() {
    //         return true;
    //     }
    //
    //     @Override
    //     public void setReadListener(ReadListener readListener) {
    //         throw new RuntimeException("Not implemented");
    //     }
    // }
    //
    // public String getRequestBody() {
    //     return cachedBytes.toString(StandardCharsets.UTF_8);
    // }
    //
    // public void setRequestBody(String body) throws IOException {
    //     cachedBytes = new ByteArrayOutputStream();
    //     cachedBytes.write(body.getBytes(StandardCharsets.UTF_8));
    //     // this.cachedBytes = body.getBytes(StandardCharsets.UTF_8);
    //     // this.cachedBytes = new ByteArrayOutputStream();
    //     // this.buffer = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
    // }

    private byte[] requestBody;

    public CacheBodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream is = request.getInputStream();
        requestBody = is.readAllBytes();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            private final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    public String getRequestBody() {
        return new String(requestBody, StandardCharsets.UTF_8);
    }

    public void setRequestBody(String body) {
        this.requestBody = body.getBytes(StandardCharsets.UTF_8);
    }
}