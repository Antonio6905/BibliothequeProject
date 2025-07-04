package com.example.bibliotheque.Controllers;

import com.example.bibliotheque.Models.*;
import com.example.bibliotheque.Services.ConfigurationPretService;
import com.example.bibliotheque.Services.PretService;
import com.example.bibliotheque.Services.ProlongementService;
import com.example.bibliotheque.Services.SuiviStatutProlongementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class ProlongementController {

    @Autowired
    private ProlongementService prolongementService;

    @Autowired
    private PretService pretService;

    @Autowired
    private SuiviStatutProlongementService suiviStatutService;

    @Autowired
    private ConfigurationPretService configPretService;

    

    // Endpoint pour faire une demande de prolongement
    @GetMapping("/prolongement/demand/{pretId}")
    @Transactional
    public String demanderProlongement(
            @PathVariable Integer pretId,
            HttpSession session,
            Model model) {

        // Récupérer le prêt
        try {
            Pret pret = pretService.findById(pretId);
            if (pret == null) {
                throw new Exception("Prêt non trouvé");
            }

            // Vérifier si le prêt est déjà prolongé
            if (pret.getProlongement() != null) {
                throw new Exception("Ce pret a deja ete prolonge");
            }

            // Calculer les nouvelles dates
            ConfigurationPret config = configPretService.findByTypeAdherent(
                    pret.getUtilisateur().getTypeAdherent().getId());

            LocalDate dateDebut = LocalDate.now();
            LocalDate dateFin = dateDebut.plusDays(config.getDureeProlongement());

            // Créer le prolongement
            Prolongement prolongement = new Prolongement();
            prolongement.setPret(pret);
            prolongement.setDateDebutProlongement(dateDebut);
            prolongement.setDateFinProlongement(dateFin);
            prolongement = prolongementService.save(prolongement);

            SuiviStatutProlongement suivi = new SuiviStatutProlongement();
            suivi.setProlongement(prolongement);
            suivi.setStatut("non traite");
            suivi.setDateModification(LocalDateTime.now());
            suiviStatutService.save(suivi);

            model.addAttribute("message", "Demande de prolongement enregistrée");
            return "list/pret";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "list/pret";
        }
    }

    // Endpoint pour répondre à une demande de prolongement
    @GetMapping("/prolongement/response/{pretId}/{response}")
    @Transactional
    public String repondreProlongement(
            @PathVariable Integer pretId,
            @PathVariable String response,
            HttpSession session,
            Model model) {

        // Vérifier que la réponse est valide
        try {
            if (!response.equalsIgnoreCase("valide") && !response.equalsIgnoreCase("non valide")) {
                model.addAttribute("error", "Réponse invalide");
                return "error";
            }

            // Récupérer le prolongement
            Prolongement prolongement = prolongementService.findByPretId(pretId);
            if (prolongement == null) {
                model.addAttribute("error", "Aucune demande de prolongement trouvée");
                return "error";
            }

            // Mettre à jour le statut
            SuiviStatutProlongement suivi = new SuiviStatutProlongement();
            suivi.setProlongement(prolongement);
            suivi.setStatut(response.toLowerCase());
            suivi.setDateModification(LocalDateTime.now());
            suiviStatutService.save(suivi);

            // Si validé, mettre à jour la date de retour du prêt
            if (response.equalsIgnoreCase("valide")) {
                Pret pret = prolongement.getPret();
                ConfigurationPret config = configPretService.findByTypeAdherent(
                pret.getUtilisateur().getTypeAdherent().getId());
                pret.setDateRetourPrevue(pret.getDateRetourPrevue().plusDays(config.getDureeProlongement()));
                pretService.save(pret);
            }

            model.addAttribute("message", "Réponse au prolongement enregistrée");
            return "redirect:/admin";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/admin";
        }
        
    }
}