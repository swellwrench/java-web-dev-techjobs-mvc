package org.launchcode.javawebdevtechjobsmvc.controllers;

import org.launchcode.javawebdevtechjobsmvc.models.Job;
import org.launchcode.javawebdevtechjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.launchcode.javawebdevtechjobsmvc.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @RequestMapping(value="results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        ArrayList<Job> jobs = new ArrayList<>();
        String formattedSearchType;
        if (searchType.equals("coreCompetency")) {
            formattedSearchType = "Skill";
        } else if (searchType.equals("positionType")) {
            formattedSearchType = "Position type";
        } else {
            formattedSearchType = searchType;
        }

        if (searchTerm.equals("all") || searchTerm.equals("")) {
            jobs = JobData.findAll();
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        model.addAttribute("columns", columnChoices);
        model.addAttribute("jobs", jobs);
        model.addAttribute("title", "Search results for " + formattedSearchType + ": " + searchTerm);
        model.addAttribute("radioSelection", searchType);
        return "results";
    }

}
