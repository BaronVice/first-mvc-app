package baronvice.mvcstuff.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("my-first-pages/cool-page")
public class FirstController {

    @GetMapping("/entrance")
    public String entrancePage(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "surname", required = false) String surname,
                               Model model){

        if (name == null || surname == null){
            name = "Unknown";
            surname = "User";
        }

        model.addAttribute("message", String.format("Hello, %s %s!", name, surname));

        return "first/entrance";
    }

    @GetMapping("/exit")
    public String exitPage(){
        return "first/exit";
    }

    @GetMapping("/calc")
    public String calcPage(@RequestParam("a") String a,
                           @RequestParam("b") String b,
                           @RequestParam("op") String op,
                           Model model){

        try{
            int first = Integer.parseInt(a);
            int second = Integer.parseInt(b);
            String operation;
            int result;
            switch (op){
                case "add" -> { result = first + second; operation = "+"; }
                case "sub" -> { result = first - second; operation = "-"; }
                case "mul" -> { result = first * second; operation = "*"; }
                case "div" -> { result = first / second; operation = "/"; }
                default -> throw new RuntimeException();
            }
            model.addAttribute("message", String.format("%d %s %d = %d", first, operation, second, result));
        }
        catch (Exception e){
            model.addAttribute("message", "Dumb ass...");
        }

        return "first/calculator";
    }
}
