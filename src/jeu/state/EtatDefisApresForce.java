package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Gère l'état du jeu après avoir vaincu le monstre.
 */
public class EtatDefisApresForce implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {}

    @Override
    public void sortir(Jeu jeu) {
    }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        String direction = commande.toUpperCase();

        switch (direction) {
            case "GODO":
                jeu.afficherImage("win1.jpeg");
                jeu.afficherTexteProgressif(
                        "Grâce à votre nouvelle force, vous parvenez à ouvrir cette sortie!\n\n" +
                                "Félicitations! Vous avez terminé le jeu avec succès!",
                        300
                );
                jeu.changerEtat(EtatJeu.VICTOIRE);
                return true;

            case "F1":
                jeu.afficherImage("f1.jpeg");
                jeu.afficherTexteProgressif(
                        "Vous avez choisi une sortie dangereuse!\n\n" +
                                "Vous êtes tombé dans un piège mortel. Vous avez perdu.",
                        300
                );
                jeu.getGui().enable(false);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        jeu.changerEtat(EtatJeu.CHOIX_MODE);
                        jeu.getGui().enable(true);
                    }
                }, 3000);
                return true;

            case "F2":
                jeu.afficherTexteProgressif(
                        "Vous avez déjà vaincu le monstre dans cette zone. Choisissez une autre direction.",
                        300
                );
                return true;

            case "F3": case "ABECHE": case "J3":
                jeu.afficherTexteProgressif(
                        "Cette sortie est bloquée pour le moment. Seule la sortie GODO peut vous mener à la victoire.",
                        300
                );
                return true;

            default:
                if (!direction.isEmpty()) {
                    jeu.afficherTexteProgressif(
                            "Sortie inconnue. Choisissez parmi les sorties disponibles: GODO, F1, F3, J3, ABECHE",
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