package nl.amazingamazigh.amazigh;

import java.util.List;
import static java.util.Arrays.asList;

public enum Language {
    dutch, german, spanish, french, english;

    static List<String> germanCategories = asList("Wähle eine Kategorie", "Tiere 1", "Frucht", "Insekten", "Gemüse", "Tiere 2", "Essen", "Kleidung", "Farbe", "Wetter", "Experte");
    static List<String> spanishCategories = asList("Elige una categoría", "Animales 1", "Fruta", "Insectos", "vegetal", "Animales 2", "Comida", "Ropa", "Color", "Clima", "Experto");
    static List<String> frenchCategories = asList("Choisissez une catégorie", "Animaux 1", "Fruit", "Insectes", "Légume", "Animaux 2", "Aliments", "Vêtements", "Couleur", "Météo", "Expert");
    static List<String> englishCategories = asList("Choose a category", "Animals 1", "Fruit", "Insects", "Vegetable", "Animals 2", "Food", "Clothing", "Color", "Weather", "Expert");
    static List<String> dutchCategories = asList("Kies een categorie", "Dieren 1", "Fruit", "Insecten", "Groente", "Dieren 2", "Eten", "Kleding", "Kleuren", "Weer", "Expert");

    static List<String> germanMenuItems = asList("Üben", "Spielen", "Scores", "Über", "Sprache");
    static List<String> spanishMenuItems = asList("Práctica", "Juega", "Scores", "Acerca", "Idioma");
    static List<String> frenchMenuItems = asList("Pratique", "Jouer", "Scores", "À propos", "Langue");
    static List<String> englishMenuItems = asList("Practice", "Play", "Scores", "About", "Language");
    static List<String> dutchMenuItems = asList("Oefen", "Speel", "Scores", "Over", "Taal");

    public static Language getLanguageFromInt(int language){
        switch (language){
            case 2:
                return Language.german;
            case 3:
                return Language.spanish;
            case 4:
                return Language.french;
            case 5:
                return Language.english;
            default:
                return Language.dutch;
        }
    }

    public static String getMenuItemsFromSubject(int language, int subject){
        switch (language){
            case 2:
                return germanMenuItems.get(subject);
            case 3:
                return spanishMenuItems.get(subject);
            case 4:
                return frenchMenuItems.get(subject);
            case 5:
                return englishMenuItems.get(subject);
            default:
                return dutchMenuItems.get(subject);
        }
    }

    public static String getCategoryFromLanguageAndInt(int language, int category){
        switch(language){
            case 2:
                return germanCategories.get(category);
            case 3:
                return spanishCategories.get(category);
            case 4:
                return frenchCategories.get(category);
            case 5:
                return englishCategories.get(category);
            default:
                return dutchCategories.get(category);
        }
    }
}
