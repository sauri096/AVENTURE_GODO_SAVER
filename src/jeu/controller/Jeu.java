package jeu.controller;

import jeu.model.ScenarioSauvetage;
import jeu.model.Zone;
import jeu.state.EtatJeu;
import jeu.state.EtatManager;
import jeu.view.GUI;

/**
 * Classe principale qui gère la logique du jeu.
 * Sert de point central pour coordonner les différents composants.
 */
public class Jeu {
    private GUI gui;
    private Joueur joueur;
    private Zone zoneCourante;
    private ScenarioManager scenarioManager;
    private CommandManager commandManager;
    private EtatManager etatManager;
    private SauvegardeManager sauvegardeManager;

    /**
     * Constructeur de la classe Jeu.
     */
    public Jeu() {

        joueur = new Joueur();
        scenarioManager = new ScenarioManager();
        commandManager = new CommandManager(this);
        etatManager = new EtatManager();
        sauvegardeManager = new SauvegardeManager();


        commandManager.initialiserCommandes();

        // Charger le scénario par défaut
        scenarioManager.chargerScenario("defaut");
        zoneCourante = scenarioManager.getScenarioActuel().getZoneDepart();
    }

    /**
     * Définit l'interface graphique et démarre le jeu.
     *
     * @param gui L'interface graphique à utiliser
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
        demarrer();
    }

    /**
     * Démarre le jeu en vérifiant l'existence d'une sauvegarde.
     */
    private void demarrer() {
        if (sauvegardeManager.existeSauvegarde()) {
            changerEtat(EtatJeu.PAUSE);
        } else {
            changerEtat(EtatJeu.ACCUEIL);
        }
    }

    /**
     * Traite la commande entrée par l'utilisateur.
     *
     * @param commandeTexte La commande sous forme de texte
     */
    public void traiterCommande(String commandeTexte) {
        if (commandeTexte == null || commandeTexte.trim().isEmpty()) {
            return;
        }


        gui.afficher("> " + commandeTexte + "\n");

        boolean commandeTraitee = etatManager.traiterCommande(commandeTexte, this);

        if (!commandeTraitee) {
            String[] parties = commandeTexte.trim().split("\\s+", 2);
            String nom = parties[0].toLowerCase();
            String[] args = parties.length > 1 ? parties[1].split("\\s+") : new String[0];

            commandManager.executerCommande(nom, args);
        }
    }

    /**
     * Change l'état actuel du jeu.
     *
     * @param nouvelEtat Le nouvel état du jeu
     */
    public void changerEtat(EtatJeu nouvelEtat) {
        etatManager.changerEtat(nouvelEtat, this);
    }

    /**
     * Affiche du texte dans l'interface progressivement.
     *
     * @param texte Le texte à afficher
     * @param delai Le délai entre chaque ligne (en ms)
     */
    public void afficherTexteProgressif(String texte, int delai) {
        gui.afficherTexteProgressif(texte, delai);
    }

    /**
     * Affiche une image dans l'interface.
     *
     * @param nomImage Le nom de l'image à afficher
     */
    public void afficherImage(String nomImage) {
        gui.afficheImage(nomImage);
    }

    /**
     * Sauvegarde l'état actuel du jeu.
     */
    public void sauvegarder() {
        sauvegardeManager.sauvegarder(this);
        gui.afficher("Partie sauvegardée.\n");
    }

    /**
     * Charge une partie sauvegardée.
     */
    public void charger() {
        sauvegardeManager.charger(this);
        gui.afficher("Partie chargée. Bienvenue à nouveau, " + joueur.getPseudo() + " !\n");
    }
    public void setEtat(EtatJeu nouvelEtat) {
        this.getEtatManager().changerEtat(nouvelEtat, this);
    }
    public void executerEtatActuel() {
        etatManager.changerEtat(etatManager.getEtatActuel(), this);
    }



    /**
     * Réinitialise le jeu pour une nouvelle partie.
     */
    public void reinitialiser() {
        joueur = new Joueur();
        scenarioManager.getScenarioActuel().reinitialiser();
        zoneCourante = scenarioManager.getScenarioActuel().getZoneDepart();
        changerEtat(EtatJeu.ACCUEIL);
    }



    public GUI getGui() {
        return gui;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Zone getZoneCourante() {
        return zoneCourante;
    }

    public void setZoneCourante(Zone zoneCourante) {
        this.zoneCourante = zoneCourante;

        if (scenarioManager.getScenarioActuel() instanceof ScenarioSauvetage) {
            ScenarioSauvetage scenario = (ScenarioSauvetage) scenarioManager.getScenarioActuel();
            if (zoneCourante.equals(scenario.getZoneGodo())) {
                getGui().afficher(" Félicitations ! Vous avez sauvé tout le monde et terminé le scénario !\n");
                changerEtat(EtatJeu.VICTOIRE);
            }
        }
    }


    public ScenarioManager getScenarioManager() {
        return scenarioManager;
    }


    public EtatManager getEtatManager() {
        return etatManager;
    }

    public GUI getGUI() {
        return gui;
    }
}