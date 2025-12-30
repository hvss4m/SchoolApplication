package com.example.schoolapplication.controllers;

import com.example.schoolapplication.entities.filiere;
import org.springframework.stereotype.Controller;
import com.example.schoolapplication.services.filiereService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/filieres")
public class filiereController {
    private filiereService filiereService;

    filiereController(filiereService FS){
        this.filiereService=FS;
    }

    @GetMapping("/{id}")
    public String getfiliereById(@PathVariable Long id, Model model){
        filiere filiere = filiereService.getFiliereById(id);
        model.addAttribute("filiere", filiere);
        model.addAttribute("eleves", filiereService.getElevesList(id));
        model.addAttribute("cours", filiere.getCours());
        return "filieres/details";
    }

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "filieres/list";
    }

    @GetMapping("/new")
    public String CreateForm(Model model) {
        model.addAttribute("filiere", new filiere());
        return "filieres/create";
    }

    @PostMapping("/create")
    public String createfiliere(@ModelAttribute filiere Filiere){
        filiereService.CreateFiliere(Filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/edit/{id}")
    public String EditForm(@PathVariable Long id, Model model) {
        model.addAttribute("filiere", filiereService.getFiliereById(id));
        return "filieres/edit";
    }

    @PostMapping("/update/{id}")
    public String updateFiliere(@PathVariable Long id, @ModelAttribute filiere filiere) {
        filiereService.UpdateFiliere(id, filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/delete/{id}")
    public String deleteFiliere(@PathVariable Long id){
        filiereService.DeleteFiliere(id);
        return "redirect:/filieres";
    }
}
