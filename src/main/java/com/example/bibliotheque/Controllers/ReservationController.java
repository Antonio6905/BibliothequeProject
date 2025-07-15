package com.example.bibliotheque.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bibliotheque.Models.ConfigurationPret;
import com.example.bibliotheque.Models.Exemplaire;
import com.example.bibliotheque.Models.Livre;
import com.example.bibliotheque.Models.Pret;
import com.example.bibliotheque.Models.Prolongement;
import com.example.bibliotheque.Models.Reservation;
import com.example.bibliotheque.Models.SuiviStatutProlongement;
import com.example.bibliotheque.Models.SuiviStatutReservation;
import com.example.bibliotheque.Models.Utilisateur;
import com.example.bibliotheque.Services.AbonnementService;
import com.example.bibliotheque.Services.ConfigurationPretService;
import com.example.bibliotheque.Services.ExemplaireService;
import com.example.bibliotheque.Services.LivreService;
import com.example.bibliotheque.Services.PretService;
import com.example.bibliotheque.Services.ReservationService;
import com.example.bibliotheque.Services.SuiviStatutReservationService;
import com.example.bibliotheque.Services.TypeAdherentService;
import com.example.bibliotheque.Services.UtilisateurService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class ReservationController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ConfigurationPretService configurationPretService;

    @Autowired
    private SuiviStatutReservationService suiviStatutReservationService;

    @Autowired
    private PretService pretService;

    @Autowired
    private TypeAdherentService typeAdherentService;

    @Autowired
    private AbonnementService abonnementService;

    @GetMapping("/reservation")
    public String showFormReservation(Model model) {
        model.addAttribute("listLivres", livreService.findAll());
        return "list/reservation";
    }

    @GetMapping("/reservation/form")
    public String showReservation(Model model) {
        model.addAttribute("listLivres", livreService.findAll());
        return "form/reservation";
    }

    @PostMapping("/reservation")
    @Transactional
    public String processReservation(
            @RequestParam(value = "dateReservation") LocalDate dateReservation,
            @RequestParam(value = "exemplaireId") Integer exemplaireId,
            @RequestParam(value = "livreId") Integer livreId,
            HttpSession session,
            Model model) {
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        try {
            if (user == null) {
                throw new Exception("User not found");
            } else {
                Exemplaire exemplaire = exemplaireService.findDispoByLivreAndId(livreId,exemplaireId);
                ConfigurationPret config = configurationPretService.findByTypeAdherent(user.getTypeAdherent().getId());
                if(!abonnementService.isAbonnementActif(user.getId())){
                    throw new Exception("Vous n'etes plus abonne!");
                }
                else if (exemplaire == null) {
                    throw new Exception("Exemplaire Introuvable!");
                } else if(!exemplaireService.checkDispo(exemplaireId,dateReservation)){
                    throw new Exception("Exemplaire encore emprunte ou deja reserve pour cette date!");
                } else if (utilisateurService.findIfSanctionne(user.getId()) != null) {
                    throw new Exception("User encore sanctionne!");
                } else if (utilisateurService.getQuota(user.getId()) == config.getNombreLivreQuota()) {
                    throw new Exception("Vous avez deja atteint votre quota Maximal!");
                } else if (typeAdherentService.findIfCanBorrow(user.getTypeAdherent().getId(), livreId) == null) {
                    throw new Exception("Vous ne pouvez pas emprunter ce type de livre");
                }
                Reservation newResa = new Reservation();
                newResa.setUtilisateur(user);
                newResa.setDateDemande(dateReservation);
                newResa.setDateExpiration(dateReservation.plusDays(configurationPretService.findByTypeAdherent(user.getTypeAdherent().getId()).getDureeReservation()));
                newResa.setExemplaire(exemplaire);

                newResa = reservationService.save(newResa);
                SuiviStatutReservation statut = new SuiviStatutReservation();
                statut.setDateModification(LocalDateTime.now());
                statut.setReservation(newResa);
                statut.setStatut("non traite");

                suiviStatutReservationService.save(statut);

                return "redirect:/reservation";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "form/reservation";
        }
    }

    @GetMapping("/reservation/response/{reservationId}/{response}")
    @Transactional
    public String repondreReservation(
            @PathVariable Integer reservationId,
            @PathVariable String response,
            Model model) {

        // Vérifier que la réponse est valide
        try {
            Reservation reservation = reservationService.findById(reservationId);
            if (reservation == null) {
                throw new Exception("Reservation not found");
            }
            Utilisateur user = reservation.getUtilisateur();
            if (user == null) {
                throw new Exception("User not found");
                
            } 
            ConfigurationPret config = configurationPretService.findByTypeAdherent(user.getTypeAdherent().getId());
            SuiviStatutReservation statut = new SuiviStatutReservation();
            statut.setDateModification(LocalDateTime.now());
            statut.setReservation(reservation);
            statut.setStatut(response);

            if(response.equalsIgnoreCase("valide")){
                Pret pret = new Pret();
                pret.setUtilisateur(user);
                pret.setDatePret(reservation.getDateDemande().atStartOfDay());
                pret.setDateRetourPrevue(pret.getDatePret().plusDays(config.getDureePret()).toLocalDate());
                pret.setExemplaire(reservation.getExemplaire());
                pretService.save(pret);
            }

            suiviStatutReservationService.save(statut);

            return "redirect:/reservation";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "list/reservation";
        }
    }
}