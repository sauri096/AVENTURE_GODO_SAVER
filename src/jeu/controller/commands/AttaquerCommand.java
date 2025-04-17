package jeu.controller.commands;

import jeu.controller.Jeu;
import jeu.state.EtatJeu;
import jeu.model.Scenario;

/**
 * Commande pour attaquer un ennemi.
 */
public class AttaquerCommand implements Command {
    
    @Override
    public boolean executer(String[] args, Jeu jeu) {
        EtatJeu etat = jeu.getEtatManager().getEtatActuel();
        
        // Cette commande n'est valide que dans l'état de rencontre de monstre
        if (etat == EtatJeu.DEFIS_RENCONTRE_MONSTRE) {
            Scenario scenario = jeu.getScenarioManager().getScenarioActuel();
            
            if (!scenario.estForceTrouvee()) {
                if (scenario.aTenteAttaqueSansForce()) {
                    jeu.afficherImage("j5.jpeg");
                    jeu.afficherTexteProgressif(
                        "Vous persistez à attaquer sans force... Le monstre vous terrasse!\n\n" +
                        "Vous avez perdu !",
                        300
                    );
                    jeu.getGui().enable(false);
                    jeu.changerEtat(EtatJeu.DEFAITE);
                } else {
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
                    "Avec votre nouvelle force, vous attaquez le monstre et le vainquez!\n" +
                    "Le passage est maintenant libre!",
                    300
                );
                scenario.tuerMonstre();
                jeu.changerEtat(EtatJeu.DEFIS_APRES_FORCE);
                
                // Proposer les sorties disponibles après avoir vaincu le monstre
                jeu.afficherTexteProgressif(
                    "Vous pouvez maintenant choisir une sortie parmi: Godo, f1, f3, J3",
                    300
                );
            }
            return true;
        } else {
            jeu.afficherTexteProgressif(
                "Il n'y a rien à attaquer ici.",
                300
            );
            return true;
        }
    }
    
    @Override
    public String getDescription() {
        return "Attaquer un ennemi";
    }
    
    @Override
    public String getCategorie() {
        return "Combat";
    }
}