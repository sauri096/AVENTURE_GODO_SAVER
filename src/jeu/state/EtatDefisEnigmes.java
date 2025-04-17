package jeu.state;

import jeu.controller.Jeu;
import jeu.model.Enigme;
import jeu.model.Scenario;
import jeu.view.GUI;

/**
 * Gère l'état du jeu lorsque le joueur doit résoudre des énigmes.
 */
public class EtatDefisEnigmes implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {
        Scenario scenario = jeu.getScenarioManager().getScenarioActuel();
        Enigme enigmeCourante = scenario.getEnigmeCourante();
        jeu.afficherImage("j22.jpeg");

        if (enigmeCourante != null) {
            jeu.afficherTexteProgressif(
                    "Énigme " + (scenario.enigmeCourante + 1) + "/" + scenario.getNombreEnigmes() + ":\n" +
                            enigmeCourante.afficherEnigme(),
                    300
            );
        } else {
            jeu.changerEtat(EtatJeu.DEFIS_APRES_ENIGMES);
        }
    }
    @Override
    public void sortir(Jeu jeu) {
    }
    
    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        Scenario scenario = jeu.getScenarioManager().getScenarioActuel();
        
        boolean correct = scenario.verifierReponse(commande);


        if (correct) {
            jeu.afficherTexteProgressif("Bonne réponse !", 300);
            if(scenario.verifierReponse(commande)) {
                jeu.changerEtat(EtatJeu.DEFIS_APRES_ENIGMES);
            }else{
                Enigme prochaineEnigme = scenario.getEnigmeCourante();
                if(prochaineEnigme != null) {
                    jeu.afficherTexteProgressif(
                            "Enigme" +(scenario.enigmeCourante +1)+"/"+scenario.getNombreEnigmes()+":\n"+prochaineEnigme.afficherEnigme(),300
                    );
                }else{
                    jeu.changerEtat(EtatJeu.DEFIS_APRES_ENIGMES);
                }
            }


        }

        else {
            if (scenario.resteEssais()) {
                jeu.afficherTexteProgressif(
                    "Réponse incorrecte. Il vous reste " + 
                    scenario.getEnigmeCourante().getEssaisRestants() + " essai(s).",
                    300
                );
            } else {
                jeu.afficherTexteProgressif(
                    "Réponse incorrecte. Vous avez épuisé vos essais.\n" +
                    "Vous devez recommencer le questionnaire depuis le début.",
                    300
                );
                scenario.reinitialiser();
                jeu.changerEtat(EtatJeu.DEFIS_INTRODUCTION);
            }
        }
        
        return true;
    }

    @Override
    public void executer(GUI gui) {

    }
}