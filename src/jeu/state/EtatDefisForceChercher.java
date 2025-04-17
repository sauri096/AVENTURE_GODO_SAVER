package jeu.state;

import jeu.controller.Jeu;
import jeu.model.Scenario;
import jeu.view.GUI;

/**
 * Gère l'état du jeu lors de la recherche de la force.
 */
public class EtatDefisForceChercher implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {
    }

    @Override
    public void sortir(Jeu jeu) {
        }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        Scenario scenario = jeu.getScenarioManager().getScenarioActuel();
        String cmd = commande.trim().toLowerCase();

        if (cmd.equals("absorber") || cmd.equals("a")) {
            // Marquer que la force a été trouvée
            scenario.trouverForce();

            jeu.afficherTexteProgressif(
                    "Vous absorbez l'énergie mystérieuse! Une puissance nouvelle parcourt votre corps.\n" +
                            "Vous retournez face au monstre, prêt à l'affronter.",
                    300
            );

            jeu.afficherImage("f2.jpeg");

            jeu.afficherTexteProgressif(
                    "Le monstre est toujours là. Que faites-vous?\n" +
                            "Options: A (Attaquer), F (Fouiller la pièce)",
                    300
            );

            jeu.changerEtat(EtatJeu.DEFIS_RENCONTRE_MONSTRE);
            return true;
        } else {
            if (!cmd.isEmpty()) {
                jeu.afficherTexteProgressif(
                        "Action non reconnue.",
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