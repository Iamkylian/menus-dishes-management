package fr.iut.prjspring2025.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.iut.prjspring2025.model.Plat;

/**
 * Repository pour l'entité Plat.
 *
 * Cette interface étend JpaRepository, ce qui permet d'obtenir automatiquement
 * un ensemble complet de méthodes CRUD (création, lecture, mise à jour,
 * suppression) pour l'entité Plat. Elle facilite l'accès aux données de la
 * table "plat" de la base de données prjspring2025.
 *
 */
public interface PlatRepository extends JpaRepository<Plat, Long> {

    /**
     * Rechercher de manière paginée les plats dont le nom contient le mot-clé
     * fourni. La requête utilise l'opérateur LIKE pour filtrer les résultats.
     *
     * @param x Le mot-clé utilisé dans la recherche. Par exemple, "%motCle%".
     * @param pageable L'objet Pageable contenant les informations de
     * pagination.
     * @return Une Page contenant les plats correspondant au filtre.
     */
    @Query("SELECT p FROM Plat p WHERE p.nom LIKE :x")
    Page<Plat> rechercher(@Param("x") String mc, Pageable pageable);

    // Méthode pour appliquer des filtres sur les plats
    @Query("SELECT p FROM Plat p "
            + "WHERE (:categorieId IS NULL OR p.categorie.id = :categorieId) "
            + "AND (:minCalories IS NULL OR p.nbCalories >= :minCalories) "
            + "AND (:maxCalories IS NULL OR p.nbCalories <= :maxCalories) "
            + "AND ((:motCle IS NULL OR :motCle = '') OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :motCle, '%')))")
    Page<Plat> findFiltered(
            @Param("categorieId") Long categorieId,
            @Param("minCalories") Integer minCalories,
            @Param("maxCalories") Integer maxCalories,
            @Param("motCle") String motCle,
            Pageable pageable);
}
