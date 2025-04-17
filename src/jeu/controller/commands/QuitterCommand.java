package jeu.controller.commands;

import jeu.controller.Jeu;
import jeu.state.EtatJeu;

/**
 * Commande pour quitter le jeu.
 */
public class QuitterCommand implements Command {
    
    @Override
    public boolean executer(String[] args, Jeu jeu) {
        // Demander confirmation avant de quitter
        jeu.changerEtat(EtatJeu.CONFIRMATION_QUITTER);
        return true;
    }
    
    @Override
    public String getDescription() {
        return "Quitter le jeu";
    }
    
    @Override
    public String getCategorie() {
        return "Quitter";
    }
}