package gui;

public class Theme {
    public String nom;
    public String fichier;
    public String preview;

    public Theme(String nom, String fichier, String preview){
        this.nom = nom;
        this.fichier = fichier;
        this.preview = preview;
    }

    public static Theme t0 = new Theme("Défaut", "file:src/Pictures/fond.png", "file:src/Pictures/Themes/t0.png");
    public static Theme t1 = new Theme("Night étoilée", "file:src/Pictures/Themes/NightSky.png", "file:src/Pictures/Themes/t1.png");
    public static Theme t2 = new Theme("Aurores Boeréales", "file:src/Pictures/Themes/Boreal.png", "file:src/Pictures/Themes/t2.png");
    public static Theme t3 = new Theme("Forêt", "file:src/Pictures/Themes/Forest.png", "file:src/Pictures/Themes/t3.png");
    public static Theme t4 = new Theme("Rétro", "file:src/Pictures/Themes/retro.gif", "file:src/Pictures/Themes/t4.png");

}
