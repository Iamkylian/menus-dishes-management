package fr.iut.prjspring2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.iut.prjspring2025.model.Categorie;

/**
 * Repository pour la gestion des catégories de plats.
 *
 * Bien que les catégories soient principalement statiques (Entrée, Plat,
 * Dessert), ce repository permet : 
 * - La lecture des catégories existantes 
 * - L'association avec les plats 
 *
 * @see Categorie
 * @see JpaRepository
 */

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
