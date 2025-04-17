package jeu.model;



/**
 * Classe utilitaire pour créer facilement des scénarios complets.
 * Implémente le pattern Factory pour la création de scénarios.
 */
public class ScenarioFactory {

    /**
     * Crée le scénario par défaut (mode défis).
     *
     * @return Le scénario créé
     */
    public static Scenario creerScenarioDefis() {
        Scenario scenario = new Scenario("defis", "Scénario principal du mode Défis");

        // Créer les zones
        Zone accueil = new Zone("l'accueil", "Accueil.png");
        Zone salon = new Zone("le debut", "j11.jpeg");
        Zone zoneF1 = new Zone("le piège mortel", "f1.jpeg");
        Zone zoneF2 = new Zone("la pièce du monstre", "f2.jpeg");
        Zone zoneJ3 = new Zone("la salle mystérieuse", "J3.jpeg");
        Zone zoneJ4 = new Zone("la source d'énergie", "j4.jpeg");
        Zone zoneJ5 = new Zone("la sortie", "j5.jpeg");
        Zone zoneFinale = new Zone("la liberté", "win1.jpeg");

        // Ajouter les zones au scénario
        scenario.ajouterZone("accueil", accueil);
        scenario.ajouterZone("debut", salon);
        scenario.ajouterZone("f1", zoneF1);
        scenario.ajouterZone("f2", zoneF2);
        scenario.ajouterZone("j3", zoneJ3);
        scenario.ajouterZone("j4", zoneJ4);
        scenario.ajouterZone("j5", zoneJ5);
        scenario.ajouterZone("finale", zoneFinale);

        // Configurer les connexions entre zones
        salon.ajouteSortie("GODO", zoneFinale);
        salon.ajouteSortie("F1", zoneF1);
        salon.ajouteSortie("F2", zoneF2);
        salon.ajouteSortie("J3", zoneJ3);


        zoneFinale.setVerrouillee(true);

        // Configurer la zone de départ
        scenario.setZoneDepart(accueil);

        // Créer les énigmes
        Enigme enigme1 = new Enigme("Combien font 2 × 5 − 7 ?", "3", "-4", "3", "0");
        Enigme enigme2 = new Enigme("Quelle est la capitale du Tchad ?", "N'Djamena", "Moundou", "Sarh", "N'Djamena");
        Enigme enigme3 = new Enigme("Quelle est la population du Tchad en millions ?", "19", "12", "19", "21");

        // Ajouter les énigmes au scénario
        scenario.ajouterEnigme(enigme1);
        scenario.ajouterEnigme(enigme2);
        scenario.ajouterEnigme(enigme3);

        return scenario;
    }


}
