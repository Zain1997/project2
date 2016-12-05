package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    Button btnOefen;
    Button btnSpeel;
    Button btnScores;
    Button btnOver;
    TextView textTaal;



    public void selectPractice(View v) {
        Intent myIntent = new Intent(MainActivity.this, Category.class);
        myIntent.putExtra("subject", 1);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_forward, R.anim.slide_out_forward);
    }
    public void selectSingle(View view){
        Intent myIntent = new Intent(MainActivity.this, Category.class);
        myIntent.putExtra("subject", 2);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_forward, R.anim.slide_out_forward);
    }

    public void selectLeaderboard(View v) {
        Intent myIntent = new Intent(MainActivity.this, Category.class);
        myIntent.putExtra("subject", 3);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_forward, R.anim.slide_out_forward);
    }

    public void selectAbout(View view){
        startActivity(new Intent(getApplicationContext(), About.class));
        overridePendingTransition(R.anim.slide_in_forward, R.anim.slide_out_forward);
    }

    public void setDutch(View v){
        editor.putInt("selectedLanguage", 1);
        editor.commit();

        setLanguage(1);
    }

    public void setGerman(View v){
        editor.putInt("selectedLanguage", 2);
        editor.commit();

        setLanguage(2);
    }

    public void setSpanish(View v){
        editor.putInt("selectedLanguage", 3);
        editor.commit();

        setLanguage(3);
    }

    public void setFrench(View v){
        editor.putInt("selectedLanguage", 4);
        editor.commit();

        setLanguage(4);
    }

    public void setEnglish(View v){
        editor.putInt("selectedLanguage", 5);
        editor.commit();

        setLanguage(5);
    }

    private void setLanguage(int language){
        btnOefen.setText(Language.getMenuItemsFromSubject(language, 0));
        btnSpeel.setText(Language.getMenuItemsFromSubject(language, 1));
        btnScores.setText(Language.getMenuItemsFromSubject(language, 2));
        btnOver.setText(Language.getMenuItemsFromSubject(language, 3));
        textTaal.setText(Language.getMenuItemsFromSubject(language, 4));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOefen = (Button) findViewById(R.id.btnOefen);
        btnSpeel = (Button) findViewById(R.id.btnSpeel);
        btnScores = (Button) findViewById(R.id.btnScores);
        btnOver = (Button) findViewById(R.id.btnOver);
        textTaal = (TextView) findViewById(R.id.textTaal);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        setLanguage(prefs.getInt("selectedLanguage", 1));
    }

}
