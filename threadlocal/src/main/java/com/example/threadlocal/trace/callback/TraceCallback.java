package com.example.threadlocal.trace.callback;

public interface TraceCallback<T> {
    T call();
}
