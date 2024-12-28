package org.javascanner.dto;

public class MethodDetail {

    private String signature;
    private String body;

    public MethodDetail() {
    }

    public MethodDetail(String signature, String body) {
        this.signature = signature;
        this.body = body;
    }

    public String getName() {
        return signature;
    }

    public String getBody() {
        return body;
    }

    public void setMethod(String method) {
        this.signature = method;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
