package com.example.bibliotheque.Controllers;

import com.example.bibliotheque.Models.Exemplaire;
import com.example.bibliotheque.Models.Livre;
import com.example.bibliotheque.Models.TypeLivre;
import com.example.bibliotheque.Services.ExemplaireService;
import com.example.bibliotheque.Services.LivreService;
import com.example.bibliotheque.Services.TypeLivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private TypeLivreService typeLivreService;

    @Autowired
    private ExemplaireService exemplaireService;

    // Liste des livres
    @GetMapping("/livre")
    public String listLivres(Model model) {
        List<Livre> livres = livreService.findAll();
        model.addAttribute("livres", livres);
        return "list/livre";
    }

    // Formulaire de création
    @GetMapping("/livre/form")
    public String showCreateForm(Model model) {
        model.addAttribute("livre", new Livre());
        model.addAttribute("typesLivre", typeLivreService.findAll());
        return "form/livre";
    }

    // Traitement de la création
    @PostMapping("livre")
    public String createLivre(@ModelAttribute Livre livre,@RequestParam(value = "nbExemplaire") Integer nbExemplaire, Model model) {
        try {
            livreService.save(livre);
            if(nbExemplaire>0){
                for (int i = 0; i < nbExemplaire; i++) {
                    Exemplaire exp = new Exemplaire();
                    exp.setDateAjout(LocalDate.now());
                    exp.setLivre(livre);
                    exemplaireService.save(exp);
                }
            }
            return "redirect:/livre";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("typesLivre", typeLivreService.findAll());
            return "form/livre";
        }
    }

    // Suppression d'un livre
    @GetMapping("/livre/delete/{id}")
    public String deleteLivre(@PathVariable Integer id) {
        livreService.deleteById(id);
        return "redirect:/livre";
    }
}