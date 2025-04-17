package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Gère l'état de défaite du jeu.
 */
public class EtatDefaite implements EtatHandler {
    
    @Override
    public void entrer(Jeu jeu) {
        // Désactiver le champ de saisie pour empêcher toute entrée pendant l'animation
        jeu.getGui().enable(false);
        
        // Afficher un message de défaite
        jeu.afficherTexteProgressif(
            "Vous avez perdu\n" +
            "Votre aventure s'arrête ici, " + jeu.getJoueur().getPseudo() + ".\n\n" +
            "Le jeu va redémarrer dans 3 secondes...",
            300
        );
        
        // Programmer le retour à l'accueil après un délai
        new Timer().schedule(
            new TimerTask() {
                @Override
                public void run() {
                    // Réinitialiser le jeu
                    jeu.reinitialiser();
                    
                    // Réactiver le champ de saisie
                    jeu.getGui().enable(true);
                }
            },
            5000
        );
    }
    
    @Override
    public void sortir(Jeu jeu) {}
    
    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {

        return true;
    }

    @Override
    public void executer(GUI gui) {

    }
}