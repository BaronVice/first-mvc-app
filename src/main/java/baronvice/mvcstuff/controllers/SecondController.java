package baronvice.mvcstuff.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("bad-page")
public class SecondController {
    @GetMapping("/exit")
    public String exitPage(){
        return "second/exit";
    }
}
