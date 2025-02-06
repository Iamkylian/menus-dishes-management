package fr.iut.prjspring2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.iut.prjspring2025.model.Categorie;

/**
 * Repository pour l'entité Categorie.
 *
 * Bien qu'il n'y ait pas de CRUD complet prévu sur Categorie (les catégories
 * sont définies à l'avance), cette interface étend JpaRepository afin de
 * permettre la lecture (et éventuellement quelques opérations) sur la table
 * "categorie" de la base de données prjspring2025. 
 * Cela est particulièrement utile pour associer une catégorie à un plat.
 *
 */

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
