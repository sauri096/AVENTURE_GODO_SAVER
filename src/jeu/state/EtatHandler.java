package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * Interface pour les handlers d'état du jeu.
 * Chaque état du jeu doit implémenter cette interface.
 */
public interface EtatHandler {
    /**
     * Méthode appelée lors de l'entrée dans cet état.
     *
     * @param jeu L'instance du jeu
     */
    void entrer(Jeu jeu);
    
    /**
     * Méthode appelée lors de la sortie de cet état.
     *
     * @param jeu L'instance du jeu
     */
    void sortir(Jeu jeu);
    
    /**
     * Traite une commande spécifique à cet état.
     *
     * @param commande La commande à traiter
     * @param jeu L'instance du jeu
     * @return true si la commande a été traitée, false sinon
     */
    boolean traiterCommande(String commande, Jeu jeu);

    void executer(GUI gui);
}