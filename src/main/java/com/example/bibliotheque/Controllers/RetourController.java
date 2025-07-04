package com.example.bibliotheque.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.bibliotheque.Models.Pret;
import com.example.bibliotheque.Models.Retour;
import com.example.bibliotheque.Services.PretService;
import com.example.bibliotheque.Services.RetourService;

@Controller
public class RetourController {

    @Autowired
    private RetourService retourService;

    @Autowired
    private PretService pretService;

    @GetMapping("/retour/{idPret}")
    public String executeRetour(@PathVariable Integer idPret,Model model){
        try {
            Pret pret = pretService.findById(idPret);
            Retour retour = new Retour();
            retour.setDateRemiseEffective(LocalDateTime.now());
            retour.setPret(pret);
            retourService.save(retour);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/pret";
        }
        return "redirect:/admin/pret";
    }
    
}