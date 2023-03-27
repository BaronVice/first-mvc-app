package baronvice.mvcstuff.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cool-page")
public class FirstController {

    @GetMapping("/entrance")
    public String entrancePage(){
        return "first/entrance";
    }

    @GetMapping("/exit")
    public String exitPage(){
        return "first/exit";
    }
}
