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

import fr.iut.prjspring2025.model.Plat;
import fr.iut.prjspring2025.repository.CategorieRepository;
import fr.iut.prjspring2025.repository.PlatRepository;

/**
 * Contrôleur pour l'entité Plat.
 *
 * Gère les opérations de lecture (avec pagination et recherche par mot-clé),
 * suppression, édition et enregistrement d'un plat.
 */
@Controller
public class PlatController {

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Affiche la liste paginée des plats.
     *
     * @param model le modèle pour la vue
     * @param page le numéro de page (défaut 0)
     * @param size le nombre d'éléments par page (défaut 10)
     * @param mc le mot-clé de recherche (défaut chaîne vide)
     * @param sort le paramètre de tri (défaut vide)
     * @return le nom de la vue affichant la liste des plats (ex : "plats")
     */
    @GetMapping("/plats")
    public String listePlats(Model model,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories,
            @RequestParam(defaultValue = "") String motCle,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "act", defaultValue = "") String action,
            @RequestParam(name = "id", defaultValue = "0") Long id) {

        // Définition de l'ordre de tri en fonction du paramètre "sort"
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
            }
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Plat> pagePlats = platRepository.findFiltered(categorieId, minCalories, maxCalories, motCle, pageable);

        model.addAttribute("plats", pagePlats.getContent());
        model.addAttribute("page", pagePlats);
        model.addAttribute("sort", sort);
        model.addAttribute("motCle", motCle);
        model.addAttribute("categorieId", categorieId);
        model.addAttribute("minCalories", minCalories);
        model.addAttribute("maxCalories", maxCalories);
        // Fournir la liste complète des catégories pour le formulaire de filtrage
        model.addAttribute("categories", categorieRepository.findAll());

        if (id > 0 && (action.equals("new") || action.equals("mod"))) {
            model.addAttribute("plat", platRepository.getReferenceById(id));
        } else if (!action.equals("del")) {
            action = "";
        }
        model.addAttribute("action", action);

        return "plats";
    }

    /**
     * Supprime un plat en fonction de son identifiant.
     *
     * @param id l'identifiant du plat à supprimer
     * @param page le numéro de la page courante
     * @param size la taille de la page
     * @param motCle le mot-clé de recherche pour maintenir le filtre après
     * suppression
     * @param categorieId l'identifiant de la catégorie pour maintenir le filtre
     * après suppression
     * @param minCalories le minimum de calories pour maintenir le filtre après
     * suppression
     * @param maxCalories le maximum de calories pour maintenir le filtre après
     * suppression
     * @return redirection vers la liste des plats
     */
    @GetMapping("/platDelete")
    public String deletePlat(@RequestParam Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String motCle,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories) {

        platRepository.deleteById(id);
        return String.format("redirect:/plats?act=del&page=%d&size=%d&motCle=%s&categorieId=%s&minCalories=%s&maxCalories=%s",
                page, size, motCle,
                categorieId != null ? categorieId : "",
                minCalories != null ? minCalories : "",
                maxCalories != null ? maxCalories : "");
    }

    /**
     * Affiche le formulaire d'édition (ou de création) d'un plat.
     *
     * Si l'identifiant passé est supérieur à 0, le plat correspondant est
     * récupéré, sinon un nouveau plat est créé.
     *
     * @param model le modèle pour la vue
     * @param page numéro de page pour l'affichage (pour retour vers la liste)
     * @param size taille de la page
     * @param mc mot-clé de recherche pour le maintien du filtre
     * @param id l'identifiant du plat (0 pour un nouveau plat)
     * @return le nom de la vue du formulaire (ex : "platForm")
     */
    @GetMapping("/platEdit")
    public String editPlat(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String mc,
            @RequestParam(defaultValue = "0") Long id) {

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
        // Fournit la liste des catégories au formulaire pour permettre la sélection
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("motCle", mc);
        return "platForm"; // Vue Thymeleaf : platForm.html
    }

    /**
     * Enregistre un plat (création ou mise à jour).
     *
     * @param plat le plat à enregistrer
     * @param page numéro de page pour redirection
     * @param size taille de page
     * @param mc mot-clé de recherche pour redirection
     * @param redirectAttributes pour transmettre les paramètres lors de la
     * redirection
     * @return redirection vers la liste des plats
     */
    @PostMapping("/plats/save")
    public String savePlat(Plat plat,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String motCle) {
        String action = (plat.getId() != null ? "mod" : "new");
        platRepository.save(plat);
        return "redirect:/plats?act=" + action + "&id=" + plat.getId()
                + "&page=" + page + "&size=" + size + "&motCle=" + motCle;
    }

    @GetMapping("/platDetail/{id}")
    public String showPlatDetail(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String mc,
            Model model) {
        Optional<Plat> optPlat = platRepository.findById(id);
        if (optPlat.isPresent()) {
            model.addAttribute("plat", optPlat.get());
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("motCle", mc);
            return "platDetail";
        }
        return "redirect:/plats";
    }
}
