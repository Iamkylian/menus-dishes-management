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
 * Cette classe contient toutes les informations détaillées d'un plat telles que
 * le nom, le nombre de calories, glucides, lipides, protéines, ainsi que la
 * catégorie associée.
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

    @Column(name = "nb_lipides")
    private int nbLipides;

    @Column(name = "nb_proteines")
    private int nbProteines;

    @Column(name = "nb_glucides")
    private int nbGlucides;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    /**
     * Constructeur par défaut.
     */
    public Plat() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param nom le nom du plat
     * @param nbCalories le nombre de calories
     * @param nbGlucides le nombre de glucides
     * @param nbLipides le nombre de lipides
     * @param nbProteines le nombre de protéines
     * @param categorie la catégorie associée au plat
     */
    public Plat(String nom, int nbCalories, int nbGlucides, int nbLipides, int nbProteines, Categorie categorie) {
        this.nom = nom;
        this.nbCalories = nbCalories;
        this.nbGlucides = nbGlucides;
        this.nbLipides = nbLipides;
        this.nbProteines = nbProteines;
        this.categorie = categorie;
    }

    /**
     * @return l'identifiant du plat
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du plat.
     *
     * @param id l'identifiant à définir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return le nom du plat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du plat.
     *
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return le nombre de calories
     */
    public int getNbCalories() {
        return nbCalories;
    }

    /**
     * Définit le nombre de calories du plat.
     *
     * @param nbCalories le nombre de calories à définir
     */
    public void setNbCalories(int nbCalories) {
        this.nbCalories = nbCalories;
    }

    /**
     * @return le nombre de glucides
     */
    public int getNbGlucides() {
        return nbGlucides;
    }

    /**
     * Définit le nombre de glucides du plat.
     *
     * @param nbGlucides le nombre de glucides à définir
     */
    public void setNbGlucides(int nbGlucides) {
        this.nbGlucides = nbGlucides;
    }

    /**
     * @return le nombre de lipides
     */
    public int getNbLipides() {
        return nbLipides;
    }

    /**
     * Définit le nombre de lipides du plat.
     *
     * @param nbLipides le nombre de lipides à définir
     */
    public void setNbLipides(int nbLipides) {
        this.nbLipides = nbLipides;
    }

    /**
     * @return le nombre de protéines
     */
    public int getNbProteines() {
        return nbProteines;
    }

    /**
     * Définit le nombre de protéines du plat.
     *
     * @param nbProteines le nombre de protéines à définir
     */
    public void setNbProteines(int nbProteines) {
        this.nbProteines = nbProteines;
    }

    /**
     * @return la catégorie associée au plat
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * Définit la catégorie à laquelle appartient le plat.
     *
     * @param categorie la catégorie à affecter
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
