package jeu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jeu.state.Sortie;

/**
 * Classe représentant un scénario de jeu complet.
 * Un scénario contient des zones, des énigmes et la logique de progression.
 */
public class Scenario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nom;
    private String description;
    private Zone zoneDepart;
    private Map<String, Zone> zones;
    private List<Enigme> enigmes;
    public int enigmeCourante;
    private boolean enigmesResolues;
    private boolean forceTrouvee;
    private boolean tentativeAttaqueSansForceFaite;
    private boolean monstreTue;

    /**
     * Constructeur de la classe Scenario.
     *
     * @param nom Le nom du scénario
     * @param description La description du scénario
     */
    public Scenario(String nom, String description) {
        this.nom = nom;
        this.description = description;
        this.zones = new HashMap<>();
        this.enigmes = new ArrayList<>();
        this.enigmeCourante = 0;
        this.enigmesResolues = false;
        this.forceTrouvee = false;
        this.tentativeAttaqueSansForceFaite = false;
        this.monstreTue = false;
    }

    /**
     * Ajoute une zone au scénario.
     *
     * @param id L'identifiant de la zone
     * @param zone La zone à ajouter
     */
    public void ajouterZone(String id, Zone zone) {
        zones.put(id.toLowerCase(), zone);
    }

    /**
     * Obtient une zone par son identifiant.
     *
     * @param id L'identifiant de la zone
     * @return La zone correspondante ou null si non trouvée
     */
    public Zone getZone(String id) {
        return zones.get(id.toLowerCase());
    }

    /**
     * Obtient la liste des identifiants de toutes les zones.
     *
     * @return Un Set contenant les identifiants des zones
     */
    public Set<String> getZonesIds() {
        return zones.keySet();
    }

    /**
     * Définit la zone de départ du scénario.
     *
     * @param zoneDepart La zone de départ
     */
    public void setZoneDepart(Zone zoneDepart) {
        this.zoneDepart = zoneDepart;
    }

    /**
     * Obtient la zone de départ du scénario.
     *
     * @return La zone de départ
     */
    public Zone getZoneDepart() {
        return zoneDepart;
    }

    /**
     * Ajoute une énigme au scénario.
     *
     * @param enigme L'énigme à ajouter
     */
    public void ajouterEnigme(Enigme enigme) {
        enigmes.add(enigme);
    }

    /**
     * Obtient l'énigme courante.
     *
     * @return L'énigme courante ou null si toutes les énigmes sont résolues
     */
    public Enigme getEnigmeCourante() {
        if (enigmeCourante < enigmes.size()) {
            return enigmes.get(enigmeCourante);
        }
        return null;
    }

    /**
     * Obtient le nombre total d'énigmes.
     *
     * @return Le nombre d'énigmes
     */
    public int getNombreEnigmes() {
        return enigmes.size();
    }

    /**
     * Vérifie la réponse à l'énigme courante.
     *
     * @param reponse La réponse à vérifier
     * @return true si la réponse est correcte, false sinon
     */
    public boolean verifierReponse(String reponse) {
        Enigme enigme = getEnigmeCourante();
        if (enigme == null) {
            return false;
        }

        boolean reponseCorrecte = enigme.verifierReponse(reponse);

        if (reponseCorrecte) {
            enigmeCourante++;
            if (enigmeCourante >= enigmes.size()) {
                enigmesResolues = true;
            }
            return true;
        } else {
            enigme.diminuerEssai();
            return false;
        }
    }

    /**
     * Vérifie si toutes les énigmes ont été résolues.
     *
     * @return true si toutes les énigmes sont résolues, false sinon
     */
    public boolean sontEnigmesResolues() {
        return enigmesResolues;
    }

    /**
     * Force l'état des énigmes résolues.
     * Utile pour le chargement d'une sauvegarde.
     *
     * @param etat L'état à définir
     */
    public void setEnigmesResolues(boolean etat) {
        this.enigmesResolues = etat;
    }

    /**
     * Vérifie s'il reste des essais pour l'énigme courante.
     *
     * @return true s'il reste des essais, false sinon
     */
    public boolean resteEssais() {
        Enigme enigme = getEnigmeCourante();
        return enigme != null && enigme.getEssaisRestants() > 0;
    }

    /**
     * Réinitialise le scénario pour une nouvelle partie.
     */
    public void reinitialiser() {
        enigmeCourante = 0;
        enigmesResolues = false;
        forceTrouvee = false;
        tentativeAttaqueSansForceFaite = false;
        monstreTue = false;

        for (Enigme enigme : enigmes) {
            enigme.reinitialiserEssais();
        }
    }

    /**
     * Marque que la force a été trouvée.
     */
    public void trouverForce() {
        forceTrouvee = true;
    }

    /**
     * Vérifie si la force a été trouvée.
     *
     * @return true si la force a été trouvée, false sinon
     */
    public boolean estForceTrouvee() {
        return forceTrouvee;
    }

    /**
     * Marque qu'une tentative d'attaque sans force a été faite.
     */
    public void marquerTentativeAttaqueSansForce() {
        tentativeAttaqueSansForceFaite = true;
    }

    /**
     * Vérifie si une tentative d'attaque sans force a été faite.
     *
     * @return true si une tentative a été faite, false sinon
     */
    public boolean aTenteAttaqueSansForce() {
        return tentativeAttaqueSansForceFaite;
    }

    /**
     * Marque que le monstre a été tué.
     */
    public void tuerMonstre() {
        monstreTue = true;
    }



    /**
     * Vérifie si le monstre a été tué.
     *
     * @return true si le monstre a été tué, false sinon
     */
    public boolean estMonstreTue() {
        return monstreTue;
    }

    /**
     * Obtient le nom du scénario.
     *
     * @return Le nom du scénario
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obtient la description du scénario.
     *
     * @return La description du scénario
     */
    public String getDescription() {
        return description;
    }

}