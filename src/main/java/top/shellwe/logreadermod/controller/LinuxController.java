package top.shellwe.logreadermod.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LinuxController {
    @RequestMapping("/1")
    @ResponseBody
    public String hello() {
        System.out.println("hh");
        return "shellwe is programing!";
    }
}
