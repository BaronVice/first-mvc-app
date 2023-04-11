package baronvice.mvcstuff.controllers;

import baronvice.mvcstuff.dao.PersonDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestBranchUpdateController {

    private final PersonDAO personDAO;

    @GetMapping()
    public String index(){
        return "test-batch-update/index";
    }

    @GetMapping("/without")
    public String separateUpdate(){
        personDAO.separateUpdate();
        return "redirect:/test-batch-update";
    }

    @GetMapping("/with")
    public String branchUpdate(){
        personDAO.batchUpdate();
        return "redirect:/test-batch-update";
    }
}
