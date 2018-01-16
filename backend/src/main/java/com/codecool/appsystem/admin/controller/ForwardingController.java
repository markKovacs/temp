package com.codecool.appsystem.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {

    @RequestMapping({
            "/applicants",
            "/applicants/{id:\\w+}",
            "/calendar",
            "/editscreening",
            "/login",
            "/login/{jwt:\\w+}",
            "/evaluate",
            "/evaluateuser/{id:\\w+}",
            "/surveygenerator",
            "/edittemplate",
            "/dashboard",
            "/personal-data"
    })
    public String index() {
        return "forward:/index.html";
    }

}
