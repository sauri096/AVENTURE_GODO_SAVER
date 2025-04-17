package jeu.controller;

import jeu.controller.commands.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire de commandes du jeu.
 * Implémente le pattern Command pour faciliter l'ajout de nouvelles commandes.
 */
public class CommandManager {
    private Map<String, Command> commandes;
    private Jeu jeu;

    /**
     * Constructeur de la classe CommandManager.
     *
     * @param jeu L'instance du jeu
     */
    public CommandManager(Jeu jeu) {
        this.jeu = jeu;
        this.commandes = new HashMap<>();
    }

    /**
     * Initialise les commandes disponibles dans le jeu.
     */
    public void initialiserCommandes() {
        // Commandes globales
        enregistrerCommande("aide", new AideCommand());
        enregistrerCommande("?", new AideCommand());
        enregistrerCommande("quitter", new QuitterCommand());
        enregistrerCommande("q", new QuitterCommand());
        enregistrerCommande("recommencer", new RecommencerCommand());
        enregistrerCommande("r", new RecommencerCommand());
        enregistrerCommande("pause", new PauseCommand());
        enregistrerCommande("p", new PauseCommand());

        // Commandes de navigation et exploration
        enregistrerCommande("aller", new AllerCommand());
        enregistrerCommande("sortir", new AllerCommand());
        enregistrerCommande("s", new AllerCommand());



        // Commandes spécifiques au scénario Sauvetage
        enregistrerCommande("absorber", new AbsorberCommand());
        enregistrerCommande("attaquer", new AttaquerCommand());
        enregistrerCommande("a", new AttaquerCommand());

        enregistrerCommande("liberer", new LibererCommand());


    }

    public void enregistrerCommande(String nom, Command commande) {
        commandes.put(nom.toLowerCase(), commande);
    }

    public boolean executerCommande(String nom, String[] args) {
        Command commande = commandes.get(nom.toLowerCase());
        if (commande != null) {
            return commande.executer(args, jeu);
        }
        return false;
    }



    public String getAideCommandes() {
        StringBuilder aide = new StringBuilder("Commandes disponibles:\n");
        Map<String, StringBuilder> categories = new HashMap<>();

        commandes.forEach((nom, cmd) -> {
            String categorie = cmd.getCategorie();
            if (!categories.containsKey(categorie)) {
                categories.put(categorie, new StringBuilder());
            }
            categories.get(categorie).append("  ").append(nom).append(" - ").append(cmd.getDescription()).append("\n");
        });

        categories.forEach((categorie, texte) -> {
            aide.append("\n").append(categorie).append(":\n").append(texte);
        });

        return aide.toString();
    }
}
