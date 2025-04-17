package jeu.state;

import jeu.controller.Jeu;

import jeu.view.GUI;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * État spécial pour gérer le mode d'exploration guidée dans le scénario de sauvetage.
 * Implémente un flux précis avec des options limitées à chaque étape.
 */
public class EtatExplorationGuidee implements EtatHandler {
    String actionCommandes ="Commandes possibles : libérer , attaquer,absorber,fouiller \n";
    String directionCommandes="labo,prison,godo,fozia,izou \n";

    private enum Etape {
        POINT_DEPART,
        LABORATOIRE,
        APRES_ABSORPTION,
        PRISON_SANS_FORCE,
        PRISON,
        COMBAT,
        PRISON_VICTOIRE,
        ZONE_LIBERATION,
        SORTIE_GODO,
        VICTOIRE,
        ECHEC
    }

    private Etape etapeActuelle = Etape.POINT_DEPART;
    private int attaquesReussies = 0;
    private long debutCombat = 0;
    private Timer timerCombat;

    @Override
    public void entrer(Jeu jeu) {
        // Point de départ du scénario de sauvetage
        jeu.afficherImage("jf0.jpeg");
        jeu.afficherTexteProgressif(
                "Tu es devant un bâtiment mystérieux...\n",
                100
        );

        etapeActuelle = Etape.POINT_DEPART;
    }

    @Override
    public boolean traiterCommande(String commande, Jeu jeu) {
        String cmd = commande.trim().toLowerCase();

        switch (etapeActuelle) {
            case POINT_DEPART:
                if (cmd.equals("labo") ) {
                    entrerLaboratoire(jeu);
                    return true;
                } else if (cmd.equals("prison") ) {
                    // Scénario A-2: essayer d'aller à la prison directement
                    entrerPrisonSansForce(jeu);
                    return true;
                }
                jeu.getGui().afficher(directionCommandes);
                return true;

            case LABORATOIRE:
                if (cmd.equals("absorber")) {
                    effectuerAbsorption(jeu);
                    return true;
                } else if (Arrays.asList("fouiller", "liberer", "attaquer").contains(cmd)) {
                    jeu.getGui().afficher("Cette commande n'est pas active ici.\n");
                    return true;
                }
                jeu.getGui().afficher(actionCommandes);
                return true;

            case PRISON_SANS_FORCE:
                if (cmd.equals("liberer")) {
                    jeu.getGui().afficherTexteProgressif(
                            "Tu dois récupérer la clé et la force avant de nous libérer.\n" +
                                    "Retourne au laboratoire pour absorber l'énergie.\n",
                            100
                    );
                    return true;
                } else if (cmd.equals("labo")) {
                    entrerLaboratoire(jeu);
                    return true;
                }
                jeu.getGui().afficher(actionCommandes + directionCommandes +"\n");
                return true;

            case APRES_ABSORPTION:
                List<String> directions = Arrays.asList("labo", "prison", "godo", "fozia", "izou");
                if (directions.contains(cmd) || cmd.startsWith("aller ") && directions.contains(cmd.substring(6))) {
                    String direction = cmd.startsWith("aller ") ? cmd.substring(6) : cmd;
                    if (direction.equals("prison")) {
                        entrerPrison(jeu);
                    } else {
                        jeu.getGui().afficher("Cette direction n'est pas encore implémentée. Essayez PRISON.\n");
                    }
                    return true;
                }
                jeu.getGui().afficher(directionCommandes + "Essayez d'aller sauver les otages.\n");
                return true;

            case PRISON:
                if (cmd.equals("attaquer")) {
                    demarrerCombat(jeu);
                    return true;
                } else if (Arrays.asList("liberer", "absorber").contains(cmd)) {
                    jeu.getGui().afficher("Cette commande n'est pas active ici..\n");
                    return true;
                }
                jeu.getGui().afficher(actionCommandes);
                return true;

            case COMBAT:
                if (cmd.equals("attaquer")) {
                    gererAttaque(jeu);
                    return true;
                }
                jeu.getGui().afficher("Continuez à attaquer plusieurs fois !\n");
                return true;

            case PRISON_VICTOIRE:
                List<String> directionsApresVictoire = Arrays.asList("labo", "prison", "godo", "fozia", "izou");
                if (directionsApresVictoire.contains(cmd) ||
                        cmd.startsWith("aller ") && directionsApresVictoire.contains(cmd.substring(6))) {
                    String direction = cmd.startsWith("aller ") ? cmd.substring(6) : cmd;
                    if (direction.equals("prison")) {
                        entrerZoneLiberation(jeu);
                    } else {
                        jeu.getGui().afficher("Cette direction n'est pas accessible actuellement.\n");
                    }
                    return true;
                }
                jeu.getGui().afficher(directionCommandes);
                return true;

            case ZONE_LIBERATION:
                if (cmd.equals("liberer")) {
                    effectuerLiberation(jeu);
                    return true;
                } else if (Arrays.asList("attaquer", "absorber").contains(cmd)) {
                    jeu.getGui().afficher("Cette commande n'est pas active ici..\n");
                    return true;
                }
                jeu.getGui().afficher(directionCommandes );
                return true;

            case SORTIE_GODO:
                List<String> directionsVersSortie = Arrays.asList("labo", "prison", "godo", "fozia", "izou");
                if (directionsVersSortie.contains(cmd) ||
                        cmd.startsWith("aller ") && directionsVersSortie.contains(cmd.substring(6))) {
                    String direction = cmd.startsWith("aller ") ? cmd.substring(6) : cmd;
                    if (direction.equals("godo")) {
                        terminerJeu(jeu);
                    } else {
                        jeu.getGui().afficher("Cette direction n'est pas accessible actuellement.\n");
                    }
                    return true;
                }
                jeu.getGui().afficher(directionCommandes);
                return true;

            case VICTOIRE:
            case ECHEC:
                // Traitement spécial pour ces états
                jeu.changerEtat(etapeActuelle == Etape.VICTOIRE ? EtatJeu.VICTOIRE : EtatJeu.DEUXIEME_ETAPE);
                return true;

            default:
                return false;
        }
    }

