package fr.iut.prjspring2025.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entité représentant une catégorie de plats.
 * 
 * Cette classe est mappée sur la table "categorie" de la base de données prjspring2025.
 * Elle définit les propriétés et les méthodes pour manipuler les catégories de plats.
 */
@Entity
@Table(name = "categorie")

public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;

    // Constructeur par défaut
    public Categorie() {
    }

    // Constructeur avec paramètre
    public Categorie(String nom) {
        this.nom = nom;
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
} 