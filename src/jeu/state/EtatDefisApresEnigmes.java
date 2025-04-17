package jeu.state;

import jeu.controller.Jeu;
import jeu.view.GUI;

/**
 * Gère l'état du jeu après que toutes les énigmes ont été résolues.
 */
public class EtatDefisApresEnigmes implements EtatHandler {

    @Override
    public void entrer(Jeu jeu) {
        jeu.afficherImage("j2.jpeg");
        jeu.afficherTexteProgressif(
            "Félicitations ! Vous avez résolu toutes les énigmes!\n\n" +
            "La porte se déverrouille. Vous pouvez maintenant explorer les sorties suivantes:\n" +
            "Godo, f1, f2, f3, J3\n\n" +
            "Choisissez une sortie en tapant son nom.",
            300
        );
    }

    @Override
    public void sortir(Jeu jeu) {
        // Rien de spécial à faire en sortant de cet état
    }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        String direction = commande.toUpperCase();

        switch (direction) {
            case "GODO":
                jeu.afficherTexteProgressif(
                    "Cette sortie est encore verrouillée. Vous devez trouver un moyen de la déverrouiller.",
                    300
                );
                return true;

            case "F1":
                jeu.afficherImage("f1.jpeg");
                jeu.afficherTexteProgressif(
                    "Vous avez choisi une sortie dangereuse!\n\n" +
                    "Vous êtes tombé dans un piège mortel. Game Over.",
                    300
                );
                jeu.getGui().enable(false);
                jeu.changerEtat(EtatJeu.DEFAITE);
                return true;

            case "F2":
                jeu.afficherImage("f2.jpeg");
                jeu.afficherTexteProgressif(
                    "Vous entrez dans une nouvelle pièce...\n\n" +
                    "Un monstre bloque votre passage! Que faites-vous?\n" +
                    "Options: Absorber,  A (Attaquer), F (Fouiller la pièce)",
                    300
                );
                jeu.changerEtat(EtatJeu.DEFIS_RENCONTRE_MONSTRE);
                return true;

            case "F3":
                jeu.afficherTexteProgressif(
                    "Cette sortie n'existe pas. Choisissez une sortie valide.",
                    300
                );
                return true;

            case "J3":
                jeu.afficherTexteProgressif(
                    "Cette sortie est accessible, mais il y a autre chose à faire avant de continuer.",
                    300
                );
                return true;

            default:
                // Si aucune direction valide n'est reconnue, afficher un message
                if (!direction.isEmpty()) {
                    jeu.afficherTexteProgressif(
                        "Sortie inconnue. Choisissez parmi les sorties disponibles: Godo, f1, f2, f3, J3",
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