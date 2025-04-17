package jeu.model;

import java.util.Arrays;
import java.util.List;


public class Enigme {
    private final String texte;
    private final List<String> options;
    private final String reponseCorrecte;
    private int essaisRestants;
    private final int MAX_ESSAIS = 2;

    /**
     * Constructeur de la classe Enigme.
     *
     * @param texte Le texte de l'énigme
     * @param reponseCorrecte La réponse correcte à l'énigme
     * @param options Les options de réponse possibles
     */
    public Enigme(String texte, String reponseCorrecte, String... options) {
        this.texte = texte;
        this.reponseCorrecte = reponseCorrecte;
        this.options = Arrays.asList(options);
        this.essaisRestants = MAX_ESSAIS;
    }


    public String getTexte() {
        return texte;
    }


    public List<String> getOptions() {
        return options;
    }

    /**
     * Vérifie si la réponse donnée est correcte.
     *
     * @param reponse La réponse à vérifier
     * @return true si la réponse est correcte, false sinon
     */
    public boolean verifierReponse(String reponse) {

        if (reponseCorrecte.equalsIgnoreCase("N'Djamena") && reponse.trim().equalsIgnoreCase("N")) {
            return true;
        }
        return reponseCorrecte.equalsIgnoreCase(reponse.trim());
    }


    public int getEssaisRestants() {
        return essaisRestants;
    }


    public void diminuerEssai() {
        if (essaisRestants > 0) {
            essaisRestants--;
        }
    }


    public void reinitialiserEssais() {
        essaisRestants = MAX_ESSAIS;
    }

    /**
     * Retourne une représentation textuelle de l'énigme avec ses options.
     *
     * @return L'énigme formatée avec ses options
     */
    public String afficherEnigme() {
        StringBuilder sb = new StringBuilder(texte);
        sb.append("\nOptions: ");
        for (String option : options) {
            sb.append(option).append(", ");
        }

        if (!options.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }
}