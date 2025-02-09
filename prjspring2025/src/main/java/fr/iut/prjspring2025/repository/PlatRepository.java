package fr.iut.prjspring2025.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.iut.prjspring2025.model.Plat;

/**
 * Repository pour la gestion des plats en base de données.
 *
 * Fournit les fonctionnalités suivantes : 
 * - Opérations CRUD standard via JpaRepository 
 * - Requêtes personnalisées pour le filtrage multicritères 
 * - Support de la pagination et du tri
 * - Recherche par critères nutritionnels
 *
 * @see Plat
 * @see JpaRepository
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
    Page<Plat> rechercher(@Param("x") String motCle, Pageable pageable);

    /**
     * Recherche paginée des plats en appliquant divers filtres.
     *
     * Filtre selon la catégorie, le nombre de calories et un mot-clé sur le
     * nom.
     *
     * @param categorieId identifiant de la catégorie (optionnel)
     * @param minCalories calories minimales (optionnel)
     * @param maxCalories calories maximales (optionnel)
     * @param minLipides lipides minimales (optionnel)
     * @param maxLipides lipides maximales (optionnel)
     * @param minGlucides glucides minimales (optionnel)
     * @param maxGlucides glucides maximales (optionnel)
     * @param minProteines protéines minimales (optionnel)
     * @param maxProteines protéines maximales (optionnel)
     * @param motCle mot-clé pour la recherche dans le nom
     * @param pageable objet Pageable pour la pagination
     * @return une Page de plats filtrés
     */
    @Query("SELECT p FROM Plat p WHERE "
            + "(:categorieId IS NULL OR p.categorie.id = :categorieId) AND "
            + "(:minCalories IS NULL OR p.nbCalories >= :minCalories) AND "
            + "(:maxCalories IS NULL OR p.nbCalories <= :maxCalories) AND "
            + "(:minLipides IS NULL OR p.nbLipides >= :minLipides) AND "
            + "(:maxLipides IS NULL OR p.nbLipides <= :maxLipides) AND "
            + "(:minGlucides IS NULL OR p.nbGlucides >= :minGlucides) AND "
            + "(:maxGlucides IS NULL OR p.nbGlucides <= :maxGlucides) AND "
            + "(:minProteines IS NULL OR p.nbProteines >= :minProteines) AND "
            + "(:maxProteines IS NULL OR p.nbProteines <= :maxProteines) AND "
            + "(:motCle = '' OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :motCle, '%')))")
    Page<Plat> findFiltered(@Param("categorieId") Long categorieId,
            @Param("minCalories") Integer minCalories,
            @Param("maxCalories") Integer maxCalories,
            @Param("minLipides") Integer minLipides,
            @Param("maxLipides") Integer maxLipides,
            @Param("minGlucides") Integer minGlucides,
            @Param("maxGlucides") Integer maxGlucides,
            @Param("minProteines") Integer minProteines,
            @Param("maxProteines") Integer maxProteines,
            @Param("motCle") String motCle,
            Pageable pageable);

}
