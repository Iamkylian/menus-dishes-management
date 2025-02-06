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
 * Entité représentant un menu de plats.
 *
 * Cette classe est mappée sur la table "menu" de la base de données
 * prjspring2025. Elle définit les propriétés et les méthodes pour manipuler les
 * menus de plats.
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

    // Constructeur par défaut
    public Menu() {
    }

    // Constructeur avec paramètres
    public Menu(String nom, String description, double prix) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Set<Plat> getPlats() {
        return plats;
    }

    public void setPlats(Set<Plat> plats) {
        this.plats = plats;
    }

    /*
     * Calcul du total de calories : somme des nbCalories de chaque plat
     */
    public int getTotalCalories() {
        return plats.stream().mapToInt(plat -> plat.getNbCalories()).sum();
    }

    public int getTotalGlucides() {
        return plats.stream()
                .mapToInt(Plat::getNbGlucides)
                .sum();
    }

    public int getTotalLipides() {
        return plats.stream()
                .mapToInt(Plat::getNbLipides)
                .sum();
    }

    public int getTotalProteines() {
        return plats.stream()
                .mapToInt(Plat::getNbProteines)
                .sum();
    }

}
