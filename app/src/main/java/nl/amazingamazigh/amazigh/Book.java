package nl.amazingamazigh.amazigh;

import com.orm.SugarApp;
import com.orm.SugarRecord;

/**
 * Created by Zain on 19-12-2016.
 */

public class Book extends SugarRecord {

    String title;

    public Book()
    {}

    public Book(String title){
        this.title=title;
    }

    public String toString(){
        return title;
    }
}
