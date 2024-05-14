# PoseidonCapitalSolutions

## Introduction

PoseidonCapitalSolutions est une application Spring Boot développée pour gérer et suivre les opérations financières dans le domaine de la finance de marché. Elle permet aux utilisateurs de gérer des listes d'enchères, des points de courbe, des évaluations de risque, des règles de trading, et des informations de trading.


## Configuration requise

- Java 17
- Maven pour la gestion des dépendances et l'exécution du projet
- Spring Boot version 3.2.5
- MySQL version 8.0.36

## Technologies Utilisées

* Spring Boot pour faciliter le développement.
* Spring Security pour la sécurité et l'authentification.
* Hibernate / JPA pour la persistance des données.
* Thymeleaf pour les vues HTML dynamiques côté serveur.
* MySQL pour la gestion de la base de données.
* Bootstrap pour le design des interfaces.

## Installation

Pour configurer et exécuter ce projet :

1. Clonez le dépôt : `git clone https://github.com/maximedrouault/PoseidonCapitalSolutions.git`
2. Accédez au répertoire du projet : `cd PoseidonCapitalSolutions`

## Configuration

Avant de pouvoir exécuter l'application, vous devez configurer l'accès à la base de données. Modifiez le fichier `src/main/resources/application.properties` pour définir les paramètres de votre base de données MySQL :

```ini
spring.datasource.url=jdbc:mysql://localhost:3306/poseidon?serverTimezone=UTC
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```

Vous devrez également créer une base de données vierge dans MySQL nommée `poseidon` :

```sql
CREATE DATABASE poseidon;
```

Importez ensuite le schéma et les données de tests dans la base de données en exécutant le script SQL : `src/main/resources/script.sql`.
```shell
mysql -u <your-username> -p poseidon < src/main/resources/script.sql
```

## Exécution

Pour exécuter l'application, utilisez la commande Maven suivante :

```shell
mvn spring-boot:run
```

- **L'application est accessible par défaut à l'adresse suivante : [http://localhost:8080](http://localhost:8080)**.
- Le port 8080 par défaut peut être changé, au besoin, dans le fichier `src/main/resources/application.properties`


## Utilisation

### Connexion

Pour vous connecter à l'application, des utilisateurs fictifs sont présents avec les identifiants suivants :

- **Utilisateur 1 :** `user`
- **Mot de passe :** `Password1#`
####
- **Admin :** `admin`
- **Mot de passe :** `Password1#`


## Fonctionnalités

### Gestion des utilisateurs

- Les utilisateurs peuvent se connecter via le formulaire de connexion.
- Des rôles sont définis pour les utilisateurs : **ADMIN** ou **USER** afin de sécuriser les différents accès.
- Les **ADMIN** ont accès à la fonctionnalité de gestion des utilisateurs **USER MANAGEMENT**.

### CRUD

- Les utilisateurs peuvent `voir` / `ajouter` / `éditer` / `supprimer` des enregistrements sur n'importe quelle entité `BidList` / `CurvePoints` / `Ratings` / `Trade` / `Rule` / `User management (admin)`.
- Des contrôles d'entrées sont présents sur chaque formulaire afin de définir les données attendues.

### Déconnexion

- Les utilisateurs peuvent se déconnecter de l'application via le bouton `Logout` dans la barre de navigation.

### Gestion des Erreurs

- Les erreurs sont gérées via des pages d'erreur personnalisées.
- Les erreurs de validation des formulaires sont affichées dans les vues HTML.


## Tests
Le projet PoseidonCapitalSolutions inclut des tests pour assurer la qualité et la fiabilité du code.

### Exécution des Tests
Pour exécuter les tests, utilisez la commande suivante :
`mvn clean test`

### Rapports de Tests
#### Rapport de couverture de code par les tests
À la suite de la précédente commande, vous pouvez consulter le rapport de couverture JaCoCo dans : `target/site/jacoco/index.html`

#### Rapport de résultat d'exécution des tests
- À la suite de la précédente commande, vous pouvez aussi lancer la commande `mvn site`.
- Cela vous donnera accès au rapport Maven Surefire dans `target/site/index.html`
- Une JAVADOC est aussi générée dans `target/site/apidocs/index.html` et visible dans le rapport Maven Site.