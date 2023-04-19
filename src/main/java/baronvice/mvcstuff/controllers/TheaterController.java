package baronvice.mvcstuff.controllers;

import baronvice.mvcstuff.dao.TheaterDAO;
import baronvice.mvcstuff.models.theater.Actor;
import baronvice.mvcstuff.models.theater.Producer;
import baronvice.mvcstuff.models.theater.StageWorker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** TODO: get for actor (his info and performances), same for producer
 *   get and (??)stage workers(??)
 *    post for add actor, producer, stage worker, performance
 *     patch for stage */

@Controller
@RequestMapping("/theater")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TheaterController {

    private final TheaterDAO theaterDAO;

    @GetMapping()
    public String showAvailablePerformances(Model model){
        model.addAttribute("performances", theaterDAO.getAvailablePerformances());
        return "theater/index";
    }

    @GetMapping("/{performanceName}")
    public String showPerformanceInformation(@PathVariable String performanceName,
                                             Model model){
        model.addAttribute("performance", theaterDAO.getPerformance(performanceName));
        return "theater/show";
    }

    @GetMapping("/new-actor")
    public String showActorCreationPage(@ModelAttribute Actor actor){
        return "theater/new-actor";
    }

    @GetMapping("/new-producer")
    public String showProducerCreationPage(@ModelAttribute Producer producer){
        return "theater/new-producer";
    }

    @GetMapping("/new-stage-worker")
    public String showStageWorkerCreationPage(@ModelAttribute StageWorker stageWorker){
        return "theater/new-stage-worker";
    }

    @GetMapping("/{performanceName}/edit")
    public String showEditPage(@PathVariable String performanceName,
                               Model model){
        model.addAttribute("performance", theaterDAO.getPerformance(performanceName));
        return "theater/edit";
    }



}
