package jeu.controller.commands;

import jeu.controller.Jeu;
import jeu.model.ScenarioSauvetage;
import jeu.state.EtatJeu;

/**
 * Commande permettant au joueur d'absorber l'énergie.
 * Spécifique au scénario de sauvetage.
 */
public class AbsorberCommand implements Command {

    @Override
    public boolean executer(String[] args, Jeu jeu) {
        if (jeu.getEtatManager().getEtatActuel() != EtatJeu.EXPLORATION_MODE) {
            jeu.getGui().afficher("Vous ne pouvez pas utiliser cette commande maintenant.\n");
            return true;
        }

        if (!(jeu.getScenarioManager().getScenarioActuel() instanceof ScenarioSauvetage)) {
            jeu.getGui().afficher("Cette commande n'est pas disponible dans ce scénario.\n");
            return true;
        }

        ScenarioSauvetage scenario = (ScenarioSauvetage) jeu.getScenarioManager().getScenarioActuel();


        if (jeu.getZoneCourante() == scenario.getZoneLabo()) {
            if (!scenario.estAbsorptionEffectuee()) {
                jeu.getGui().afficherTexteProgressif(
                        "Vous vous concentrez et commencez à absorber l'énergie mystérieuse qui emplit le laboratoire...\n\n" +
                                "Une sensation de puissance vous envahit ! Vous sentez que vous pouvez maintenant accéder à de nouvelles zones !\n",
                        100
                );


                scenario.executerAbsorption();

                jeu.getGui().afficher("\n" + jeu.getZoneCourante().getDescription() + "\n");

                jeu.getGui().afficherTexteProgressif(
                        "Vous sentez que vous pouvez maintenant aller vers la PRISON pour sauver les otages.\n",
                        100
                );
            } else {
                jeu.getGui().afficher("Vous avez déjà absorbé toute l'énergie disponible ici.\n");
            }
        } else {
            jeu.getGui().afficher("Il n'y a rien à absorber ici. Essayez dans le laboratoire.\n");
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Absorber l'énergie mystérieuse (dans le laboratoire)";
    }

    @Override
    public String getCategorie() {
        return "Actions spéciales";
    }
}