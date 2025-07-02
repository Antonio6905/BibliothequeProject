package com.example.bibliotheque.Controllers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bibliotheque.Models.ConfigurationPret;
import com.example.bibliotheque.Models.Exemplaire;
import com.example.bibliotheque.Models.Livre;
import com.example.bibliotheque.Models.Pret;
import com.example.bibliotheque.Models.Utilisateur;
import com.example.bibliotheque.Services.ConfigurationPretService;
import com.example.bibliotheque.Services.ExemplaireService;
import com.example.bibliotheque.Services.LivreService;
import com.example.bibliotheque.Services.PretService;
import com.example.bibliotheque.Services.TypeAdherentService;
import com.example.bibliotheque.Services.UtilisateurService;


@Controller
public class AdminController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ConfigurationPretService configurationPretService;

    @Autowired
    private TypeAdherentService typeAdherentService;

    @Autowired
    private PretService pretService;

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
            @RequestParam Integer numero,
            @RequestParam Integer livreId,
            Model model) {
        Utilisateur user = utilisateurService.findById(numero);
        try {
            if (user == null) {
                throw new Exception("User not found");
            } else {
                List<Exemplaire> livreDispo = exemplaireService.findDispoByLivre(livreId);
                ConfigurationPret config = configurationPretService.findByTypeAdherent(user.getTypeAdherent().getId());
                if (livreDispo == null) {
                    throw new Exception("Plus d'exemplaire disponible!");
                } else if (utilisateurService.findIfSanctionne(numero) != null) {
                    throw new Exception("User encore sanctionne!");
                } else if (utilisateurService.getQuota(numero) == config.getNombreLivreQuota()) {
                    throw new Exception("Cet utilisateur a deja atteint son quota Maximal!");
                } else if (typeAdherentService.findIfCanBorrow(user.getTypeAdherent().getId(), livreId) == null) {
                    throw new Exception("Cet utilisateur ne peux pas emprunter ce type de livre");
                }
                Pret newPret = new Pret();
                newPret.setUtilisateur(user);
                newPret.setExemplaire(livreDispo.get(0));
                newPret.setDatePret(LocalDateTime.now());
                newPret.setDateRetourPrevue(LocalDate.now().plusDays(config.getDureePret()));

                pretService.save(newPret);
                return "redirect:/pret";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/formPret";
        }
    }
}