    private void entrerLaboratoire(Jeu jeu) {
        jeu.afficherImage("jfl.jpeg");
        jeu.afficherTexteProgressif(
                """
                        Tu entres dans le laboratoire...
                        Une étrange énergie t'envahit.
                        """+
                        actionCommandes,
                100
        );
        etapeActuelle = Etape.LABORATOIRE;
    }

    private void effectuerAbsorption(Jeu jeu) {
        jeu.afficherTexteProgressif(
                "Tu absorbes l'énergie du laboratoire... \n" +
                        "Une force nouvelle circule en toi !\n\n" +
                        "Après avoir absorbé, nouvelles directions disponibles :\n" +
                       directionCommandes,
                100
        );
        etapeActuelle = Etape.APRES_ABSORPTION;
    }

    private void entrerPrisonSansForce(Jeu jeu) {
        jeu.afficherImage("jf11.jpeg");
        jeu.afficherTexteProgressif(
                "Tu entres dans la prison mais tu n'as ni la clé ni la force nécessaire.\n\n" +
                        actionCommandes + directionCommandes,
                100
        );
        etapeActuelle = Etape.PRISON_SANS_FORCE;
    }

    private void entrerPrison(Jeu jeu) {
        jeu.afficherImage("jm1.jpeg");
        jeu.afficherTexteProgressif(
                "Tu entres dans la prison. Des ennemis surgissent !\n" + actionCommandes, 100);
        etapeActuelle = Etape.PRISON;
    }

    private void demarrerCombat(Jeu jeu) {
        attaquesReussies = 1;
        debutCombat = System.currentTimeMillis();
        etapeActuelle = Etape.COMBAT;

        timerCombat = new Timer();
        timerCombat.schedule(new TimerTask() {
            @Override
            public void run() {
                verifierCombat(jeu);
            }
        }, 2000);
    }

    private void gererAttaque(Jeu jeu) {
        long tempsEcoule = System.currentTimeMillis() - debutCombat;

        if (tempsEcoule <= 2000) {
            attaquesReussies++;
            jeu.getGui().afficher("⚔️ Attaque " + attaquesReussies + " réussie !\n");

            if (attaquesReussies >= 3) {
                if (timerCombat != null) {
                    timerCombat.cancel();
                    timerCombat = null;
                }
                victoire(jeu);
            }
        } else {
            jeu.getGui().afficher("Trop tard ! Le temps est écoulé.\n");
        }
    }

    private void verifierCombat(Jeu jeu) {
        if (etapeActuelle == Etape.COMBAT && attaquesReussies < 3) {
            echec(jeu);
        }
    }

    private void victoire(Jeu jeu) {
        jeu.afficherImage("jm2.jpeg");
        jeu.afficherTexteProgressif(
                "Tu as vaincu tous les ennemis ! Les otages sont encore enfermés mais tu peux maintenant accéder à la zone de libération.\n\n" +
                        directionCommandes ,
                100
        );
        etapeActuelle = Etape.PRISON_VICTOIRE;
    }

    private void entrerZoneLiberation(Jeu jeu) {
        jeu.afficherImage("jf11.jpeg");
        jeu.afficherTexteProgressif(
                "Tu entres dans la zone de libération. Les otages sont enfermés ici.\n\n" +
                        actionCommandes,
                100
        );
        etapeActuelle = Etape.ZONE_LIBERATION;
    }

    private void effectuerLiberation(Jeu jeu) {
        jeu.afficherImage("jf01.jpeg");
        jeu.afficherTexteProgressif(
                "Tu as libéré tous les otages ! 🎉\n" +
                        "La voie vers la sortie est maintenant ouverte.\n\n" +
                        directionCommandes,
                100
        );
        etapeActuelle = Etape.SORTIE_GODO;
    }

    private void terminerJeu(Jeu jeu) {
        jeu.afficherImage("win2.jpeg");
        jeu.afficherTexteProgressif(
                "Tu as réussi à sauver tous les otages et à sortir du complexe !\n" +
                        "Félicitations, tu as terminé le scénario de sauvetage avec succès ! 🏆\n\n" +
                        "Appuyez sur ENTRÉE pour continuer.",
                100
        );
        etapeActuelle = Etape.VICTOIRE;
    }

    private void echec(Jeu jeu) {
        jeu.afficherImage("f1.jpeg");
        jeu.afficherTexteProgressif(
                "Vous avez été submergé par les ennemis... \n" +
                        "Vous devez recommencer depuis le début appuyer n'importe quel touche.\n\n" ,
                100
        );
        etapeActuelle = Etape.ECHEC;
    }

    @Override
    public void sortir(Jeu jeu) {
        if (timerCombat != null) {
            timerCombat.cancel();
            timerCombat = null;
        }
    }

    @Override
    public void executer(GUI gui) {
    }
}