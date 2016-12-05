package nl.amazingamazigh.amazigh;

public class Translation {

    public Translation(String dutch, String english, String spanish, String french, String german, String amazigh){
        this.dutch = dutch;
        this.english = english;
        this.spanish = spanish;
        this.french = french;
        this.german = german;
        this.amazigh = amazigh;
    }

    public Translation(String dutch, String translation){
        this.dutch = dutch;
        this.translation = translation;
    }

    public String getDutch() {
        return dutch;
    }

    public void setDutch(String dutch) {
        this.dutch = dutch;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String enlish) {
        this.english = enlish;
    }

    public String getSpanish() {
        return spanish;
    }

    public void setSpanish(String spanish) {
        this.spanish = spanish;
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        this.french = french;
    }

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    public String getAmazigh() {
        return amazigh;
    }

    public void setAmazigh(String amazigh) {
        this.amazigh = amazigh;
    }

    public String getTranslation() {
        return amazigh;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    private String dutch;
    private String english;
    private String spanish;
    private String french;
    private String german;
    private String amazigh;
    private String translation;
}
