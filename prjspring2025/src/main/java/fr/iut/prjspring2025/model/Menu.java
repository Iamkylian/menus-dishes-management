package fr.iut.prjspring2025.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entité représentant un menu du restaurant.
 *
 * Cette classe définit la structure d'un menu avec :
 * - Ses informations de base (nom, description, prix)
 * - La liste des plats qui le composent
 * - Des méthodes de calcul pour les valeurs nutritionnelles totales
 * 
 * Elle est mappée sur la table "menu" de la base de données prjspring2025.
 *
 * @see Plat
 */

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private double prix;

    @ManyToMany
    @JoinTable(
            name = "menu_plat",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "plat_id")
    )
    private Set<Plat> plats = new HashSet<>();

    /**
     * Constructeur par défaut.
     */
    public Menu() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param nom nom du menu
     * @param description description du menu
     * @param prix prix du menu
     */
    public Menu(String nom, String description, double prix) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    /**
     * @return l'identifiant du menu
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du menu.
     *
     * @param id l'identifiant à définir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return le nom du menu
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du menu.
     *
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return la description du menu
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description du menu.
     *
     * @param description la description à définir
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return le prix du menu
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Définit le prix du menu.
     *
     * @param prix le prix à définir
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * @return l'ensemble des plats composant le menu
     */
    public Set<Plat> getPlats() {
        return plats;
    }

    /**
     * Définit la liste des plats du menu.
     *
     * @param plats set des plats à associer au menu
     */
    public void setPlats(Set<Plat> plats) {
        this.plats = plats;
    }

    /**
     * Calcule le total des calories du menu.
     *
     * @return la somme des calories de chaque plat
     */
    public int getTotalCalories() {
        return plats.stream().mapToInt(plat -> plat.getNbCalories()).sum();
    }

    /**
     * Calcule le total des glucides du menu.
     *
     * @return la somme des glucides de chaque plat
     */
    public int getTotalGlucides() {
        return plats.stream().mapToInt(Plat::getNbGlucides).sum();
    }

    /**
     * Calcule le total des lipides du menu.
     *
     * @return la somme des lipides de chaque plat
     */
    public int getTotalLipides() {
        return plats.stream().mapToInt(Plat::getNbLipides).sum();
    }

    /**
     * Calcule le total des protéines du menu.
     *
     * @return la somme des protéines de chaque plat
     */
    public int getTotalProteines() {
        return plats.stream().mapToInt(Plat::getNbProteines).sum();
    }

}
