package fr.iut.prjspring2025.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.iut.prjspring2025.model.Menu;

/**
 * Repository pour la gestion des menus en base de données.
 *
 * Cette interface étend JpaRepository et JpaSpecificationExecutor pour fournir :
 * - Les opérations CRUD standard
 * - Des requêtes personnalisées pour le filtrage avancé
 * - La gestion de la pagination et du tri
 *
 * @see Menu
 * @see JpaRepository

 * @see JpaSpecificationExecutor
 */

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    /**
     * Recherche les menus en appliquant différents critères de filtrage.
     *
     * Filtre sur le nom du menu (ou des plats associés), le prix et le total de
     * calories.
     *
     * @param filtre texte à rechercher dans le nom du menu ou des plats
     * @param prixMin prix minimal du menu
     * @param prixMax prix maximal du menu
     * @param calMin calories minimales totales du menu
     * @param calMax calories maximales totales du menu
     * @param pageable objet Pageable pour la pagination
     * @return une Page de Menu correspondant aux critères
     */
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
