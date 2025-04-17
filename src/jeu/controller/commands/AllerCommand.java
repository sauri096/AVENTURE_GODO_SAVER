package jeu.controller.commands;

import jeu.controller.Jeu;
import jeu.model.Zone;
import jeu.state.EtatJeu;

/**
 * Commande permettant au joueur de se déplacer dans une direction.
 */
public class AllerCommand implements Command {

    @Override
    public boolean executer(String[] args, Jeu jeu) {
        if (jeu.getEtatManager().getEtatActuel() != EtatJeu.EXPLORATION_MODE) {
            jeu.getGui().afficher("Vous ne pouvez pas vous déplacer en ce moment.\n");
            return true;
        }

        if (args.length == 0) {
            jeu.getGui().afficher("Où voulez-vous aller ? Spécifiez une direction.\n");
            return true;
        }

        String direction = args[0].toUpperCase();
        Zone zoneCourante = jeu.getZoneCourante();
        Zone nouvelleZone = zoneCourante.obtientSortie(direction);

        if (nouvelleZone == null) {
            jeu.getGui().afficher("Cette direction n'existe pas : " + direction + "\n");
            return true;
        }

        if (nouvelleZone.estVerrouillee()) {
            jeu.getGui().afficher("Cette zone est verrouillée pour le moment.\n");
            return true;
        }

        // Déplacement valide
        jeu.setZoneCourante(nouvelleZone);
        jeu.afficherImage(nouvelleZone.nomImage());

        return true;
    }

    @Override
    public String getDescription() {
        return "Se déplacer dans une direction ";
    }

    @Override
    public String getCategorie() {
        return "Navigation";
    }
}