package fr.iut.prjspring2025.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.iut.prjspring2025.model.Menu;

/**
 * Repository pour l'entité Menu.
 *
 * Cette interface hérite de JpaRepository et fournit ainsi toutes les
 * opérations CRUD pour manipuler les menus dans la base de données. Cela permet
 * notamment de gérer la table "menu" de la base de données prjspring2025.
 *
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    @Query("SELECT DISTINCT m FROM Menu m "
            + "LEFT JOIN m.plats p "
            + "WHERE (:filtre = '' OR LOWER(m.nom) LIKE CONCAT('%', LOWER(:filtre), '%') OR LOWER(p.nom) LIKE CONCAT('%', LOWER(:filtre), '%')) "
            + "  AND (:prixMin IS NULL OR m.prix >= :prixMin) "
            + "  AND (:prixMax IS NULL OR m.prix <= :prixMax) "
            + "  AND (:calMin IS NULL OR COALESCE((SELECT SUM(p2.nbCalories) FROM m.plats p2), 0) >= :calMin) "
            + "  AND (:calMax IS NULL OR COALESCE((SELECT SUM(p2.nbCalories) FROM m.plats p2), 0) <= :calMax)")
    Page<Menu> findFiltered(@Param("filtre") String filtre,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax,
            @Param("calMin") Integer calMin,
            @Param("calMax") Integer calMax,
            Pageable pageable);
}
