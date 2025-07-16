package com.example.bibliotheque.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bibliotheque.Models.Pret;
import com.example.bibliotheque.Models.Retour;
import com.example.bibliotheque.Models.Sanction;
import com.example.bibliotheque.Models.Utilisateur;
import com.example.bibliotheque.Services.PretService;
import com.example.bibliotheque.Services.RetourService;
import com.example.bibliotheque.Services.SanctionService;
import com.example.bibliotheque.Services.SanctionTypeAdherentService;

@Controller
public class RetourController {

    @Autowired
    private RetourService retourService;

    @Autowired
    private PretService pretService;

    @Autowired
    private SanctionService sanctionService;

    @Autowired
    private SanctionTypeAdherentService sanctionTypeAdherentService;

    @GetMapping("/retour")
    public String executeRetour(@RequestParam(value = "idPret") Integer idPret,@RequestParam(value = "dateRetour",required = true)LocalDate dateRetour,Model model){
        try {
            Pret pret = pretService.findById(idPret);
            Utilisateur user = pret.getUtilisateur();
            Retour retour = new Retour();
            retour.setDateRemiseEffective(dateRetour.atStartOfDay());
            retour.setPret(pret);
            retourService.save(retour);

            if(pret.getDateRetourPrevue().isBefore(dateRetour)){
                Sanction sanction = new Sanction();
                sanction.setMotif("Retard sur retour de livre");
                sanction.setDateDebutSanction(dateRetour);
                List<Sanction> allSanctions = sanctionService.findByUser(user.getId());
                if(allSanctions!=null && !allSanctions.isEmpty()){
                    Sanction sanction0 = allSanctions.get(0);
                    sanction.setDateDebutSanction(sanction0.getDateDebutSanction());
                    sanction.setDateFinSanction(sanction0.getDateFinSanction().plusDays(sanctionTypeAdherentService.findByTypeAdherentId(user.getTypeAdherent().getId()).getDureeSanction()));
                }
                else{
                    sanction.setDateFinSanction(dateRetour.plusDays(sanctionTypeAdherentService.findByTypeAdherentId(user.getTypeAdherent().getId()).getDureeSanction()));
                }
                
                sanction.setPret(pret);
                sanction.setUtilisateur(user);
                sanctionService.save(sanction);
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/pret";
        }
        return "redirect:/admin/pret";
    }
    
}