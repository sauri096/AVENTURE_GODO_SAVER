package jeu.view;
import jeu.controller.Jeu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

/**
 * Classe gérant l'interface graphique du jeu.
 */
public class GUI implements ActionListener {
    private Jeu jeu;
    private JFrame fenetre;
    private JTextField entree;
    private JTextArea texte;
    private JLabel image;
    private Timer textTimer;

    // Dimensions fixes pour le panneau d'image
    private final int IMAGE_PANEL_WIDTH = 800;
    private final int IMAGE_PANEL_HEIGHT = 600;

    // Gestionnaire d'images
    private ImageManager imageManager;

    /**
     * Constructeur de la classe GUI.
     *
     * @param jeu L'instance du jeu à connecter à l'interface
     */
    public GUI(Jeu jeu) {
        this.jeu = jeu;
        this.imageManager = new ImageManager();
        this.textTimer = new Timer();
        creerGUI();
        jeu.setGUI(this);
    }


    /**
     * Affiche le texte spécifié dans la zone de texte.
     *
     * @param s Le texte à afficher
     */
    public void afficher(String s) {
        texte.append(s);
        texte.setCaretPosition(texte.getDocument().getLength());
    }

    /**
     * Insère un saut de ligne dans la zone de texte.
     */
    public void afficher() {
        afficher("\n");
    }

    /**
     * Affiche du texte progressivement dans l'interface.
     *
     * @param texteComplet Le texte complet à afficher
     * @param delai Le délai entre chaque ligne (en ms)
     */
    public void afficherTexteProgressif(String texteComplet, int delai) {
        String[] lignes = texteComplet.split("\n");

        for (int i = 0; i < lignes.length; i++) {
            final int index = i;
            textTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        afficher(lignes[index]);
                        afficher();
                    });
                }
            }, i * delai);
        }
    }

    /**
     * Affiche l'image spécifiée dans le panneau d'image.
     *
     * @param nomImage Le nom du fichier image à afficher
     */
    public void afficheImage(String nomImage) {
        ImageIcon icon = imageManager.getImage(nomImage);
        if (icon != null) {

            image.getParent().setIgnoreRepaint(true);

            image.setIcon(icon);

            image.getParent().setIgnoreRepaint(false);
            image.getParent().repaint();
        }
    }






    /**
     * Active ou désactive le champ de saisie.
     *
     * @param ok true pour activer, false pour désactiver
     */
    public void enable(boolean ok) {
        entree.setEditable(ok);
        if (!ok) {
            entree.getCaret().setBlinkRate(0);
        } else {
            entree.getCaret().setBlinkRate(500);
            entree.requestFocus();
        }
    }

    /**
     * Crée l'interface graphique du jeu.
     */
    private void creerGUI() {
        fenetre = new JFrame("Jeu d'aventure");

        entree = new JTextField(34);

        texte = new JTextArea();
        texte.setEditable(false);
        JScrollPane textScroller = new JScrollPane(texte);
        textScroller.setPreferredSize(new Dimension(600, 200));
        textScroller.setMinimumSize(new Dimension(400, 150));

        // Créer le panneau d'image avec une taille fixe
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(IMAGE_PANEL_WIDTH, IMAGE_PANEL_HEIGHT));
        imagePanel.setMinimumSize(new Dimension(IMAGE_PANEL_WIDTH, IMAGE_PANEL_HEIGHT));
        imagePanel.setMaximumSize(new Dimension(IMAGE_PANEL_WIDTH, IMAGE_PANEL_HEIGHT));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        image = new JLabel();
        image.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(image, BorderLayout.CENTER);

        // Configuration du panneau principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(imagePanel, BorderLayout.NORTH);
        panel.add(textScroller, BorderLayout.CENTER);

        // Panneau pour l'entrée de texte
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        JLabel inputLabel = new JLabel("Entrez votre commande :");
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(entree, BorderLayout.CENTER);

        // Ajouter une barre d'outils avec des boutons pour les commandes fréquentes
        JToolBar toolBar = creerBarreOutils();
        inputPanel.add(toolBar, BorderLayout.SOUTH);

        panel.add(inputPanel, BorderLayout.SOUTH);

        fenetre.getContentPane().add(panel, BorderLayout.CENTER);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        entree.addActionListener(this);

        // Définir une taille fixe
        fenetre.setSize(new Dimension(850, 900));
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);

        // Précharger les images
        imageManager.prechargerImages();

        fenetre.setVisible(true);
        entree.requestFocus();
    }

    /**
     * Crée une barre d'outils avec des boutons pour les commandes fréquentes.
     *
     * @return La barre d'outils créée
     */
    private JToolBar creerBarreOutils() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Bouton d'aide
        JButton aideBtn = new JButton("Aide");
        aideBtn.addActionListener(e -> executerCommande("?"));
        toolBar.add(aideBtn);

        toolBar.addSeparator();
        // Boutons de navigation
        JButton foullerBtn = new JButton("Fouiller");
        foullerBtn.addActionListener(e -> executerCommande("fouiller"));
        toolBar.add(foullerBtn);



        return toolBar;
    }

    /**
     * Gère les événements d'action, spécifiquement lorsque l'utilisateur
     * appuie sur Entrée dans le champ de texte.
     *
     * @param e L'événement d'action
     */
    public void actionPerformed(ActionEvent e) {
        executerCommande(entree.getText());
    }

    /**
     * Exécute la commande entrée par l'utilisateur.
     *
     * @param commande La commande à exécuter
     */
    private void executerCommande(String commande) {
        if (commande == null || commande.trim().isEmpty()) {
            return;
        }

        entree.setText("");
        jeu.traiterCommande(commande);
    }

    /**
     * Efface le contenu de la zone de texte.
     */
    public void effacerTexte() {
        texte.setText("");
    }

    /**
     * Change le titre de la fenêtre.
     *
     * @param titre Le nouveau titre
     */
    public void setTitre(String titre) {
        fenetre.setTitle(titre);
    }

    /**
     * Affiche une boîte de dialogue avec un message.
     *
     * @param message Le message à afficher
     * @param titre Le titre de la boîte de dialogue
     */
    public void afficherMessage(String message, String titre) {
        JOptionPane.showMessageDialog(fenetre, message, titre, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Affiche une boîte de dialogue de confirmation.
     *
     * @param message Le message à afficher
     * @param titre Le titre de la boîte de dialogue
     * @return true si l'utilisateur confirme, false sinon
     */
    public boolean confirmer(String message, String titre) {
        int reponse = JOptionPane.showConfirmDialog(
            fenetre, message, titre, JOptionPane.YES_NO_OPTION
        );
        return reponse == JOptionPane.YES_OPTION;
    }
}