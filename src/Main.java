
import jeu.controller.Jeu;
import jeu.view.GUI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Point d'entrée principal du jeu d'aventure.
 */
public class Main {

    /**
     * Méthode principale qui démarre le jeu.
     *
     * @param args Les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Configurer l'interface utilisateur pour avoir un aspect natif
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // En cas d'échec, utiliser le look and feel par défaut
            System.err.println("Impossible de configurer le look and feel natif: " + e.getMessage());
        }

        // Démarrer le jeu dans l'EDT de Swing
        SwingUtilities.invokeLater(() -> {
            // Créer l'instance du jeu
            Jeu jeu = new Jeu();

            // Créer et configurer l'interface graphique
            new GUI(jeu);
        });
    }
}