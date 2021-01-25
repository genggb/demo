package com.ggb.spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringMvcTest1 {

    @RequestMapping("test1")
    @ResponseBody
    public String test1(HttpServletRequest request) {
        String name = request.getParameter("name");
        return name;
    }


}
