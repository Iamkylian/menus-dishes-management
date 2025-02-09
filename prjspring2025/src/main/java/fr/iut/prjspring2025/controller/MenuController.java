package fr.iut.prjspring2025.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import fr.iut.prjspring2025.model.Menu;
import fr.iut.prjspring2025.model.Plat;
import fr.iut.prjspring2025.repository.CategorieRepository;
import fr.iut.prjspring2025.repository.MenuRepository;
import fr.iut.prjspring2025.repository.PlatRepository;

/**
 * Contrôleur pour l'entité Menu.
 *
 * Ce contrôleur fournit les fonctionnalités CRUD (Create, Read, Update, Delete)
 * pour les menus, ainsi que des fonctionnalités avancées comme : - Le filtrage
 * multicritères (nom, prix, calories) - La pagination des résultats - Le tri
 * personnalisé - L'association de plats aux menus
 */
@Controller
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Affiche la liste paginée des menus avec application des filtres.
     *
     * @param model modèle pour la vue
     * @param page numéro de la page
     * @param size taille de la page
     * @param filtre chaîne de filtre sur le nom du menu ou des plats
     * @param prixMin prix minimum pour filtrer
     * @param prixMax prix maximum pour filtrer
     * @param calMin calories minimales totales pour filtrer
     * @param calMax calories maximales totales pour filtrer
     * @param sort critère de tri
     * @param action action en cours (ex. "new", "mod", "del")
     * @param id identifiant du menu à éditer ou afficher
     * @return le nom de la vue affichant la liste des menus
     */
    @GetMapping("/menus")
    public String listMenus(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String filtre,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false) Integer calMin,
            @RequestParam(required = false) Integer calMax,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "") String action,
            @RequestParam(defaultValue = "0") Long id) {

        // Définition de l'ordre de tri pour les attributs simples
        Sort sortOrder = Sort.unsorted();
        if (sort != null) {
            switch (sort) {
                case "nomAsc" ->
                    sortOrder = Sort.by("nom").ascending();
                case "nomDesc" ->
                    sortOrder = Sort.by("nom").descending();
                case "prixAsc" ->
                    sortOrder = Sort.by("prix").ascending();
                case "prixDesc" ->
                    sortOrder = Sort.by("prix").descending();
            }
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Récupération des menus filtrés via le repository
        Page<Menu> menuPage = menuRepository.findFiltered(filtre, prixMin, prixMax, calMin, calMax, pageable);
        List<Menu> menus = new ArrayList<>(menuPage.getContent());

        // Tri en mémoire par total de calories si ce critère est demandé
        if (sort != null) {
            switch (sort) {
                case "calAsc" ->
                    menus.sort((m1, m2) -> Integer.compare(m1.getTotalCalories(), m2.getTotalCalories()));
                case "calDesc" ->
                    menus.sort((m1, m2) -> Integer.compare(m2.getTotalCalories(), m1.getTotalCalories()));
            }
        }

        // Prépare l'affichage du formulaire d'édition si besoin
        if (id > 0 && (action.equals("new") || action.equals("mod"))) {
            model.addAttribute("menu", menuRepository.getReferenceById(id));
        } else if (!action.equals("del")) {
            action = "";
        }
        model.addAttribute("action", action);

        model.addAttribute("menus", menus);
        model.addAttribute("page", menuPage);
        model.addAttribute("filtre", filtre);
        model.addAttribute("sort", sort);
        model.addAttribute("prixMin", prixMin);
        model.addAttribute("prixMax", prixMax);
        model.addAttribute("calMin", calMin);
        model.addAttribute("calMax", calMax);

        return "menus";
    }

    /**
     * Affiche le formulaire d'édition/création d'un menu avec possibilité de
     * filtrer les plats disponibles.
     *
     * @param model modèle pour la vue
     * @param id identifiant du menu (0 si création)
     * @param page numéro de la page pour le retour
     * @param size taille de la page pour le retour
     * @param filtre filtre actif pour le maintien du critère
     * @param categorieId identifiant de la catégorie pour filtrer les plats
     * (optionnel)
     * @param minCalories seuil de calories minimum (optionnel)
     * @param maxCalories seuil de calories maximum (optionnel)
     * @param minLipides seuil de lipides minimum (optionnel)
     * @param maxLipides seuil de lipides maximum (optionnel)
     * @param minGlucides seuil de glucides minimum (optionnel)
     * @param maxGlucides seuil de glucides maximum (optionnel)
     * @param minProteines seuil de protéines minimum (optionnel)
     * @param maxProteines seuil de protéines maximum (optionnel)
     * @return le nom de la vue du formulaire (menuForm)
     */
    @GetMapping("/edit")
    public String editMenu(Model model,
            @RequestParam(defaultValue = "0") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String filtre,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories,
            @RequestParam(required = false) Integer minLipides,
            @RequestParam(required = false) Integer maxLipides,
            @RequestParam(required = false) Integer minGlucides,
            @RequestParam(required = false) Integer maxGlucides,
            @RequestParam(required = false) Integer minProteines,
            @RequestParam(required = false) Integer maxProteines) {

        // Récupération du menu existant ou instanciation d'un nouveau menu
        if (id != 0) {
            Optional<Menu> optMenu = menuRepository.findById(id);
            if (optMenu.isPresent()) {
                model.addAttribute("menu", optMenu.get());
            } else {
                return "redirect:/menus?page=" + page + "&size=" + size + "&filtre=" + filtre;
            }
        } else {
            model.addAttribute("menu", new Menu());
        }

        // Récupération de la liste des plats filtrés avec les nouveaux critères
        List<Plat> plats;
        if (categorieId != null || minCalories != null || maxCalories != null
                || minLipides != null || maxLipides != null
                || minGlucides != null || maxGlucides != null
                || minProteines != null || maxProteines != null) {
            plats = platRepository.findFiltered(
                    categorieId, minCalories, maxCalories,
                    minLipides, maxLipides,
                    minGlucides, maxGlucides,
                    minProteines, maxProteines,
                    "", Pageable.unpaged()).getContent();
        } else {
            plats = platRepository.findAll();
        }
        model.addAttribute("plats", plats);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("filtre", filtre);
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("minCalories", minCalories);
        model.addAttribute("maxCalories", maxCalories);
        model.addAttribute("categorieId", categorieId);
        // Ajoutez aussi les nouveaux attributs dans le modèle
        model.addAttribute("minLipides", minLipides);
        model.addAttribute("maxLipides", maxLipides);
        model.addAttribute("minGlucides", minGlucides);
        model.addAttribute("maxGlucides", maxGlucides);
        model.addAttribute("minProteines", minProteines);
        model.addAttribute("maxProteines", maxProteines);

        return "menuForm";
    }

    /**
     * Sauvegarde un menu (création ou modification).
     *
     * @param menu le menu à sauvegarder
     * @param platIds liste des identifiants des plats à associer au menu
     * @param page numéro de la page pour la redirection
     * @param size taille de la page pour la redirection
     * @param filtre filtre actif à conserver
     * @param redirectAttributes attributs de redirection
     * @return redirection vers la liste des menus avec message de confirmation
     */
    @PostMapping("/menus/save")
    public String saveMenu(Menu menu,
            @RequestParam(required = false) List<Long> platIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String filtre,
            RedirectAttributes redirectAttributes) {

        // Si des plats sont affectés, les associe au menu
        if (platIds != null) {
            Set<Plat> selectedPlats = new HashSet<>();
            for (Long platId : platIds) {
                platRepository.findById(platId).ifPresent(selectedPlats::add);
            }
            menu.setPlats(selectedPlats);
        } else {
            menu.setPlats(new HashSet<>());
        }

        // Détermine l'action (création ou modification) et sauvegarde le menu
        String action = (menu.getId() != null ? "mod" : "new");
        menuRepository.save(menu);

        // Prépare les attributs de redirection
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("filtre", filtre);
        redirectAttributes.addAttribute("action", action);
        redirectAttributes.addAttribute("id", menu.getId());

        return "redirect:/menus";
    }

    /**
     * Supprime un menu identifié par son id.
     *
     * @param id l'identifiant du menu à supprimer
     * @param page numéro de la page pour redirection
     * @param size taille de la page pour redirection
     * @param filtre filtre actif pour le maintien du critère
     * @param redirectAttributes attributs pour la redirection
     * @return redirection vers la liste des menus après suppression
     */
    @GetMapping("/menuDelete")
    public String deleteMenu(@RequestParam Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String filtre,
            RedirectAttributes redirectAttributes) {

        menuRepository.deleteById(id);

        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("filtre", filtre);
        redirectAttributes.addAttribute("action", "del");

        return "redirect:/menus";
    }

    /**
     * Affiche le détail d'un menu.
     *
     * @param id identifiant du menu à afficher
     * @param page numéro de la page
     * @param size taille de la page
     * @param filtre filtre actif pour le maintien du critère
     * @param model modèle pour la vue
     * @return la vue affichant les détails du menu ou redirection si non trouvé
     */
    @GetMapping("/menuDetail/{id}")
    public String showMenuDetail(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String filtre,
            Model model) {

        Optional<Menu> optMenu = menuRepository.findById(id);
        if (optMenu.isPresent()) {
            model.addAttribute("menu", optMenu.get());
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("filtre", filtre);
            return "menuDetail";
        }
        return "redirect:/menus";
    }
}
