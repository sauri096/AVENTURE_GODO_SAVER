package jeu.controller;

import java.io.Serializable;

/**
 * Classe représentant le joueur avec ses informations et sa progression.
 */
public class Joueur implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pseudo;
    private boolean pseudoSaisi;
    private boolean partie1Gagnee;

    /**
     * Constructeur par défaut.
     */
    public Joueur() {
        this.pseudo = "Joueur";
        this.pseudoSaisi = false;
        this.partie1Gagnee = false;
    }

    /**
     * Obtient le pseudo du joueur.
     *
     * @return Le pseudo du joueur
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Définit le pseudo du joueur.
     *
     * @param pseudo Le nouveau pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Vérifie si le pseudo a été saisi.
     *
     * @return true si le pseudo a été saisi, false sinon
     */
    public boolean isPseudoSaisi() {
        return pseudoSaisi;
    }

    /**
     * Définit si le pseudo a été saisi.
     *
     * @param pseudoSaisi L'état de saisie du pseudo
     */
    public void setPseudoSaisi(boolean pseudoSaisi) {
        this.pseudoSaisi = pseudoSaisi;
    }

    /**
     * Vérifie si la première partie (mode Défis) a été gagnée.
     *
     * @return true si la première partie a été gagnée, false sinon
     */
    public boolean isPartie1Gagnee() {
        return partie1Gagnee;
    }

    /**
     * Définit si la première partie a été gagnée.
     *
     * @param partie1Gagnee L'état de victoire de la première partie
     */
    public void setPartie1Gagnee(boolean partie1Gagnee) {
        this.partie1Gagnee = partie1Gagnee;
    }
}
