package jeu.controller;

import jeu.model.Scenario;
import jeu.model.Zone;
import jeu.state.EtatJeu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe gérant la sauvegarde et le chargement des parties.
 */
public class SauvegardeManager {
    private static final String NOM_FICHIER_SAUVEGARDE = "sauvegarde.dat";
    
    /**
     * Vérifie si une sauvegarde existe.
     *
     * @return true si une sauvegarde existe, false sinon
     */
    public boolean existeSauvegarde() {
        File fichierSauvegarde = new File(NOM_FICHIER_SAUVEGARDE);
        return fichierSauvegarde.exists() && fichierSauvegarde.length() > 0;
    }
    
    /**
     * Sauvegarde l'état actuel du jeu.
     *
     * @param jeu L'instance du jeu à sauvegarder
     */
    public void sauvegarder(Jeu jeu) {

        EtatSauvegarde sauvegarde = new EtatSauvegarde(jeu);
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(NOM_FICHIER_SAUVEGARDE))) {
            out.writeObject(sauvegarde);
            out.flush();
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde: " + e.getMessage());
        }
    }
    
    /**
     * Charge une partie sauvegardée.
     *
     * @param jeu L'instance du jeu dans laquelle charger la sauvegarde
     * @return true si le chargement a réussi, false sinon
     */
    public boolean charger(Jeu jeu) {
        if (!existeSauvegarde()) {
            return false;
        }
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(NOM_FICHIER_SAUVEGARDE))) {
            EtatSauvegarde sauvegarde = (EtatSauvegarde) in.readObject();
            sauvegarde.appliquerA(jeu);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Supprime le fichier de sauvegarde.
     *
     * @return true si la suppression a réussi, false sinon
     */
    public boolean supprimerSauvegarde() {
        File fichierSauvegarde = new File(NOM_FICHIER_SAUVEGARDE);
        if (fichierSauvegarde.exists()) {
            return fichierSauvegarde.delete();
        }
        return true;
    }
    
    /**
     * Classe interne représentant l'état du jeu à sauvegarder.
     */
    private static class EtatSauvegarde implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private final String pseudoJoueur;
        private final EtatJeu etatJeu;
        private final String idScenarioCourant;
        private final String idZoneCourante;
        private final int enigmeCourante;
        private final boolean enigmesResolues;
        private final boolean forceTrouvee;
        private final boolean tentativeAttaqueSansForce;
        private final boolean monstreTue;
        
        /**
         * Constructeur qui extrait les données du jeu.
         *
         * @param jeu L'instance du jeu
         */
        public EtatSauvegarde(Jeu jeu) {
            this.pseudoJoueur = jeu.getJoueur().getPseudo();
            this.etatJeu = jeu.getEtatManager().getEtatActuel();
            
            Scenario scenario = jeu.getScenarioManager().getScenarioActuel();
            this.idScenarioCourant = scenario.getNom();
            

            Zone zoneCourante = jeu.getZoneCourante();

            this.idZoneCourante = trouverIdZone(scenario, zoneCourante);
            
            this.enigmeCourante = scenario.getEnigmeCourante() != null ? scenario.enigmeCourante : -1;
            this.enigmesResolues = scenario.sontEnigmesResolues();
            this.forceTrouvee = scenario.estForceTrouvee();
            this.tentativeAttaqueSansForce = scenario.aTenteAttaqueSansForce();
            this.monstreTue = scenario.estMonstreTue();
        }
        
        /**
         * Trouve l'ID d'une zone dans un scénario.
         * 
         * @param scenario Le scénario contenant la zone
         * @param zone La zone dont on cherche l'ID
         * @return L'ID de la zone, ou "accueil" par défaut
         */
        private String trouverIdZone(Scenario scenario, Zone zone) {

            for (String id : scenario.getZonesIds()) {
                if (scenario.getZone(id) == zone) {
                    return id;
                }
            }
            return "accueil";
        }
        
        /**
         * Applique l'état sauvegardé à une instance de jeu.
         *
         * @param jeu L'instance du jeu à mettre à jour
         */
        public void appliquerA(Jeu jeu) {
            jeu.getJoueur().setPseudo(pseudoJoueur);
            jeu.getJoueur().setPseudoSaisi(true);
            
            // Charger le scénario
            jeu.getScenarioManager().chargerScenario(idScenarioCourant);
            Scenario scenario = jeu.getScenarioManager().getScenarioActuel();
            
            // Restaurer l'état du scénario
            if (enigmeCourante >= 0) {
                scenario.enigmeCourante = enigmeCourante;
            }
            if (enigmesResolues) {
                scenario.setEnigmesResolues(true);
            }
            if (forceTrouvee) {
                scenario.trouverForce();
            }
            if (tentativeAttaqueSansForce) {
                scenario.marquerTentativeAttaqueSansForce();
            }
            if (monstreTue) {
                scenario.tuerMonstre();
            }
            
            Zone zone = scenario.getZone(idZoneCourante);
            if (zone != null) {
                jeu.setZoneCourante(zone);
            } else {
                jeu.setZoneCourante(scenario.getZoneDepart());
            }
            
            jeu.changerEtat(etatJeu);
        }
    }
}