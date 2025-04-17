package jeu.controller.commands;

import jeu.controller.Jeu;
import jeu.model.ScenarioSauvetage;
import jeu.model.Zone;

public class LibererCommand implements Command {

    @Override
    public boolean executer(String[] args, Jeu jeu) {
        if (!(jeu.getScenarioManager().getScenarioActuel() instanceof ScenarioSauvetage)) {
            jeu.getGui().afficher("Cette commande n'est pas disponible ici.\n");
            return true;
        }

        ScenarioSauvetage scenario = (ScenarioSauvetage) jeu.getScenarioManager().getScenarioActuel();
        Zone zoneActuelle = jeu.getZoneCourante();

        if (zoneActuelle.equals(scenario.getZonePrisonVictoire())) {
            jeu.getGui().afficher("Vous avez libéré les otages. Une nouvelle sortie s'est ouverte...\n");
            jeu.afficherImage("jf01.jpeg");


            jeu.setZoneCourante(scenario.getZoneLiberation());


            scenario.libererOtages();
        } else {
            jeu.getGui().afficher("Il n'y a rien à libérer ici.\n");
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Libérer les otages après avoir gagné le combat";
    }

    @Override
    public String getCategorie() {
        return "Action spéciale";
    }
}
