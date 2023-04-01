package baronvice.mvcstuff.controllers;

import baronvice.mvcstuff.dao.PersonDAO;
import baronvice.mvcstuff.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PeopleController {

    PersonDAO personDAO;

    @GetMapping()
    public String index(Model model){
        // Get all people from DAO and present them in view
        model.addAttribute("people", personDAO.index());

        return "people/index";
    }

    @GetMapping("/{nickname}")
    public String show(@PathVariable("nickname") String nickname,
                       Model model){
        // Get person from DAO and present it in view
        Person person = personDAO.show(nickname);
        model.addAttribute("person", person == null ?
                // That's kinda lame, better option is to send message. Sadly, I'm not frontend dev
                new Person("Sorry,", "but...", "Not found") : person);

        return "people/show";
    }

    @GetMapping("/new")
    // Here empty person is created and sent to people/new
    public String sendCreationPage(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @GetMapping("/{nickname}/edit")
    public String sendEditPage(@PathVariable("nickname") String nickname,
                       Model model){
        model.addAttribute("person", personDAO.show(nickname));

        return "people/edit";
    }

    @PostMapping()
    // This person is caught from page which sends post request here (/people/create)
    public String create(@ModelAttribute("person") Person person){
        try{
            personDAO.save(person);
        } catch (RuntimeException e){
            // I don't know yet how to handle it
        }

        return "redirect:/people";
    }

    @PatchMapping("/{nickname}")
    public String edit(@ModelAttribute("person") Person person,
                       @PathVariable("nickname") String nickname){
        personDAO.update(nickname,person);

        return String.format("redirect:/people/%s", person.getNickname());
    }

    @DeleteMapping("/{nickname}")
    public String delete(@PathVariable("nickname") String nickname){
        personDAO.delete(nickname);

        return "redirect:/people";
    }
}
