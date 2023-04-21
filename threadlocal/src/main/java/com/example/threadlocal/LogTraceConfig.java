package com.example.threadlocal;

import com.example.threadlocal.trace.logtrace.FieldLogTrace;
import com.example.threadlocal.trace.logtrace.LogTrace;
import com.example.threadlocal.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace fieldLogTrace() {
        return new ThreadLocalLogTrace();
    }
}
