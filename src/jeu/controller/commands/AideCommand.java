package jeu.controller.commands;

import jeu.controller.Jeu;


/**
 * Commande pour afficher l'aide contextuelle du jeu.
 */
public class AideCommand implements Command {
    
    @Override
    public boolean executer(String[] args, Jeu jeu) {
        StringBuilder aideMessage = new StringBuilder("Besoin d'aide, " + jeu.getJoueur().getPseudo() + " ?\n\n");
        

        switch (jeu.getEtatManager().getEtatActuel()) {
            case CHOIX_MODE:
                aideMessage.append(
                    "Vous êtes dans le menu de sélection du mode de jeu.\n" +
                    "Tapez '1' ou 'Defis' pour le mode Défis.\n" +
                    "Tapez '2' ou 'Explorer' pour le mode Exploration non disponible pour le moment.\n\n"
                );
                break;
            case DEFIS_INTRODUCTION:
                aideMessage.append(
                    "Vous êtes dans l'introduction du mode Défis.\n"

                );
                break;
            case DEFIS_ENIGMES:
                aideMessage.append(
                    "Vous devez résoudre l'énigme en cours.\n" +
                    "Choisissez parmi les options proposées et saisissez votre réponse.\n\n"
                );
                break;
            case DEFIS_APRES_ENIGMES:
                aideMessage.append(
                    "Vous avez résolu toutes les énigmes et pouvez explorer les sorties.\n" +
                    "Les sorties disponibles sont: Godo, f1, f2, f3, J3\n\n"
                );
                break;
            case DEFIS_RENCONTRE_MONSTRE:
                if (jeu.getScenarioManager().getScenarioActuel().estForceTrouvee()) {
                    aideMessage.append(
                        "Vous êtes face à un monstre et avez la force nécessaire pour le vaincre!\n" +
                        "Options: B (Battre), A (Attaquer)\n\n"
                    );
                } else {
                    aideMessage.append(
                        "Vous êtes face à un monstre!\n" +
                        "Options: B (Battre), A (Attaquer), F (Fouiller la pièce)\n\n"
                    );
                }
                break;
            case DEFIS_CHERCHER_FORCE:
                aideMessage.append("Vous avez trouvé une source d'énergie mystérieuse.\n");
                break;
            case DEFIS_APRES_FORCE:
                aideMessage.append("Vous avez absorbé l'énergie et vaincu le monstre.\n" +
                                    "Les sorties disponibles sont: Godo, f1, f3, J3\n");
                break;
            case EXPLORATION_MODE:
                aideMessage.append(
                    "Vous êtes en mode exploration.\n" );
                break;
            default:
                aideMessage.append(
                    "Les commandes disponibles dépendent de votre situation actuelle dans le jeu.\n\n");
                break;
        }
        
        // Ajouter les commandes globales
        aideMessage.append(
            "Commandes globales:\n" +
            "? ou aide - Afficher l'aide\n" +
            "p ou pause - Mettre le jeu en pause\n" +
            "q ou quitter - Quitter le jeu\n" +
            "r ou recommencer - Recommencer le jeu\n"
        );
        
        jeu.afficherTexteProgressif(aideMessage.toString(), 300);
        return true;
    }
    
    @Override
    public String getDescription() {
        return "Affiche l'aide contextuelle";
    }
    
    @Override
    public String getCategorie() {
        return "Système";
    }
}