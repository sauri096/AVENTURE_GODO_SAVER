package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * Handler pour l'état d'accueil du jeu.
 */
public class EtatAccueil implements EtatHandler {

    private boolean pseudoDemande = false;

    @Override
    public void entrer(Jeu jeu) {
        jeu.afficherImage("Accueil.png");
        jeu.afficherTexteProgressif(
                "Bienvenue dans notre jeu d'aventure !\n\n" +
                        "Veuillez entrer votre pseudo pour commencer :\n",
                300
        );
        pseudoDemande = true;
    }

    @Override
    public void sortir(Jeu jeu) {}

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        if (pseudoDemande) {
            if (!commande.trim().isEmpty() && commande.trim().matches("[a-zA-Z0-9]+")) {
                jeu.getJoueur().setPseudo(commande.trim());
                jeu.getJoueur().setPseudoSaisi(true);

                jeu.afficherTexteProgressif(
                        "Bienvenue, " + commande.trim() + " !\n\n" +
                                "Pour obtenir de l'aide à tout moment, tapez '?' ou 'aide'.\n",
                        300
                );

                jeu.changerEtat(EtatJeu.CHOIX_MODE);
                return true;
            } else {
                jeu.getGui().afficher("Veuillez entrer un pseudo valide (lettres et chiffres uniquement).\n");
                return true;
            }
        }
        return false;
    }

    @Override
    public void executer(GUI gui) {}
}
