package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * Gère l'état de pause du jeu.
 */
public class EtatPause implements EtatHandler {
    
    private EtatJeu etatPrecedent;
    
    @Override
    public void entrer(Jeu jeu) {
        // Sauvegarder l'état précédent
        etatPrecedent = jeu.getEtatManager().getEtatActuel();
        jeu.afficherImage("pause.png");
        
        // Afficher le menu de pause
        jeu.afficherTexteProgressif(
            "Jeu en pause. À bientôt, " + jeu.getJoueur().getPseudo() + "!\n\n" +
            "Choisissez une option :\n" +
            "C (Continuer) - Reprendre au même endroit\n" +
            "S (Sauvegarder) - Sauvegarder votre progression\n" +
            "Q (Quitter) - Quitter le jeu",
            300
        );
    }
    
    @Override
    public void sortir(Jeu jeu) {
        // Rien de spécial à faire en sortant de cet état
    }
    
    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        switch (commande.toUpperCase()) {
            case "C": case "CONTINUER":
                // Revenir à l'état précédent
                jeu.afficherTexteProgressif("Reprise du jeu...", 300);
                
                // Éviter de revenir à un état de confirmation
                if (etatPrecedent != EtatJeu.CONFIRMATION_RECOMMENCER && 
                    etatPrecedent != EtatJeu.CONFIRMATION_QUITTER &&
                    etatPrecedent != EtatJeu.PAUSE) {
                    jeu.changerEtat(etatPrecedent);
                } else {
                    // Si l'état précédent était problématique, revenir au choix de mode
                    jeu.changerEtat(EtatJeu.CHOIX_MODE);
                }
                return true;
                
            case "S": case "SAUVEGARDER":
                jeu.sauvegarder();
                jeu.afficherTexteProgressif(
                    "Partie sauvegardée avec succès. Pour reprendre, redémarrez l'application et sélectionnez 'Continuer'.",
                    300
                );
                return true;
                
            case "Q": case "QUITTER":
                // Quitter le jeu
                jeu.afficherTexteProgressif("Au revoir, " + jeu.getJoueur().getPseudo() + "!", 300);
                jeu.getGui().enable(false);
                
                new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    },
                    2000
                );
                return true;
                
            default:
                // Si la commande n'est pas reconnue, afficher un message d'aide
                if (!commande.isEmpty()) {
                    jeu.afficherTexteProgressif(
                        "Commande non reconnue. Tapez 'C' pour commencer, 'S' pour sauvegarder ou 'Q' pour quitter.",
                        300
                    );
                    return true;
                }
                return false;
        }
    }

    @Override
    public void executer(GUI gui) {

    }
}