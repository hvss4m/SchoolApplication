package com.example.schoolapplication.controllers;

import com.example.schoolapplication.entities.eleve;
import com.example.schoolapplication.entities.filiere;
import com.example.schoolapplication.services.coursService;
import com.example.schoolapplication.services.eleveService;
import com.example.schoolapplication.services.filiereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/eleves")
public class eleveController {
    private final eleveService EleveService;
    private final filiereService FiliereService;
    private final coursService CoursService;

    public eleveController(
            eleveService eleveService,
            filiereService filiereService,
            coursService coursService) {

        EleveService = eleveService;
        FiliereService = filiereService;
        CoursService = coursService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("eleves", EleveService.getAllEleves());
        return "eleves/list";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        eleve eleve = EleveService.getEleveById(id);
        model.addAttribute("eleve", eleve);
        model.addAttribute("filieres", FiliereService.getAllFilieres());
        model.addAttribute("cours", CoursService.getAllCours());
        return "eleves/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("eleve", new eleve());
        model.addAttribute("filieres", FiliereService.getAllFilieres());
        return "eleves/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute eleve eleve, @RequestParam Long filiereId) {

        if (filiereId == null) {
            throw new RuntimeException("La filière est obligatoire");
        }

        filiere filiere = FiliereService.getFiliereById(filiereId);
        eleve.setFiliere(filiere);

        EleveService.CreateEleve(eleve);

        return "redirect:/eleves";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("eleve", EleveService.getEleveById(id));
        model.addAttribute("filieres", FiliereService.getAllFilieres());
        return "eleves/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute eleve eleve) {
        EleveService.UpdateEleve(id, eleve);
        return "redirect:/eleves";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        EleveService.DeleteEleve(id);
        return "redirect:/eleves";
    }

    @PostMapping("/{id}/filiere")
    public String affectFiliere(@PathVariable Long id, @RequestParam Long filiereId) {
        EleveService.AffectFiliere(id, filiereId);
        return "redirect:/eleves/" + id;
    }

    @GetMapping("/{id}/inscrire-cours")
    public String inscrireCoursForm(@PathVariable Long id, Model model) {
        model.addAttribute("eleve", EleveService.getEleveById(id));
        model.addAttribute("cours", CoursService.getAllCours());
        return "eleves/inscrire-cours";
    }

    @PostMapping("/{id}/inscrire-cours")
    public String inscrireCours(@PathVariable Long id, @RequestParam Long coursId) {
        EleveService.inscritCours(coursId, id);
        return "redirect:/eleves/" + id;
    }
}
