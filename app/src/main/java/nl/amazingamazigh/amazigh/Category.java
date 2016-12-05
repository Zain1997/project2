package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Category extends Activity {

    int subject;
    TextView chooseCategory;
    TextView textViewScore;
    Button btnDieren1;
    Button btnFruit;
    Button btnInsecten;
    Button btnGroente;
    Button btnDieren2;
    Button btnEten;
    Button btnKleding;
    Button btnKleuren;
    Button btnWeer;
    Button amazighExpert;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        chooseCategory = (TextView) findViewById(R.id.chooseCategory);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        btnDieren1 = (Button) findViewById(R.id.c1);
        btnFruit = (Button) findViewById(R.id.c2);
        btnInsecten = (Button) findViewById(R.id.c3);
        btnGroente = (Button) findViewById(R.id.c4);
        btnDieren2 = (Button) findViewById(R.id.c5);
        btnEten = (Button) findViewById(R.id.c6);
        btnKleding = (Button) findViewById(R.id.c7);
        btnKleuren = (Button) findViewById(R.id.c8);
        btnWeer = (Button) findViewById(R.id.c9);
        amazighExpert = (Button)findViewById(R.id.c10);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle extras = getIntent().getExtras();
        subject = extras.getInt("subject", 2);
        setLanguage(prefs.getInt("selectedLanguage", 1));

        textViewScore.setText(Language.getMenuItemsFromSubject(prefs.getInt("selectedLanguage", 1), subject - 1) + " Amazigh");

        //als het onderwerp de quiz is, bekijk welke categorieen er al zijn behaald
        //zet deze open inclusief de daaropvolgende categorie
        switch(subject){
            case 1: //bij oefenen, zet Amazigh expert uit
                amazighExpert.setVisibility(View.GONE);
                break;
            case 3: // bij scores hoeft er niets extras te gebeuren
                break;
            default:
                int unlockedCategories = prefs.getInt("unlockedCategories", 1);
                for (int i = 1; i <= 10; i++){
                    if(unlockedCategories < i) {
                        String buttonID = "c" + i;
                        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                        b = ((Button) findViewById(resID));
                        b.setClickable(false);
                        b.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable._general_ic_lock_black_24dp, 0, 0);
                        b.setPadding(0, 10, 0,0);
                        //b.setText("XX" + b.getText() + "XX");
                    }
                }
                break;
        }
    }

    private void setLanguage(int language) {
        chooseCategory.setText(Language.getCategoryFromLanguageAndInt(language, 0));
        btnDieren1.setText(Language.getCategoryFromLanguageAndInt(language, 1));
        btnFruit.setText(Language.getCategoryFromLanguageAndInt(language, 2));
        btnInsecten.setText(Language.getCategoryFromLanguageAndInt(language, 3));
        btnGroente.setText(Language.getCategoryFromLanguageAndInt(language, 4));
        btnDieren2.setText(Language.getCategoryFromLanguageAndInt(language, 5));
        btnEten.setText(Language.getCategoryFromLanguageAndInt(language, 6));
        btnKleding.setText(Language.getCategoryFromLanguageAndInt(language, 7));
        btnKleuren.setText(Language.getCategoryFromLanguageAndInt(language, 8));
        btnWeer.setText(Language.getCategoryFromLanguageAndInt(language, 9));
        amazighExpert.setText(Language.getCategoryFromLanguageAndInt(language, 10));
    }

    public void toSubject(View v) {
        Button buttonk = (Button) v;

        Intent myIntent;

        switch(subject) {
            case 2:
                myIntent = new Intent(Category.this, QuizScreen.class);
                break;
            case 3:
                myIntent = new Intent(Category.this, LeaderboardScreen.class);
                break;
            default:
                myIntent = new Intent(Category.this, Practice.class);
                break;
        }
        myIntent.putExtra("category", Integer.parseInt(buttonk.getTag().toString()));

        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_forward, R.anim.slide_out_forward);
    }
}
