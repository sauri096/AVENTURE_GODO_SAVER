package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * État d'introduction au mode défis.
 * Présente l'histoire et le contexte du jeu au joueur.
 */
public class EtatDefisIntroduction implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {
        // Afficher l'image d'introduction
        jeu.afficherImage("j1.jpeg");

        // Présenter l'histoire et les objectifs
        jeu.afficherTexteProgressif(
                "Bienvenue, " + jeu.getJoueur().getPseudo() + ", dans le mode DÉFIS !\n\n" +
                        "Vous vous trouvez dans un mystérieux salon. Votre mission est de résoudre une série d'énigmes\n" +
                        "et de défis pour progresser dans cette aventure.\n\n" +
                        "Vous devrez faire preuve d'intelligence, de courage et de détermination pour réussir.\n\n" +
                        "Êtes-vous prêt à commencer cette aventure ?\n\n" +
                        "Tapez 'COMMENCER' pour débuter les défis ou 'AIDE' pour voir les commandes disponibles.",
                200
        );
    }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        String cmd = commande.trim().toLowerCase();

        if (cmd.equals("commencer") || cmd.equals("oui") ) {
            // Transition vers l'état des énigmes
            jeu.changerEtat(EtatJeu.DEFIS_ENIGMES);
            return true;
        } else if (cmd.equals("aide") || cmd.equals("?") ) {
            // Afficher l'aide
            jeu.getGui().afficher(
                    "Commandes disponibles :\n" +
                            "- COMMENCER : Débuter les défis\n" +
                            "- AIDE : Afficher cette aide\n" +
                            "- QUITTER : Quitter le jeu\n"
            );
            return true;
        } else if (cmd.equals("quitter") || cmd.equals("q")) {
            // Confirmation pour quitter
            jeu.changerEtat(EtatJeu.CONFIRMATION_QUITTER);
            return true;
        } else {
            jeu.getGui().afficher("Commande non reconnue. Tapez 'COMMENCER' pour débuter ou 'AIDE' pour l'aide.\n");
            return true;
        }
    }

    @Override
    public void sortir(Jeu jeu) {
        // Message de transition
        jeu.getGui().afficher("Que l'aventure commence...\n");
    }

    @Override
    public void executer(GUI gui) {
        // Non utilisé
    }
}