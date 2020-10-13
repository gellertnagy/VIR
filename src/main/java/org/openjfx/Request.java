package org.openjfx;

import javafx.beans.property.IntegerProperty;

import java.net.URL;

public class Request {
    String method;
    //URL url;
    String url;
    String body;

    public Request(){}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String  getUrl() {
        return url;
    }

    public void setUrl(String  url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
