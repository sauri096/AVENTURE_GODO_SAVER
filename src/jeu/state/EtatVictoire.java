package jeu.state;

import jeu.controller.Jeu;
import jeu.model.ScenarioSauvetage;
import jeu.view.GUI;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Gère l'état de victoire du jeu.
 */
public class EtatVictoire implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {
        jeu.afficherTexteProgressif(
                "FÉLICITATIONS, " + jeu.getJoueur().getPseudo().toUpperCase() + " !\n\n" +
                        "Vous avez terminé cette partie avec succès !\n\n" +
                        "Appuyez sur n'importe quel character pour continuer ou tapez 'Q' pour quitter.",
                300
        );
    }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        if (commande.equalsIgnoreCase("Q") || commande.equalsIgnoreCase("QUITTER")) {
            jeu.afficherTexteProgressif("Merci d'avoir joué, " + jeu.getJoueur().getPseudo() + " !", 300);
            jeu.getGui().enable(false);
            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    },
                    2000
            );
            return true;
        } else {
            if (!jeu.getJoueur().isPartie1Gagnee()) {
                jeu.getJoueur().setPartie1Gagnee(true);
                jeu.afficherTexteProgressif("Vous avez réussi les défis ! Vous débloquez maintenant le mode sauvetage...", 300);

                // Charger le scénario de sauvetage
                ScenarioSauvetage scenario = new ScenarioSauvetage();
                jeu.getScenarioManager().setScenarioActuel(scenario);
                jeu.setZoneCourante(scenario.getZoneDepart());

                jeu.changerEtat(EtatJeu.DEUXIEME_ETAPE);
            } else {
                jeu.afficherTexteProgressif("Vous avez terminé le jeu en entier ! Retour au menu principal...", 300);
                jeu.getScenarioManager().chargerScenario("defaut");
                jeu.setZoneCourante(jeu.getScenarioManager().getScenarioActuel().getZoneDepart());
                jeu.changerEtat(EtatJeu.CHOIX_MODE);
            }
            return true;
        }
    }

    @Override
    public void executer(GUI gui) {
    }

    @Override
    public void sortir(Jeu jeu) {
    }

}