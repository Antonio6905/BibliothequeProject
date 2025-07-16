package com.example.bibliotheque.Controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bibliotheque.Services.SanctionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SanctionController {
    @Autowired
    private SanctionService sanctionService;
    @GetMapping("admin/sanctions")
    public String viewAllSanctions(Model model,@RequestParam(value = "date",required = false) LocalDate date) {
        LocalDate theDate = date;
        if (theDate==null) {
            theDate = LocalDate.now();
        }
        model.addAttribute("listSanctions", sanctionService.findActive(theDate));
        return "list/sanction";
    }
}
