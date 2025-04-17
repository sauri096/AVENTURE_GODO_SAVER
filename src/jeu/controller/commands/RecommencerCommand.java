package jeu.controller.commands;

import jeu.controller.Jeu;
import jeu.state.EtatJeu;

/**
 * Commande pour recommencer le jeu.
 */
public class RecommencerCommand implements Command {
    
    @Override
    public boolean executer(String[] args, Jeu jeu) {
        jeu.changerEtat(EtatJeu.CONFIRMATION_RECOMMENCER);
        return true;
    }
    
    @Override
    public String getDescription() {
        return "Recommencer le jeu depuis le début";
    }
    
    @Override
    public String getCategorie() {
        return "Système";
    }
}