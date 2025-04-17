package jeu.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant une zone dans le jeu.
 * Une zone est un lieu que le joueur peut visiter.
 */
public class Zone implements Serializable {
    private static final long serialVersionUID = 1L;

    private String description;
    private String nomImage;
    private Map<String, Zone> sorties;
    private boolean verrouillee;

    /**
     * Constructeur de la classe Zone.
     *
     * @param description La description de la zone
     * @param nomImage Le nom du fichier image associé à la zone
     */
    public Zone(String description, String nomImage) {
        this.description = description;
        this.nomImage = nomImage;
        this.sorties = new HashMap<>();
        this.verrouillee = false;
    }
    /**
     * Retourne le nom du fichier image associé à la zone.
     *
     * @return Le nom du fichier image
     */
    public String nomImage() {
        return nomImage;
    }

    /**
     * Définit si la zone est verrouillée ou non.
     *
     * @param verrouillee true si la zone est verrouillée, false sinon
     */
    public void setVerrouillee(boolean verrouillee) {
        this.verrouillee = verrouillee;
    }

    /**
     * Vérifie si la zone est verrouillée.
     *
     * @return true si la zone est verrouillée, false sinon
     */
    public boolean estVerrouillee() {
        return verrouillee;
    }

    /**
     * Change le nom de l'image associée à la zone.
     *
     * @param nomImage Le nouveau nom de l'image
     */
    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    /**
     * Obtient la description de la zone.
     *
     * @return La description de la zone
     */
    public String getDescription() {
        return description;
    }







    public void ajouteSortie(String direction, Zone zoneVoisine) {
        sorties.put(direction.toUpperCase(), zoneVoisine);
    }

    /**
     * Retourne la zone accessible dans la direction spécifiée.
     *
     * @param direction La direction à suivre
     * @return La zone correspondante ou null si aucune sortie n'existe dans cette direction
     */
    public Zone obtientSortie(String direction) {
        if (direction != null) {
            return sorties.get(direction.toUpperCase());
        }
        return null;
    }






    /**
     * Verrouille ou déverrouille une sortie spécifique.
     *
     * @param direction La direction de la sortie
     * @param verrouillee true pour verrouiller, false pour déverrouiller
     * @return true si la sortie existe et a été modifiée, false sinon
     */
    public boolean verrouillerSortie(String direction, boolean verrouillee) {
        Zone zone = sorties.get(direction);
        if (zone != null) {
            zone.setVerrouillee(verrouillee);
            return true;
        }
        return false;
    }

    public void setDescription(String s) {
        System.out.println(s);
    }
}