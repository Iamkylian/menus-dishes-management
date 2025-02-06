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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * Gère les opérations de création, lecture, mise à jour et suppression d'un
 * menu. Lors de l'affichage, la liste des menus est paginée et peut inclure des
 * filtres (ex : par tranche de prix). Chaque menu affiche automatiquement le
 * total de calories (somme des nbCalories des plats le composant) et la liste
 * de ses plats. Lors de l'édition, il est possible d'affecter des plats au
 * menu.
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
     * Affiche la liste paginée des menus. La vue pourra ainsi afficher le total
     * de calories pour chaque menu et la liste de ses plats.
     *
     * @param model le modèle pour la vue
     * @param page numéro de page (défaut 0)
     * @param size taille de la page (défaut 10)
     * @param filtre filtre optionnel (ex : tranche de prix ou calories totales)
     * @param prixMin filtre optionnel pour la plage de prix minimales
     * @param prixMax filtre optionnel pour la plage de prix maximales
     * @param calMin filtre optionnel pour la plage de calories minimales
     * @param calMax filtre optionnel pour la plage de calories maximales
     * @param sort tri optionnel (ex : nomAsc, nomDesc, prixAsc, prixDesc,
     * calAsc, calDesc)
     * @param action action en cours (new, mod, del)
     * @param id identifiant du menu concerné
     * @return le nom de la vue affichant la liste des menus (ex : "menus")
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
            @RequestParam(name = "act", defaultValue = "") String action,
            @RequestParam(name = "id", defaultValue = "0") Long id) {

        // Définition de l'ordre de tri pour les attributs simples
        Sort sortOrder = Sort.unsorted();
        if (sort != null) {
            switch (sort) {
                case "nomAsc" -> sortOrder = Sort.by("nom").ascending();
                case "nomDesc" -> sortOrder = Sort.by("nom").descending();
                case "prixAsc" -> sortOrder = Sort.by("prix").ascending();
                case "prixDesc" -> sortOrder = Sort.by("prix").descending();
            }
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Utilisation de la méthode findFiltered pour appliquer les filtres sur le nom, le prix et (éventuellement) les calories
        Page<Menu> menuPage = menuRepository.findFiltered(filtre, prixMin, prixMax, calMin, calMax, pageable);
        List<Menu> menus = new ArrayList<>(menuPage.getContent());

        // Tri des menus par total de calories en mémoire si le tri demandé concerne les calories
        if (sort != null) {
            switch (sort) {
                case "calAsc" -> menus.sort((m1, m2) -> Integer.compare(m1.getTotalCalories(), m2.getTotalCalories()));
                case "calDesc" -> menus.sort((m1, m2) -> Integer.compare(m2.getTotalCalories(), m1.getTotalCalories()));
            }
        }

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

        return "menus"; // Vue Thymeleaf : menus.html
    }

    /**
     * Affiche le formulaire pour la création ou l'édition d'un menu. Permet
     * d'affecter des plats au menu.
     *
     * @param model le modèle pour la vue
     * @param id identifiant du menu à éditer (0 pour un nouveau menu)
     * @param page numéro de page (pour retour vers la liste)
     * @param size taille de la page
     * @param filtre filtre en cours (pour maintien du filtre lors de la
     * redirection)
     * @param categorieId filtre optionnel pour la catégorie
     * @param minCalories filtre optionnel pour la plage de calories minimales
     * @param maxCalories filtre optionnel pour la plage de calories maximales
     * @return le nom de la vue du formulaire (ex : "menuForm")
     */
    @GetMapping("/edit")
    public String editMenu(Model model,
            @RequestParam(defaultValue = "0") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String filtre,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories) {

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

        // Application des filtres sur les plats disponibles
        List<Plat> plats;
        if (categorieId != null || minCalories != null || maxCalories != null) {
            plats = platRepository.findFiltered(categorieId, minCalories, maxCalories, "", Pageable.unpaged()).getContent();
        } else {
            plats = platRepository.findAll();
        }
        model.addAttribute("plats", plats);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("filtre", filtre);

        // Ajout des catégories pour le filtre par catégorie dans la vue
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("minCalories", minCalories);
        model.addAttribute("maxCalories", maxCalories);
        model.addAttribute("categorieId", categorieId);
        return "menuForm"; // Vue Thymeleaf : menuForm.html
    }

    /**
     * Enregistre un menu (création ou mise à jour). Permet d'affecter les plats
     * sélectionnés au menu.
     *
     * @param menu le menu à enregistrer
     * @param platIds tableau des identifiants des plats affectés
     * @param page numéro de page (pour redirection)
     * @param size taille de la page (pour redirection)
     * @param filtre filtre en cours (pour redirection)
     * @param redirectAttributes attributs pour la redirection
     * @return redirection vers la liste des menus
     */
    @PostMapping("/menus/save")
    public String saveMenu(Menu menu,
            @RequestParam(required = false) Long[] platIds,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "") String filtre,
            RedirectAttributes redirectAttributes) {

        if (platIds != null) {
            Set<Plat> selectedPlats = new HashSet<>();
            for (Long platId : platIds) {
                platRepository.findById(platId).ifPresent(selectedPlats::add);
            }
            menu.setPlats(selectedPlats);
        } else {
            menu.setPlats(new HashSet<>());
        }

        String action = (menu.getId() != null ? "mod" : "new");
        menuRepository.save(menu);

        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("filtre", filtre);
        redirectAttributes.addAttribute("act", action);
        redirectAttributes.addAttribute("id", menu.getId());

        return "redirect:/menus";
    }

    /**
     * Supprime un menu en fonction de son identifiant.
     *
     * @param id identifiant du menu à supprimer
     * @param page numéro de page (pour redirection)
     * @param size taille de la page (pour redirection)
     * @param filtre filtre en cours (pour redirection)
     * @param redirectAttributes attributs pour la redirection
     * @return redirection vers la liste des menus
     */
    @RequestMapping(value = "/menus/delete", method = RequestMethod.GET)
    public String deleteMenu(@RequestParam Long id,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "") String filtre,
            RedirectAttributes redirectAttributes) {

        menuRepository.deleteById(id);

        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("filtre", filtre);
        redirectAttributes.addAttribute("act", "del");

        return "redirect:/menus";
    }

    @GetMapping("/menuDetail/{id}")
    public String showMenuDetail(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
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
