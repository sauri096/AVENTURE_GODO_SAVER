package jeu.model;

import java.util.Timer;
import java.util.TimerTask;

public class ScenarioSauvetage extends Scenario {
    private boolean absorptionEffectuee = false;
    private boolean attaqueChronoEnCours = false;
    private int compteAttaques = 0;
    private long startTime;

    private Zone pointDepart, labo, prison, prisonVictoire, liberation, godo, fozia, izou;

    public ScenarioSauvetage() {
        super("sauvetage", "Scénario de sauvetage des otages");
        initialiserZones();
        setZoneDepart(pointDepart);
    }

    private void initialiserZones() {
        pointDepart = new Zone("Point de départ", "jf0.jpeg");
        labo = new Zone("Laboratoire", "jfl.jpeg");
        prison = new Zone("Prison", "jm1.jpeg");
        prisonVictoire = new Zone("Prison libérée", "jm2.jpeg");
        liberation = new Zone("Libération finale", "jf01.jpeg");
        godo = new Zone("Sortie", "victoire.jpeg");
        fozia = new Zone("Fozia", "fozia.jpeg");
        izou = new Zone("Izou", "izou.jpeg");

        // Mise à jour des descriptions pour plus de clarté
        pointDepart.setDescription("l'entrée du complexe mystérieux");
        labo.setDescription("un laboratoire rempli d'équipement étrange");
        prison.setDescription("une prison où des otages sont retenus");
        prisonVictoire.setDescription("la prison après votre victoire");
        liberation.setDescription("la zone de libération des otages");
        godo.setDescription("la sortie du complexe");
        fozia.setDescription("la zone Fozia");
        izou.setDescription("la zone Izou");

        // Configuration des sorties du point de départ
        pointDepart.ajouteSortie("LABO", labo);
        pointDepart.ajouteSortie("PRISON", prison);
        pointDepart.ajouteSortie("GODO", godo);
        pointDepart.ajouteSortie("FOZIA", fozia);
        pointDepart.ajouteSortie("IZOU", izou);

        // Verrouiller toutes les sorties sauf LABO au départ
        pointDepart.verrouillerSortie("PRISON", true);
        pointDepart.verrouillerSortie("GODO", true);
        pointDepart.verrouillerSortie("FOZIA", true);
        pointDepart.verrouillerSortie("IZOU", true);

        // Ajouter des sorties aux autres zones
        labo.ajouteSortie("DEPART", pointDepart);
        prison.ajouteSortie("DEPART", pointDepart);
        prison.ajouteSortie("LIBERATION", liberation);
        prison.verrouillerSortie("LIBERATION", true);

        prisonVictoire.ajouteSortie("LIBERATION", liberation);
        prisonVictoire.ajouteSortie("DEPART", pointDepart);

        liberation.ajouteSortie("PRISON", prison);
        liberation.ajouteSortie("GODO", godo);
        liberation.verrouillerSortie("GODO", true);

        fozia.ajouteSortie("DEPART", pointDepart);
        izou.ajouteSortie("DEPART", pointDepart);
        godo.ajouteSortie("DEPART", pointDepart);

        // Ajouter toutes les zones au scénario
        ajouterZone("depart", pointDepart);
        ajouterZone("labo", labo);
        ajouterZone("prison", prison);
        ajouterZone("prisonVictoire", prisonVictoire);
        ajouterZone("liberation", liberation);
        ajouterZone("godo", godo);
        ajouterZone("fozia", fozia);
        ajouterZone("izou", izou);
    }

    /**
     * Exécute l'absorption d'énergie dans le laboratoire
     * et déverrouille les zones accessibles.
     */
    public void executerAbsorption() {
        if (!absorptionEffectuee) {
            absorptionEffectuee = true;
            pointDepart.verrouillerSortie("PRISON", false);
            pointDepart.verrouillerSortie("FOZIA", false);
            pointDepart.verrouillerSortie("IZOU", false);
            // On garde GODO verrouillé jusqu'à la fin
        }
    }

    /**
     * Démarre un combat chronométré.
     *
     * @param onVictoire Action à exécuter en cas de victoire
     * @param onDefaite Action à exécuter en cas de défaite
     */
    public void demarrerCombatChrono(Runnable onVictoire, Runnable onDefaite) {
        if (attaqueChronoEnCours) return;

        attaqueChronoEnCours = true;
        compteAttaques = 0;
        startTime = System.currentTimeMillis();

        Timer chrono = new Timer();
        chrono.schedule(new TimerTask() {
            @Override
            public void run() {
                attaqueChronoEnCours = false;
                if (compteAttaques >= 3) {
                    onVictoire.run();
                } else {
                    onDefaite.run();
                }
            }
        }, 3000); // On donne 3 secondes au lieu de 2 pour être plus généreux
    }

    /**
     * Enregistre une attaque pendant le combat chronométré.
     *
     * @return true si l'attaque a été enregistrée, false sinon
     */
    public boolean enregistrerAttaque() {
        if (!attaqueChronoEnCours) return false;

        if (System.currentTimeMillis() - startTime <= 3000) {
            compteAttaques++;
            return true;
        }
        return false;
    }

    /**
     * Vérifie si l'absorption d'énergie a été effectuée.
     *
     * @return true si l'absorption a été effectuée, false sinon
     */
    public boolean estAbsorptionEffectuee() {
        return absorptionEffectuee;
    }

    /**
     * Libère les otages et déverrouille l'accès à la sortie.
     */
    public void libererOtages() {
        liberation.verrouillerSortie("GODO", false);
        pointDepart.verrouillerSortie("GODO", false);
    }

    // Getters pour toutes les zones
    public Zone getZoneDepart() { return pointDepart; }
    public Zone getZoneLabo() { return labo; }
    public Zone getZonePrison() { return prison; }
    public Zone getZonePrisonVictoire() { return prisonVictoire; }
    public Zone getZoneLiberation() { return liberation; }
    public Zone getZoneGodo() { return godo; }
    public Zone getZoneFozia() { return fozia; }
    public Zone getZoneIzou() { return izou; }
}