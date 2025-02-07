package fr.iut.prjspring2025.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entité représentant une catégorie de plats.
 * 
 * Cette classe permet de définir les catégories prédéfinies (ex. Entrée, Plat, Dessert)
 * auxquelles un plat peut être associé.
 */
@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;

    /**
     * Constructeur par défaut.
     */
    public Categorie() {
    }

    /**
     * Constructeur avec paramètre.
     * 
     * @param nom le nom de la catégorie
     */
    public Categorie(String nom) {
        this.nom = nom;
    }

    /**
     * @return l'identifiant de la catégorie
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la catégorie.
     * 
     * @param id l'identifiant à définir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return le nom de la catégorie
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de la catégorie.
     * 
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
} 