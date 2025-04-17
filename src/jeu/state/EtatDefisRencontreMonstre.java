package jeu.state;

import jeu.controller.Jeu;
import jeu.model.Scenario;
import jeu.view.GUI;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Gère l'état du jeu lors de la rencontre avec le monstre.
 */
public class EtatDefisRencontreMonstre implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {
    }

    @Override
    public void sortir(Jeu jeu) {
    }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        Scenario scenario = jeu.getScenarioManager().getScenarioActuel();
        String cmd = commande.trim().toUpperCase();

        switch (cmd) {
            case "A": case "ATTAQUER":
                if (!scenario.estForceTrouvee()) {
                    // Premier essai sans force
                    if (scenario.aTenteAttaqueSansForce()) {
                        // Deuxième tentative sans force - mort
                        jeu.afficherImage("f11.jpeg");
                        jeu.afficherTexteProgressif(
                                "Vous persistez à attaquer sans force... Le monstre vous terrasse!\n\n" +
                                        "Vous avez perdu.",
                                300
                        );
                        jeu.getGui().enable(false);

                        // Programmer le retour à l'accueil après 3 secondes
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                jeu.changerEtat(EtatJeu.CHOIX_MODE);
                                jeu.getGui().enable(true);
                            }
                        }, 3000);
                    } else {
                        // Premier essai - avertissement
                        jeu.afficherTexteProgressif(
                                "Vous n'avez pas la force de battre le monstre. Fouillez la pièce pour trouver une source de pouvoir.",
                                300
                        );
                        scenario.marquerTentativeAttaqueSansForce();
                    }
                } else {
                    // Si on a la force, on tue le monstre
                    jeu.afficherImage("j5.jpeg");
                    jeu.afficherTexteProgressif(
                            "Avec votre nouvelle force, vous attaquez le monstre et le vaincrez!\n" +
                                    "Le passage est maintenant libre!\n\n" +
                                    "Vous pouvez maintenant choisir une sortie parmi: GODO, F1, F3, J3, ABECHE",
                            300
                    );
                    scenario.tuerMonstre();
                    jeu.changerEtat(EtatJeu.DEFIS_APRES_FORCE);
                }
                return true;

            case "F": case "FOUILLER":
                if (scenario.estForceTrouvee()) {
                    jeu.afficherTexteProgressif(
                            "Vous avez déjà trouvé et absorbé l'énergie. Utilisez A ou ATTAQUER pour affronter le monstre.",
                            300
                    );
                } else {
                    jeu.afficherImage("j4.jpeg");
                    jeu.afficherTexteProgressif(
                            "Vous fouillez soigneusement la pièce et découvrez une source d'énergie mystérieuse!\n\n" +
                                    "Vous pouvez maintenant: ABSORBER (pour absorber cette énergie)",
                            300
                    );
                    jeu.changerEtat(EtatJeu.DEFIS_CHERCHER_FORCE);
                }
                return true;

            case "ABSORBER":
                if (scenario.estForceTrouvee()) {
                    jeu.afficherTexteProgressif(
                            "Vous avez déjà absorbé l'énergie. Utilisez A ou ATTAQUER pour affronter le monstre.",
                            300
                    );
                } else {
                    jeu.afficherTexteProgressif(
                            "Vous devez d'abord fouiller la pièce pour trouver l'énergie. Tapez F ou FOUILLER.",
                            300
                    );
                }
                return true;

            default:
                if (!cmd.isEmpty()) {
                    if (scenario.estForceTrouvee()) {
                        jeu.afficherTexteProgressif(
                                "Action non reconnue.",
                                300
                        );
                    } else {
                        jeu.afficherTexteProgressif(
                                "Action non reconnue. Options: A (Attaquer), F (Fouiller la pièce)",
                                300
                        );
                    }
                    return true;
                }
                return false;
        }
    }

    @Override
    public void executer(GUI gui) {
    }
}