package com.example.threadlocal.trace.logtrace;

import com.example.threadlocal.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
