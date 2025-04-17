
package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * Gère l'état de choix du mode de jeu.
 */
public class EtatChoixMode implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {
        StringBuilder message = new StringBuilder();
        message.append("Bonjour ").append(jeu.getJoueur().getPseudo()).append(" !\n\n");
        message.append("Choisissez votre mode de jeu :\n");
        message.append("1 ou Defis - Mode Défis\n");

        // Afficher le mode Sauvetage uniquement s'il est débloqué
        if (jeu.getJoueur().isPartie1Gagnee()) {
            message.append("2 ou Sauvetage - Mode Sauvetage\n");
        } else {
            message.append("2 ou Sauvetage \n");
        }

        message.append("\nCommandes disponibles :\n");
        message.append("? ou Aide - Afficher l'aide\n");
        message.append("R ou Recommencer - Recommencer le jeu\n");
        message.append("S ou Sauvegarder - Sauvegarder la partie\n");
        message.append("Q ou Quitter - Quitter le jeu\n");

        jeu.afficherTexteProgressif(message.toString(), 300);
    }

    @Override
    public void sortir(Jeu jeu) {
        // Rien de spécial à faire
    }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        String cmd = commande.trim().toLowerCase();

        switch (cmd) {
            case "1": case "defis":
                jeu.afficherImage("j1.jpeg");
                jeu.afficherTexteProgressif(
                        "Vous vous réveillez dans un salon abandonné. La porte est verrouillée.\n\n" +
                                "Une voix mystérieuse résonne : \"Si tu veux sortir, prouve que tu mérites ta liberté... en répondant à ces questions.\"\n\n" +
                                "Commandes disponibles : 'commencer' pour débuter ou '?' pour l'aide",
                        300
                );
                jeu.changerEtat(EtatJeu.DEFIS_INTRODUCTION);
                return true;

            case "2": case "sauvetage":
                if (jeu.getJoueur().isPartie1Gagnee()) {
                    // Charger le scénario de sauvetage
                    jeu.getScenarioManager().chargerScenarioSauvetage();
                    jeu.setZoneCourante(jeu.getScenarioManager().getScenarioActuel().getZoneDepart());
                    jeu.changerEtat(EtatJeu.DEUXIEME_ETAPE);
                } else {
                    jeu.afficherTexteProgressif(
                            "Le mode Sauvetage n'est pas encore disponible. Vous devez d'abord terminer le mode Défis.",
                            300
                    );
                }
                return true;

            case "?": case "aide":
                jeu.getGui().afficher(
                        "Commandes disponibles :\n" +
                                "- 1 ou Defis :\n" +
                                "- 2 ou Sauvetage :\n" +
                                "- ? ou Aide : Afficher cette aide\n" +
                                "- R ou Recommencer : Recommencer le jeu\n" +
                                "- S ou Sauvegarder : Sauvegarder la partie\n" +
                                "- Q ou Quitter : Quitter le jeu\n"
                );
                return true;

            case "r": case "recommencer":
                jeu.changerEtat(EtatJeu.CONFIRMATION_RECOMMENCER);
                return true;

            case "s": case "sauvegarder":
                jeu.sauvegarder();
                return true;

            case "q": case "quitter":
                jeu.changerEtat(EtatJeu.CONFIRMATION_QUITTER);
                return true;

            default:
                if (!cmd.isEmpty()) {
                    jeu.getGui().afficher("Commande non reconnue. Tapez '?' pour l'aide.\n");
                    return true;
                }
                return false;
        }
    }

    @Override
    public void executer(GUI gui) {
    }
}