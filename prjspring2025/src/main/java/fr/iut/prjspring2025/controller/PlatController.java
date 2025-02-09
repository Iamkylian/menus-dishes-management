package fr.iut.prjspring2025.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.iut.prjspring2025.model.Plat;
import fr.iut.prjspring2025.repository.CategorieRepository;
import fr.iut.prjspring2025.repository.PlatRepository;

/**
 * Contrôleur pour l'entité Plat.
 *
 * Ce contrôleur gère l'ensemble des opérations CRUD sur les plats et propose :
 * - Un système de filtrage avancé (catégorie, valeurs nutritionnelles)
 * - Une pagination des résultats
 * - Un tri multicritères
 * - Une gestion détaillée des informations nutritionnelles
 */

@Controller
public class PlatController {

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Affiche la liste paginée des plats en appliquant les filtres possibles.
     *
     * @param model le modèle pour la vue
     * @param categorieId l'identifiant de la catégorie pour filtrer les plats
     * (optionnel)
     * @param minCalories le seuil de calories minimum (optionnel)
     * @param maxCalories le seuil de calories maximum (optionnel)
     * @param minLipides le seuil de lipides minimum (optionnel)
     * @param maxLipides le seuil de lipides maximum (optionnel)
     * @param minGlucides le seuil de glucides minimum (optionnel)
     * @param maxGlucides le seuil de glucides maximum (optionnel)
     * @param minProteines le seuil de protéines minimum (optionnel)
     * @param maxProteines le seuil de protéines maximum (optionnel)
     * @param motCle mot-clé pour la recherche sur le nom du plat
     * @param sort mode de tri à appliquer sur la liste
     * @param page numéro de la page
     * @param size nombre d'éléments par page
     * @param action action en cours (ex. "new", "mod", "del")
     * @param id identifiant du plat, utilisé lors de l'édition ou suppression
     * @return le nom de la vue affichant la liste des plats
     */
    @GetMapping("/plats")
    public String listePlats(Model model,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories,
            @RequestParam(required = false) Integer minLipides,
            @RequestParam(required = false) Integer maxLipides,
            @RequestParam(required = false) Integer minGlucides,
            @RequestParam(required = false) Integer maxGlucides,
            @RequestParam(required = false) Integer minProteines,
            @RequestParam(required = false) Integer maxProteines,
            @RequestParam(defaultValue = "") String motCle,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String action,
            @RequestParam(defaultValue = "0") Long id) {

        // Détermine l'ordre de tri selon le paramètre "sort"
        Sort sortOrder = Sort.unsorted();
        if (sort != null) {
            switch (sort) {
                case "nomAsc" ->
                    sortOrder = Sort.by("nom").ascending();
                case "nomDesc" ->
                    sortOrder = Sort.by("nom").descending();
                case "calAsc" ->
                    sortOrder = Sort.by("nbCalories").ascending();
                case "calDesc" ->
                    sortOrder = Sort.by("nbCalories").descending();
                case "catAsc" ->
                    sortOrder = Sort.by("categorie.nom").ascending();
                case "catDesc" ->
                    sortOrder = Sort.by("categorie.nom").descending();
                case "glucidesAsc" ->
                    sortOrder = Sort.by("nbGlucides").ascending();
                case "glucidesDesc" ->
                    sortOrder = Sort.by("nbGlucides").descending();
                case "lipidesAsc" ->
                    sortOrder = Sort.by("nbLipides").ascending();
                case "lipidesDesc" ->
                    sortOrder = Sort.by("nbLipides").descending();
                case "proteinesAsc" ->
                    sortOrder = Sort.by("nbProteines").ascending();
                case "proteinesDesc" ->
                    sortOrder = Sort.by("nbProteines").descending();
            }
        }

        // Configuration de la pagination avec le tri
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        // Récupération des plats filtrés depuis le repository
        Page<Plat> pagePlats = platRepository.findFiltered(
                categorieId, minCalories, maxCalories,
                minLipides, maxLipides,
                minGlucides, maxGlucides,
                minProteines, maxProteines,
                motCle, pageable);

        // Ajout des attributs au modèle pour la vue Thymeleaf
        model.addAttribute("plats", pagePlats.getContent());
        model.addAttribute("page", pagePlats);
        model.addAttribute("sort", sort);
        model.addAttribute("motCle", motCle);
        model.addAttribute("categorieId", categorieId);
        model.addAttribute("minCalories", minCalories);
        model.addAttribute("maxCalories", maxCalories);
        model.addAttribute("minLipides", minLipides);
        model.addAttribute("maxLipides", maxLipides);
        model.addAttribute("minGlucides", minGlucides);
        model.addAttribute("maxGlucides", maxGlucides);
        model.addAttribute("minProteines", minProteines);
        model.addAttribute("maxProteines", maxProteines);
        // Fournit la liste des catégories pour le filtre
        model.addAttribute("categories", categorieRepository.findAll());

        // Gestion de l'affichage du formulaire d'édition si requis
        if (!action.isEmpty() && id > 0) {
            Optional<Plat> optPlat = platRepository.findById(id);
            if (optPlat.isPresent()) {
                model.addAttribute("plat", optPlat.get());
            }
        } else if (!action.equals("del")) {
            action = "";
        }
        model.addAttribute("action", action);

        return "plats";
    }

