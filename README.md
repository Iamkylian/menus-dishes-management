# Projet SpringBoot - Gestion de Menus et Plats

| Version | Date | Auteur | Module |
|---------|------|--------|--------|
| 1.0 | 9 février 2025 | Kylian GACHET 2B | R6.A.05 Développement avancé |

## Sommaire

- [Projet SpringBoot - Gestion de Menus et Plats](#projet-springboot---gestion-de-menus-et-plats)
  - [Sommaire](#sommaire)
  - [Présentation du projet](#présentation-du-projet)
  - [Fonctionnalités demandées et implémentées](#fonctionnalités-demandées-et-implémentées)
  - [Fonctionnalités demandées mais non implémentées](#fonctionnalités-demandées-mais-non-implémentées)
  - [Fonctionnalités non demandées mais ajoutées (bonus)](#fonctionnalités-non-demandées-mais-ajoutées-bonus)
  - [Conclusion](#conclusion)

## Présentation du projet
Le projet **Gestion de Menus et Plats** est une application web développée avec Spring Boot, conçue pour faciliter la gestion des menus et des plats d'un restaurant. En s'appuyant sur le modèle MVC, l'application intègre les technologies suivantes :
- **Spring MVC** pour le traitement des requêtes HTTP et la navigation entre les vues.
- **Thymeleaf** pour la génération dynamique des pages HTML.
- **Spring Data JPA** pour la persistance des données.
- **MariaDB** (ou MySQL) pour la gestion de la base de données.
- **Bootstrap** et **Font Awesome** pour une interface utilisateur moderne, responsive et ergonomique.

L'application permet de créer, modifier, supprimer et afficher des menus et des plats de manière intuitive, tout en offrant des fonctionnalités comme le filtrage, la pagination et le calcul automatique de valeurs nutritionnelles.

## Fonctionnalités demandées et implémentées

**Toutes les fonctionnalités** demandées ont été **implémentées**, soit :

- **Gestion des plats** :
  - **CRUD** : création, lecture, mise à jour et suppression des plats.
  - **Système de filtrage et de pagination** lors de l'affichage de la liste des plats : par mot-clé (nom du plat), par catégorie (entrée, plat, dessert), par tranche de calories (Min, Max), de lipides (Min, Max), de glucides (Min, Max) et de protéines (Min, Max).
- **Gestion des menus** :
  - **CRUD** : création, modification, suppression et affichage des menus.
  - Pour la **visualisation de la liste des menus** : 
    - **Calcul automatique du nombre total de calories** d'un menu (somme des calories de chacun des plats qui le composent).
    - **Affichage du contenu de chaque menu** (la liste de ses plats).
    - **Mise en place (optionnelle) de filtres et de pagination sur la liste des menus** : par mot-clé (nom du menu), par tranche de prix (Min, Max) et par tranche de calories (Min, Max).
  - Pour la **création/modification d'un menu** :
    - **Affectation de plats** à un menu.
      - **Filtrage des plats disponibles** : par catégorie (entrée, plat, dessert) et par tranche de calories (Min, Max).
  - Pour la **constitution et l'édition d'un menu** :
    - **Sélection de plats** : lors de la constitution d'un menu, l'utilisateur peut sélectionner les plats qu'il souhaite ajouter à son menu.
      - **Intégrer un système de filtres** lors de la sélection des plats :
        - Filtrage par catégorie.
        - Filtrage par nombre de calories (minimum, maximum), de lipides (minimum, maximum), de glucides (minimum, maximum) et de protéines (minimum, maximum).

## Fonctionnalités demandées mais non implémentées

**Aucune fonctionnalité non implémentée**.

## Fonctionnalités non demandées mais ajoutées (bonus)
- **Visualisation graphique** :
  - Intégration de Chart.js pour représenter graphiquement la répartition des nutriments dans un menu et dans un plat.
- **Système de tri** (différent du filtrage) sur les colonnes de la liste des plats et des menus :
  - **Pour la liste des plats** :
    - **Tri par nom** : les plats peuvent être triés par nom, par ordre alphabétique croissant ou décroissant.
    - **Tri par catégorie** : les plats peuvent être triés par catégorie, par ordre alphabétique croissant ou décroissant.
    - **Tri par nombre de calories** : les plats peuvent être triés par nombre de calories, du plus élevé au plus faible et inversement.
    - **Tri par nombre de lipides** : les plats peuvent être triés par nombre de lipides, du plus élevé au plus faible et inversement.
    - **Tri par nombre de glucides** : les plats peuvent être triés par nombre de glucides, du plus élevé au plus faible et inversement.
    - **Tri par nombre de protéines** : les plats peuvent être triés par nombre de protéines, du plus élevé au plus faible et inversement.
  - **Pour la liste des menus** :
    - **Tri par nom** : les menus peuvent être triés par nom, par ordre alphabétique croissant ou décroissant.
    - **Tri par prix** : les menus peuvent être triés par prix, du plus cher au moins cher et inversement.
    - **Tri par nombre de calories total** : les menus peuvent être triés par nombre de calories total, du plus élevé au plus faible et inversement.

- **Alertes** :
  - **Création d'un menu et/ou d'un plat** : une alerte est affichée pour informer l'utilisateur que le menu ou le plat a été créé avec succès.
  - **Modification d'un menu et/ou d'un plat** : une alerte est affichée pour informer l'utilisateur que le menu ou le plat a été modifié avec succès.
  - **Suppression d'un menu et/ou d'un plat** : une alerte est affichée pour informer l'utilisateur que le menu ou le plat a été supprimé avec succès.

- **Mise en évidence de la page active** (considéré comme une fonctionnalité bonus ?) : sur les pages de liste des plats et de liste des menus, la page active est mise en évidence.

## Conclusion
En conclusion, malgré le délai très court de quelques jours, ce projet de gestion des menus et des plats d'un restaurant nous a permis de mettre en pratique les concepts enseignés. Il a permis de mettre en œuvre, de manière concrète, les fonctionnalités essentielles tout en intégrant des améliorations bonus qui renforcent l'ergonomie de l'application. 
En respectant scrupuleusement les contraintes techniques (port 8086, base de données prjspring2025, etc.), cette réalisation se positionne comme une base solide et évolutive, ouvrant la voie à de futures optimisations et ajouts.