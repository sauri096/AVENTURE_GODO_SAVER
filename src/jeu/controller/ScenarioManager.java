package jeu.controller;

import jeu.model.Scenario;
import jeu.model.ScenarioFactory;
import jeu.model.ScenarioSauvetage;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui gère les différents scénarios disponibles dans le jeu.
 */
public class ScenarioManager {
    private final Map<String, Scenario> scenarios;
    private Scenario scenarioActuel;
    
    /**
     * Constructeur de la classe ScenarioManager.
     */
    public ScenarioManager() {
        scenarios = new HashMap<>();
        initialiserScenarios();
    }
    
    /**
     * Initialise les scénarios disponibles dans le jeu.
     */
    private void initialiserScenarios() {
        Scenario scenarioDefaut = ScenarioFactory.creerScenarioDefis();

        scenarios.put("defaut", scenarioDefaut);
        


        scenarioActuel = scenarioDefaut;
    }
    public void chargerScenarioSauvetage() {
        ScenarioSauvetage scenario = new ScenarioSauvetage();
        scenarioActuel = scenario;
    }
    public void setScenarioActuel(Scenario scenario) {
        this.scenarioActuel = scenario;
    }



    /**
     * Charge un scénario par son nom.
     *
     * @param nom Le nom du scénario à charger
     * @return true si le scénario a été chargé, false sinon
     */
    public boolean chargerScenario(String nom) {
        if (scenarios.containsKey(nom)) {
            scenarioActuel = scenarios.get(nom);
            return true;
        }
        return false;
    }
    
    /**
     * Obtient le scénario actuellement chargé.
     *
     * @return Le scénario actuel
     */
    public Scenario getScenarioActuel() {
        return scenarioActuel;
    }
    
    /**
     * Ajoute un nouveau scénario.
     *
     * @param nom Le nom du scénario
     * @param scenario Le scénario à ajouter
     */
    public void ajouterScenario(String nom, Scenario scenario) {
        scenarios.put(nom, scenario);
    }
    
    /**
     * Liste les noms de tous les scénarios disponibles.
     *
     * @return Un tableau des noms de scénarios
     */
    public String[] listeScenarios() {
        return scenarios.keySet().toArray(new String[0]);
    }
    
    /**
     * Vérifie si un scénario existe.
     *
     * @param nom Le nom du scénario à vérifier
     * @return true si le scénario existe, false sinon
     */
    public boolean scenarioExiste(String nom) {
        return scenarios.containsKey(nom);
    }
    
    /**
     * Supprime un scénario.
     *
     * @param nom Le nom du scénario à supprimer
     * @return true si le scénario a été supprimé, false sinon
     */
    public boolean supprimerScenario(String nom) {
        // Ne pas supprimer le scénario actuel
        if (scenarioActuel == scenarios.get(nom)) {
            return false;
        }
        
        return scenarios.remove(nom) != null;
    }


}