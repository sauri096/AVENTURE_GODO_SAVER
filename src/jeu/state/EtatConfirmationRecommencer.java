package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * Gère l'état de confirmation pour recommencer le jeu.
 */
public class EtatConfirmationRecommencer implements EtatHandler {
    
    private EtatJeu etatPrecedent;
    
    @Override
    public void entrer(Jeu jeu) {
        // Sauvegarder l'état précédent
        etatPrecedent = jeu.getEtatManager().getEtatActuel();
        
        // Afficher le message de confirmation
        jeu.afficherTexteProgressif(
            "Êtes-vous sûr de vouloir recommencer le jeu? Tout progrès non sauvegardé sera perdu.\n" +
            "O (Oui) - Recommencer le jeu\n" +
            "N (Non) - Continuer la partie actuelle",
            300
        );
    }
    
    @Override
    public void sortir(Jeu jeu) {
        // Rien de particulier à faire ici
    }
    
    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        if (commande.equalsIgnoreCase("O") || commande.equalsIgnoreCase("OUI")) {
            jeu.afficherTexteProgressif("Redémarrage du jeu...", 300);
            jeu.reinitialiser();
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
    public void executer(GUI gui) {

    }
}