package kdk.ltd.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.WebContentInterceptor;


@Controller
public class DefaultController extends WebContentInterceptor {

    public DefaultController() {
        setRequireSession(false);
        setCacheSeconds(120);
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
