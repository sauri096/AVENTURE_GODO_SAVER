package jeu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsable de la gestion et du préchargement des images.
 */
public class ImageManager {
    // Cache d'images préchargées
    private Map<String, ImageIcon> imageCache;
    
    // Dimensions maximales pour les images
    private final int MAX_WIDTH = 800;
    private final int MAX_HEIGHT = 600;
    
    /**
     * Constructeur de la classe ImageManager.
     */
    public ImageManager() {
        imageCache = new HashMap<>();
    }
    
    /**
     * Précharge les images pour éviter les délais lors des transitions.
     */
    public void prechargerImages() {
        try {
            // Liste des images à précharger
            String[] nomImages = {
                "Accueil.png",  "j2.jpeg", "f1.jpeg",
                "f2.jpeg", "j4.jpeg", "j5.jpeg", "win1.jpeg", "J3.jpeg","jf11.jpeg",
                "jfl.jpeg","jf0.jpeg","jf01.jpeg","jf02.jpeg",
                "jm1.jpeg","jm2.jpeg","win2.jpeg",
                "jfp.jpeg","jfp3.jpeg","jp2.jpeg","jfp4.jpeg"
            };
            
            for (String nom : nomImages) {
                chargerImage(nom);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du préchargement des images: " + e.getMessage());
        }
    }
    
    /**
     * Charge une image et l'ajoute au cache.
     *
     * @param nomImage Le nom du fichier image à charger
     * @return L'ImageIcon créée, ou null en cas d'échec
     */
    public ImageIcon chargerImage(String nomImage) {
        try {
            URL imageURL = this.getClass().getClassLoader().getResource("jeu/images/" + nomImage);
            if (imageURL != null) {
                BufferedImage imgOriginal = ImageIO.read(imageURL);
                Image scaledImage = redimensionnerImage(imgOriginal);
                ImageIcon icon = new ImageIcon(scaledImage);
                
                // Mettre en cache
                imageCache.put(nomImage, icon);
                return icon;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image " + nomImage + ": " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Obtient une image du cache. Si l'image n'est pas en cache, tente de la charger.
     *
     * @param nomImage Le nom du fichier image
     * @return L'ImageIcon correspondante, ou null si impossible à obtenir
     */
    public ImageIcon getImage(String nomImage) {
        // Vérifier si l'image est dans le cache
        if (imageCache.containsKey(nomImage)) {
            return imageCache.get(nomImage);
        }
        
        // Sinon, essayer de la charger
        return chargerImage(nomImage);
    }
    
    /**
     * Redimensionne une image pour s'adapter aux dimensions maximales.
     *
     * @param original L'image originale
     * @return L'image redimensionnée
     */
    private Image redimensionnerImage(BufferedImage original) {
        // Calculer les nouvelles dimensions en préservant le ratio
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();
        
        // Calculer le ratio pour le redimensionnement
        double ratio = Math.min(
            (double) MAX_WIDTH / originalWidth,
            (double) MAX_HEIGHT / originalHeight
        );
        
        // Calculer les nouvelles dimensions
        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);
        
        return original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
    
    /**
     * Vide le cache d'images.
     */
    public void viderCache() {
        imageCache.clear();
    }
    
    /**
     * Retire une image du cache.
     *
     * @param nomImage Le nom de l'image à retirer
     */
    public void retirerDuCache(String nomImage) {
        imageCache.remove(nomImage);
    }
    
    /**
     * Vérifie si une image est dans le cache.
     *
     * @param nomImage Le nom de l'image à vérifier
     * @return true si l'image est dans le cache, false sinon
     */
    public boolean estDansCache(String nomImage) {
        return imageCache.containsKey(nomImage);
    }
    
    /**
     * Obtient le nombre d'images dans le cache.
     *
     * @return Le nombre d'images dans le cache
     */
    public int getNombreImagesCache() {
        return imageCache.size();
    }
}