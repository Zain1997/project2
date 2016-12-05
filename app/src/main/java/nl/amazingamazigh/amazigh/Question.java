package nl.amazingamazigh.amazigh;

/**
 * Created by DannyW on 2-6-2015.
 */
public class Question {
    int id;
    int category;
    Translation answer;

    public Question() {

    }

    public Question(int category, Translation answer) {
        this.category = category;
        this.answer = answer;
    }


    public int getId() {
        return id;
    }
    public int getCategory() {
        return category;
    }
    public Translation getAnswer() {
        return answer;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public void setAnswer(Translation answer) {
        this.answer = answer;
    }
}
