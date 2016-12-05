package nl.amazingamazigh.amazigh;

/**
 * Created by DannyW on 6-7-2015.
 */
public class Leaderboard {
    private int id;
    private String name;
    private int score;
    private int category;

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getCategory() {return this.category; }
    public void setCategory(int category) { this.category = category; }
}