    /**
     * Supprime un plat identifié par son id.
     *
     * @param id l'identifiant du plat à supprimer
     * @param page numéro de la page courante
     * @param size nombre d'éléments par page
     * @param motCle mot-clé de recherche pour le maintien du filtre
     * @param categorieId l'identifiant de la catégorie (facultatif)
     * @param minCalories seuil minimal de calories (facultatif)
     * @param maxCalories seuil maximal de calories (facultatif)
     * @return redirection vers la liste des plats après suppression
     */
    @GetMapping("/platDelete")
    public String deletePlat(@RequestParam Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String motCle,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories,
            RedirectAttributes redirectAttributes) {

        // Suppression du plat à l'aide de son identifiant
        platRepository.deleteById(id);

        // Prépare les attributs de redirection pour l'alerte
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("motCle", motCle);
        redirectAttributes.addAttribute("action", "del");

        return "redirect:/plats";
    }

    /**
     * Affiche le formulaire d'édition ou de création d'un plat.
     *
     * Renvoie vers un formulaire pré-rempli si l'id est fourni, sinon instancie
     * un nouveau plat.
     *
     * @param model le modèle pour la vue
     * @param page numéro de la page pour le retour
     * @param size taille de la page pour le retour
     * @param motCle mot-clé pour le maintien du filtre
     * @param id identifiant du plat (0 pour créer un nouveau plat)
     * @return le nom de la vue du formulaire (platForm)
     */
    @GetMapping("/platEdit")
    public String editPlat(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String motCle,
            @RequestParam(defaultValue = "0") Long id) {

        // Récupération du plat existant ou création d'une nouvelle instance
        if (id != 0) {
            Optional<Plat> optPlat = platRepository.findById(id);
            if (optPlat.isPresent()) {
                model.addAttribute("plat", optPlat.get());
            } else {
                return "redirect:/plats";
            }
        } else {
            model.addAttribute("plat", new Plat());
        }
        // Ajoute la liste des catégories pour le formulaire
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("motCle", motCle);
        return "platForm";
    }

    /**
     * Enregistre (crée ou mise à jour) un plat.
     *
     * @param plat objet Plat à sauvegarder
     * @param page numéro de la page pour redirection
     * @param size taille de la page pour redirection
     * @param motCle mot-clé de recherche pour le maintien du filtre
     * @return redirection vers la liste des plats avec l'action effectuée
     */
    @PostMapping("/plats/save")
    public String savePlat(Plat plat,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "") String motCle,
            RedirectAttributes redirectAttributes) {

        // Détermine l'action (création ou modification)
        String action = (plat.getId() != null ? "mod" : "new");
        platRepository.save(plat);

        // Prépare les attributs de redirection
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("motCle", motCle);
        redirectAttributes.addAttribute("action", action);
        redirectAttributes.addAttribute("id", plat.getId());

        return "redirect:/plats";
    }

    /**
     * Affiche le détail d'un plat.
     *
     * @param id identifiant du plat à afficher
     * @param page numéro de la page
     * @param size taille de la page
     * @param motCle mot-clé pour maintien du filtre
     * @param model modèle pour la vue
     * @return la vue détaillée du plat ou redirection vers la liste si non
     * trouvé
     */
    @GetMapping("/platDetail/{id}")
    public String showPlatDetail(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String motCle,
            Model model) {
        Optional<Plat> optPlat = platRepository.findById(id);
        if (optPlat.isPresent()) {
            model.addAttribute("plat", optPlat.get());
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("motCle", motCle);
            return "platDetail";
        }
        return "redirect:/plats";
    }
}
