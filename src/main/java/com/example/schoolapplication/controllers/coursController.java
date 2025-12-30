package com.example.schoolapplication.controllers;

import com.example.schoolapplication.entities.cours;
import com.example.schoolapplication.services.coursService;
import com.example.schoolapplication.services.filiereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cours")
public class coursController {
    private coursService coursService;
    private filiereService filiereService;

    public coursController(coursService coursService, filiereService filiereService) {
        this.coursService = coursService;
        this.filiereService = filiereService;
    }

    @GetMapping("/{id}")
    public String getCoursById(@PathVariable Long id, Model model){
        cours cours = coursService.getCoursById(id);
        model.addAttribute("cours", cours);
        model.addAttribute("filieres", cours.getFilieres());
        return "cours/details";
    }

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("courss", coursService.getAllCours());
        return "cours/list";
    }

    @GetMapping("/new")
    public String CreateForm(Model model) {
        model.addAttribute("cours", new cours());
        return "cours/create";
    }

    @PostMapping("/create")
    public String createCours(@ModelAttribute cours cours){
        coursService.CreateCours(cours);
        return "redirect:/cours";
    }

    @GetMapping("/edit/{id}")
    public String EditForm(@PathVariable Long id, Model model) {
        model.addAttribute("cours", coursService.getCoursById(id));
        return "cours/edit";
    }

    @PostMapping("/update/{id}")
    public String updateCours(@PathVariable Long id, @ModelAttribute cours cours) {
        coursService.UpdateCours(id, cours);
        return "redirect:/cours";
    }

    @GetMapping("/delete/{id}")
    public String deleteCours(@PathVariable Long id){
        coursService.DeleteCours(id);
        return "redirect:/cours";
    }

    @GetMapping("/{id}/attach-filiere")
    public String attachFiliereForm(@PathVariable Long id, Model model) {
        model.addAttribute("cours", coursService.getCoursById(id));
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "cours/attach-filiere";
    }

    @PostMapping("/{id}/attach-filiere")
    public String attachFiliere(@PathVariable Long id, @RequestParam Long filiereId) {
        coursService.AttachFiliere(id, filiereId);
        return "redirect:/cours/" + id;
    }
}
