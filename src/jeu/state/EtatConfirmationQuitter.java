package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * Gère l'état de confirmation pour quitter le jeu.
 */
public class EtatConfirmationQuitter implements EtatHandler {
    
    private EtatJeu etatPrecedent;
    
    @Override
    public void entrer(Jeu jeu) {
        // Sauvegarder l'état précédent
        etatPrecedent = jeu.getEtatManager().getEtatActuel();
        
        // Afficher le message de confirmation
        jeu.afficherTexteProgressif(
            "Êtes-vous sûr de vouloir quitter le jeu? Tout progrès non sauvegardé sera perdu.\n" +
            "O (Oui) - Quitter le jeu\n" +
            "N (Non) - Continuer la partie",
            300
        );
    }
    
    @Override
    public void sortir(Jeu jeu) {}
    
    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        if (commande.equalsIgnoreCase("O") || commande.equalsIgnoreCase("OUI")) {
            jeu.afficherTexteProgressif("Au revoir " + jeu.getJoueur().getPseudo() + "...", 300);
            jeu.getGui().enable(false);
            
            // Programmation du système pour quitter après un délai
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
        } else {
            jeu.afficherTexteProgressif("Reprise de la partie.", 300);
            
            // Revenir à l'état précédent
            if (etatPrecedent != EtatJeu.CONFIRMATION_RECOMMENCER && 
                etatPrecedent != EtatJeu.CONFIRMATION_QUITTER) {
                jeu.changerEtat(etatPrecedent);
            } else {
                // Si l'état précédent était aussi une confirmation, revenir à l'accueil par défaut
                jeu.changerEtat(EtatJeu.CHOIX_MODE);
            }
            return true;
        }
    }

    @Override
    public void executer(GUI gui) {}
}