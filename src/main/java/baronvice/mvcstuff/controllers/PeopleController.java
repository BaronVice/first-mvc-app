package baronvice.mvcstuff.controllers;

import baronvice.mvcstuff.dao.PersonDAO;
import baronvice.mvcstuff.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        // Get person from DAO and present it in view
        Person person = personDAO.show(id);
        model.addAttribute("person", person == null ? "Not found" : person);
        return "people/show";
    }


}
