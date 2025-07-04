package com.example.bibliotheque.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bibliotheque.Services.PretService;
import com.example.bibliotheque.Services.ProlongementService;
import com.example.bibliotheque.Services.ReservationService;



@Controller
public class AdminController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ProlongementService prolongementService;
    
    @Autowired
    private PretService pretService;

    @GetMapping( "/admin")
    public String showAdminPage() {
        
        return "admin/admin";
    }

    @GetMapping("/admin/reservation")
    public String showAdminPageReservation(Model model) {
        model.addAttribute("listReservations",reservationService.findNonTraite());
        return "admin/reservation";
    }

    @GetMapping("/admin/prolongement")
    public String showAdminPageProlongement(Model model) {
        model.addAttribute("listProlongements", prolongementService.findNonTraite());
        return "admin/prolongement";
    }

    @GetMapping("/admin/pret")
    public String showAdminPagePret(Model model) {
        model.addAttribute("listPrets", pretService.findEnCours());
        return "admin/pret";
    }

}