package fr.iut.prjspring2025.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entité représentant un plat.
 * 
 * Cette classe est mappée sur la table "plat" de la base de données prjspring2025.
 * Elle définit les propriétés et les méthodes pour manipuler les plats.
 */
@Entity
@Table(name = "plat")
public class Plat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    @Column(name = "nb_calories")
    private int nbCalories;
    
    @Column(name = "nb_glucides")
    private int nbGlucides;
    
    @Column(name = "nb_lipides")
    private int nbLipides;
    
    @Column(name = "nb_proteines")
    private int nbProteines;
    
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    // Constructeur par défaut
    public Plat() {
    }

    // Constructeur avec paramètres
    public Plat(String nom, int nbCalories, int nbGlucides, int nbLipides, int nbProteines, Categorie categorie) {
        this.nom = nom;
        this.nbCalories = nbCalories;
        this.nbGlucides = nbGlucides;
        this.nbLipides = nbLipides;
        this.nbProteines = nbProteines;
        this.categorie = categorie;
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

    public int getNbCalories() {
        return nbCalories;
    }

    public void setNbCalories(int nbCalories) {
        this.nbCalories = nbCalories;
    }

    public int getNbGlucides() {
        return nbGlucides;
    }

    public void setNbGlucides(int nbGlucides) {
        this.nbGlucides = nbGlucides;
    }

    public int getNbLipides() {
        return nbLipides;
    }

    public void setNbLipides(int nbLipides) {
        this.nbLipides = nbLipides;
    }

    public int getNbProteines() {
        return nbProteines;
    }

    public void setNbProteines(int nbProteines) {
        this.nbProteines = nbProteines;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
} 