package jeu.controller.commands;

import jeu.controller.Jeu;

/**
 * Interface pour les commandes du jeu.
 * Chaque commande doit implémenter cette interface.
 */
public interface Command {
    /**
     * Exécute la commande avec les arguments fournis.
     *
     * @param args Les arguments de la commande
     * @param jeu L'instance du jeu
     * @return true si la commande a été exécutée avec succès, false sinon
     */
    boolean executer(String[] args, Jeu jeu);
    
    /**
     * Retourne une description de la commande.
     *
     * @return La description de la commande
     */
    String getDescription();
    
    /**
     * Retourne la catégorie de la commande pour l'aide.
     *
     * @return La catégorie de la commande
     */
    String getCategorie();
}