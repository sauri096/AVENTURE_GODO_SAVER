package jeu.controller.commands;

import jeu.controller.Jeu;
import jeu.state.EtatJeu;

/**
 * Commande pour mettre le jeu en pause et sauvegarder la partie.
 */
public class PauseCommand implements Command {
    
    @Override
    public boolean executer(String[] args, Jeu jeu) {
        jeu.sauvegarder();
        
        jeu.afficherTexteProgressif(
            "Jeu en pause. À bientôt, " + jeu.getJoueur().getPseudo() + "!\n\n" +
            "Pour reprendre, redémarrez l'application.",
            300
        );
        
        jeu.changerEtat(EtatJeu.PAUSE);
        return true;
    }
    
    @Override
    public String getDescription() {
        return "Mettre le jeu en pause et sauvegarder la partie";
    }
    
    @Override
    public String getCategorie() {
        return "Pause";
    }
}