package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoreScreen extends Activity {

    dbHandler db = new dbHandler(this);
    SharedPreferences prefs;

    boolean isSaved = false;

    TextView headingScore;
    TextView textScore;
    EditText textName;
    TextView textSaved;
    Button buttonSave;
    TextView headingUnlocks;
    TextView textUnlocks;

    int category;
    int categoryTranslation;
    int language;
    int score = -1;
    final int MINIMUM_SCORE_UNLOCK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        headingScore = (TextView) findViewById(R.id.headingScore);
        textScore = (TextView) findViewById(R.id.textScore);
        textName = (EditText) findViewById(R.id.textName);
        textSaved = (TextView) findViewById(R.id.textSaved);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        headingUnlocks = (TextView) findViewById(R.id.headingUnlocks);
        textUnlocks = (TextView) findViewById(R.id.textUnlocks);

        Bundle extras = getIntent().getExtras();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        language = prefs.getInt("selectedLanguage", 1);
        SharedPreferences.Editor editor = prefs.edit();

        textName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveScore(v);
                    return true;
                }
                return false;
            }
        });

        if(extras != null) {
            score = extras.getInt("score", 1);
            category = extras.getInt("category", 1);
        } else {
            score = 1;
            category = 1;
        }

        if(category == 100)
            categoryTranslation = 10;
        else
            categoryTranslation = category;

        setTranslations();
        textScore.setText(score + "");

        //unlock next category by getting a high enough score in the previous category
        //alter score to alter difficulty
        if(score > MINIMUM_SCORE_UNLOCK && category == prefs.getInt("unlockedCategories", 1)) {
            int yow = prefs.getInt("unlockedCategories", 1) + 1;
            editor.putInt("unlockedCategories", yow);
            editor.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(!isSaved) {
            new AlertDialog.Builder(this)
                    .setMessage(getDialogMessageTranslation())
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.slide_in_backward, R.anim.slide_out_backward);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slide_in_backward, R.anim.slide_out_backward);
        }
    }

    public String getDialogMessageTranslation(){
        switch (Language.getLanguageFromInt(language)) {
            case german:
                return "Fortfahren ohne zu speichern?";
            case spanish:
                return "Continuar sin guardar?";
            case french:
                return "Continuer sans sauvegarder?";
            case english:
                return "Continue without saving?";
            default:
                return "Doorgaan zonder op te slaan?";
        }
    }

    public void saveScore(View v) {
        if(textName.getText().length() == 0)
            return;
        isSaved = true;

        Leaderboard newScore = new Leaderboard();
        newScore.setName(textName.getText().toString());
        newScore.setScore(score);
        newScore.setCategory(category);

        textName.setVisibility(View.GONE);
        textName.setEnabled(false);
        buttonSave.setVisibility(View.GONE);
        buttonSave.setEnabled(false);

        textSaved.setVisibility(View.VISIBLE);

        db.addScore(newScore);
    }

    public void setTranslations(){
        headingScore.setText("Score " + Language.getCategoryFromLanguageAndInt(language, categoryTranslation));

        switch (Language.getLanguageFromInt(language)) {
            case german:
                textName.setHint("Ihren Namen");
                textSaved.setText("Ihr Ergebnis ist erfolgreich gespeichert! Sie können Ihre Gäste auf der Rangliste an!");
                headingUnlocks.setText("Entsperrten");
                if(category == 100 || prefs.getInt("unlockedCategories", 1) == 10)
                    textUnlocks.setText("Sie haben alle Kategorien bereits freigeschaltet .");
                else if(prefs.getInt("unlockedCategories", 1) > category)
                    textUnlocks.setText("Sie eröffnet bereits die Kategorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + ".");
                else if(score > MINIMUM_SCORE_UNLOCK)
                    textUnlocks.setText("Sie haben die Kategorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + " freigeschaltet! Viel Glück auf Ihrer Reise!");
                else
                    textUnlocks.setText("Sie benötigen einen Score über "+ MINIMUM_SCORE_UNLOCK + " die Kategorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + " zu entsperren.");
                break;
            case spanish:
                textName.setHint("Su nombre");
                textSaved.setText("Su puntuación se guarda correctamente ! Puede ver su puntuación en la clasificación!");
                headingUnlocks.setText("Desbloqueado");
                if(category == 100 || prefs.getInt("unlockedCategories", 1) == 10)
                    textUnlocks.setText("Usted ha desbloqueado ya todas las categorías.");
                else if(prefs.getInt("unlockedCategories", 1) > category)
                    textUnlocks.setText("Ya abrió la categoría " + Language.getCategoryFromLanguageAndInt(language, category + 1) + ".");
                else if(score > MINIMUM_SCORE_UNLOCK)
                    textUnlocks.setText("Que haya desbloqueado la categoría " + Language.getCategoryFromLanguageAndInt(language, category + 1) + "! Buena suerte en tu viaje!");
                else
                    textUnlocks.setText("Se necesita una puntuación por encima de " + MINIMUM_SCORE_UNLOCK + " para desbloquear la categoría " + Language.getCategoryFromLanguageAndInt(language, category + 1) + ".");
                break;
            case french:
                textName.setHint("Votre nom");
                textSaved.setText("Votre scre est enregistré avec succès! Vous pouvez afficher votre score sur le classement!");
                headingUnlocks.setText("Déverrouillée");
                if(category == 100 || prefs.getInt("unlockedCategories", 1) == 10)
                    textUnlocks.setText("Vous avez toutes les catégories déjà déverrouillé.");
                else if(prefs.getInt("unlockedCategories", 1) > category)
                    textUnlocks.setText("Vous avez déjà débloqué la catégorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + ".");
                else if(score > MINIMUM_SCORE_UNLOCK)
                    textUnlocks.setText("Vous avez débloqué la catégorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + "! Bonne chance pour ton voyage!");
                else
                    textUnlocks.setText("Vous avez besoin d' un score supérieur à " + MINIMUM_SCORE_UNLOCK + " pour déverrouiller la catégorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + ".");
                break;
            case english:
                textName.setHint("Your name");
                textSaved.setText("Your score is successfully saved! You can view your score on the leaderboard!");
                headingUnlocks.setText("Unlocked");
                if(category == 100 || prefs.getInt("unlockedCategories", 1) == 10)
                    textUnlocks.setText("You already unlocked all categories.");
                else if(prefs.getInt("unlockedCategories", 1) > category)
                    textUnlocks.setText("You already unlocked the category " + Language.getCategoryFromLanguageAndInt(language, category + 1) + ".");
                else if(score > MINIMUM_SCORE_UNLOCK)
                    textUnlocks.setText("You have unlocked the category " + Language.getCategoryFromLanguageAndInt(language, category + 1) + "! Good luck on your journey!");
                else
                    textUnlocks.setText("You need a score above " + MINIMUM_SCORE_UNLOCK + " to unlock the category " + Language.getCategoryFromLanguageAndInt(language, category + 1) + ".");
                break;
            default:
                textName.setHint("Jouw naam");
                textSaved.setText("Je score is opgeslagen! Je kan je score zien op het scorebord!");
                headingUnlocks.setText("Vrijgespeeld");
                if(category == 100 || prefs.getInt("unlockedCategories", 1) == 10)
                    textUnlocks.setText("Je hebt alle categorieën al vrijgespeeld.");
                else if(prefs.getInt("unlockedCategories", 1) > category)
                    textUnlocks.setText("Je hebt de categorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + " al vrijgespeeld.");
                else if(score > MINIMUM_SCORE_UNLOCK)
                    textUnlocks.setText("Je hebt de categorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + " vrijgespeeld! Veel succes met je avontuur!");
                else
                    textUnlocks.setText("Je hebt een score hoger dan " + MINIMUM_SCORE_UNLOCK + " nodig om de categorie " + Language.getCategoryFromLanguageAndInt(language, category + 1) + " vrij te spelen.");
                break;
        }
    }

}
