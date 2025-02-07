package fr.iut.prjspring2025.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur pour la page d'accueil.
 *
 * Gère l'affichage de la page d'accueil de l'application.
 */
@Controller
public class HomeController {

    /**
     * Affiche la vue correspondant à l'accueil.
     *
     * @return le nom de la vue "home"
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

}
