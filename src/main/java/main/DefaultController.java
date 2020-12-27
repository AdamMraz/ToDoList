package main;

import main.model.Case;
import main.model.NewCaseRepository;
import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    NewCaseRepository caseRepository;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Case> caseIterable = caseRepository.findAll();
        ArrayList<Case> cases = new ArrayList<>();
        for (Case newCase : caseIterable) {
            cases.add(newCase);
        }
        model.addAttribute("cases", cases);
        return "index";
    }
}
