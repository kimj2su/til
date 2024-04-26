package com.jisu.requestbody;


public class RequestBodyDto {

    private Object filed;

    public RequestBodyDto() {
    }

    public Object getFiled() {
        return filed;
    }

    public void setFiled(Object filed) {
        this.filed = filed;
    }

    @Override
    public String toString() {
        return "RequestBodyDto{" +
                "filed=" + filed +
                '}';
    }
}
