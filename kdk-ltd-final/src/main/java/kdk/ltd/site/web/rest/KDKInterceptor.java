package kdk.ltd.site.web.rest;

import org.springframework.web.servlet.mvc.WebContentInterceptor;


public class KDKInterceptor extends WebContentInterceptor {

    public KDKInterceptor() {
        this.setRequireSession(false);
    }
}
