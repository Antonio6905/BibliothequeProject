package com.example.bibliotheque.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bibliotheque.Models.ConfigurationPret;
import com.example.bibliotheque.Models.Livre;
import com.example.bibliotheque.Models.Utilisateur;
import com.example.bibliotheque.Services.ConfigurationPretService;
import com.example.bibliotheque.Services.LivreService;
import com.example.bibliotheque.Services.UtilisateurService;


@Controller
public class AdminController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ConfigurationPretService configurationPretService;

    @GetMapping( "/admin")
    public String showAdminPage() {
        
        return "admin/admin";
    }

    @GetMapping("/pret")
    public String showFormPret(Model model) {
        model.addAttribute("listLivres",livreService.findAll() );
        return "admin/formPret";
    }

    @PostMapping("/pret")
    public String processPret(
            @RequestParam Integer userId,
            @RequestParam Integer livreId) {
        Utilisateur user = utilisateurService.findById(userId);
        String error;
        if (user==null) {
            error = "User not found";
        }
        else{
            List<Livre> livreDispo = livreService.findifDispo(livreId);
            ConfigurationPret config = configurationPretService.findByTypeAdherent(user.getTypeAdherent().getId());
            if (livreDispo == null) {
                error = "Plus d'exemplaire disponible!";
            } else if (utilisateurService.findIfSanctionne(userId) != null) {
                error = "User encore sanctionne!";
            }
            else if(utilisateurService.getQuota(userId)==config.getNombreLivreQuota()){
                error = "Vous avez deja atteint le quota Maximum!";
            }
        }
        return "redirect:pret";
    }
}