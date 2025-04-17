package jeu.state;

public enum Sortie {
    // Valeurs originales
    ACCUEIL, DEFIS, EXPLORATION,

    // Directions Partie 2
    LABO,PRISON,FOZIA,IZOU,

    // Directions spécifiques au scénario
    GODO, F1, F2, F3, J3;

    /**
     * Convertit une chaîne de caractères en une valeur de l'énumération Sortie.
     * Cette méthode est insensible à la casse.
     *
     * @param direction La chaîne de caractères représentant la direction
     * @return La valeur de l'énumération correspondante, ou null si aucune correspondance n'est trouvée
     */
    public static Sortie fromString(String direction) {
        if (direction == null) {
            return null;
        }

        String dir = direction.toUpperCase();

        // Essayer d'obtenir directement la valeur de l'énumération
        try {
            return valueOf(dir);
        } catch (IllegalArgumentException e) {
            // Si la valeur directe n'existe pas, essayer avec des abréviations
            switch (dir) {
                case "L":
                    return LABO;
                case "P":
                    return PRISON;
                case "GODO":
                    return GODO;
                case "F":
                    return FOZIA;
                case "I":
                    return IZOU;
                default:
                    return null;
            }
        }
    }
}