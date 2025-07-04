package com.example.bibliotheque.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bibliotheque.Services.RetourService;

@Controller
public class RetourController {

    @Autowired
    private RetourService retourService;

    @GetMapping("/retour/{idPret}")
    public String executeRetour(){


        return "redirect:/admin/pret";
    }
    
}