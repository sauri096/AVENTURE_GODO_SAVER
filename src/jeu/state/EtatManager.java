package jeu.state;

import jeu.controller.Jeu;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire d'états du jeu, implémente le pattern State
 * pour faciliter la transition entre les différents états du jeu.
 */
public class EtatManager {
    private EtatJeu etatActuel;
    private Map<EtatJeu, EtatHandler> handlers;

    /**
     * Constructeur qui initialise tous les handlers d'état.
     */
    public EtatManager() {
        handlers = new HashMap<>();

        // Initialiser tous les handlers d'état
        handlers.put(EtatJeu.ACCUEIL, new EtatAccueil());
        handlers.put(EtatJeu.CHOIX_MODE, new EtatChoixMode());
        handlers.put(EtatJeu.DEFIS_INTRODUCTION, new EtatDefisIntroduction());
        handlers.put(EtatJeu.DEFIS_ENIGMES, new EtatDefisEnigmes());
        handlers.put(EtatJeu.DEFIS_APRES_ENIGMES, new EtatDefisApresEnigmes());
        handlers.put(EtatJeu.DEFIS_RENCONTRE_MONSTRE, new EtatDefisRencontreMonstre());
        handlers.put(EtatJeu.DEFIS_CHERCHER_FORCE, new EtatDefisForceChercher());
        handlers.put(EtatJeu.DEFIS_APRES_FORCE, new EtatDefisApresForce());
        handlers.put(EtatJeu.EXPLORATION_MODE, new EtatExplorationGuidee());
        handlers.put(EtatJeu.PAUSE, new EtatPause());
        handlers.put(EtatJeu.VICTOIRE, new EtatVictoire());
        handlers.put(EtatJeu.DEFAITE, new EtatDefaite());
        handlers.put(EtatJeu.CONFIRMATION_RECOMMENCER, new EtatConfirmationRecommencer());
        handlers.put(EtatJeu.CONFIRMATION_QUITTER, new EtatConfirmationQuitter());
        handlers.put(EtatJeu.DEUXIEME_ETAPE, new EtatExplorationGuidee()); // Utiliser la version guidée aussi ici

        // État initial
        etatActuel = EtatJeu.ACCUEIL;
    }

    /**
     * Change l'état actuel du jeu.
     *
     * @param nouvelEtat Le nouvel état du jeu
     * @param jeu L'instance du jeu
     */
    public void changerEtat(EtatJeu nouvelEtat, Jeu jeu) {
        if (etatActuel != null && handlers.containsKey(etatActuel)) {
            handlers.get(etatActuel).sortir(jeu);
        }

        etatActuel = nouvelEtat;

        if (handlers.containsKey(etatActuel)) {
            handlers.get(etatActuel).entrer(jeu);
        }
    }

    /**
     * Traite une commande selon l'état actuel.
     *
     * @param commande La commande à traiter
     * @param jeu L'instance du jeu
     * @return true si la commande a été traitée, false sinon
     */
    public boolean traiterCommande(String commande, Jeu jeu) {
        if (handlers.containsKey(etatActuel)) {
            return handlers.get(etatActuel).traiterCommande(commande, jeu);
        }
        return false;
    }

    /**
     * Obtient l'état actuel du jeu.
     *
     * @return L'état actuel
     */
    public EtatJeu getEtatActuel() {
        return etatActuel;
    }
}