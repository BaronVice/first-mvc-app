package baronvice.mvcstuff.controllers;

import baronvice.mvcstuff.dao.PersonDAO;
import baronvice.mvcstuff.models.Person;
import baronvice.mvcstuff.util.PersonValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @GetMapping()
    public String index(Model model){
        // Get all people from DAO and present them in view
        model.addAttribute("people", personDAO.index());

        return "/people/index";
    }

    @GetMapping("/{nickname}")
    public String show(@PathVariable("nickname") String nickname,
                       Model model){
        // TODO: th:id
        // Get person from DAO and present it in view
        Person person = personDAO.showByNickname(nickname);
        model.addAttribute("person", person == null ?
                // That's kinda lame, better option is to send message. Sadly, I'm not frontend dev
                new Person("Sorry,", "but", "not", "found") : person);

        return "/people/show";
    }

    @GetMapping("/new")
    // Here empty person is created and sent to people/new
    public String sendCreationPage(@ModelAttribute("person") Person person){
        return "/people/new";
    }

    @GetMapping("/{nickname}/edit")
    public String sendEditPage(@PathVariable("nickname") String nickname,
                               Model model){
        model.addAttribute("person", personDAO.showByNickname(nickname));
        return "/people/edit";
    }

    @PostMapping()
    // This person is caught from page which sends post request here (/people/create)
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{nickname}")
    public String edit(@ModelAttribute("person") @Valid Person updatedPerson,
                       BindingResult bindingResult,
                       @PathVariable("nickname") String previousNickname){
        personValidator.validate(updatedPerson, bindingResult);

        if (bindingResult.hasErrors())
            return "/people/edit";

        personDAO.update(previousNickname, updatedPerson);
        return String.format("redirect:/people/%s", updatedPerson.getNickname());
    }

    @DeleteMapping("/{nickname}")
    public String delete(@PathVariable("nickname") String nickname){
        personDAO.delete(nickname);
        return "redirect:/people";
    }
}
