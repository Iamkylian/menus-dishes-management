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
 * Ce contrôleur gère l'affichage, la recherche, l'édition, la création et la
 * suppression des plats. Il intègre la gestion de la pagination ainsi que
 * l'application de filtres.
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
            @RequestParam(defaultValue = "") String motCle,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "act", defaultValue = "") String action,
            @RequestParam(name = "id", defaultValue = "0") Long id) {

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
            }
        }

        // Configuration de la pagination avec le tri
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        // Récupération des plats filtrés depuis le repository
        Page<Plat> pagePlats = platRepository.findFiltered(categorieId, minCalories, maxCalories, motCle, pageable);

        // Ajout des attributs au modèle pour la vue Thymeleaf
        model.addAttribute("plats", pagePlats.getContent());
        model.addAttribute("page", pagePlats);
        model.addAttribute("sort", sort);
        model.addAttribute("motCle", motCle);
        model.addAttribute("categorieId", categorieId);
        model.addAttribute("minCalories", minCalories);
        model.addAttribute("maxCalories", maxCalories);
        // Fournit la liste des catégories pour le filtre
        model.addAttribute("categories", categorieRepository.findAll());

        // Gestion de l'affichage du formulaire d'édition si requis
        if (id > 0 && (action.equals("new") || action.equals("mod"))) {
            model.addAttribute("plat", platRepository.getReferenceById(id));
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
            @RequestParam(required = false) Integer maxCalories) {

        // Suppression du plat à l'aide de son identifiant
        platRepository.deleteById(id);
        return String.format("redirect:/plats?act=del&page=%d&size=%d&motCle=%s&categorieId=%s&minCalories=%s&maxCalories=%s",
                page, size, motCle,
                categorieId != null ? categorieId : "",
                minCalories != null ? minCalories : "",
                maxCalories != null ? maxCalories : "");
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
     * @param mc mot-clé pour le maintien du filtre
     * @param id identifiant du plat (0 pour créer un nouveau plat)
     * @return le nom de la vue du formulaire (platForm)
     */
    @GetMapping("/platEdit")
    public String editPlat(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String mc,
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
        model.addAttribute("motCle", mc);
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
            @RequestParam String motCle) {
        // Déduit l'action en fonction de la présence ou non de l'id
        String action = (plat.getId() != null ? "mod" : "new");
        // Sauvegarde du plat dans la base de données
        platRepository.save(plat);
        return "redirect:/plats?act=" + action + "&id=" + plat.getId()
                + "&page=" + page + "&size=" + size + "&motCle=" + motCle;
    }

    /**
     * Affiche le détail d'un plat.
     *
     * @param id identifiant du plat à afficher
     * @param page numéro de la page
     * @param size taille de la page
     * @param mc mot-clé pour maintien du filtre
     * @param model modèle pour la vue
     * @return la vue détaillée du plat ou redirection vers la liste si non
     * trouvé
     */
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
