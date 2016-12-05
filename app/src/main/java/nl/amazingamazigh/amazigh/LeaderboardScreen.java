package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardScreen extends Activity {

    LayoutInflater layoutInflater;
    dbHandler db = new dbHandler(this);
    Map<Integer, String> categories = new HashMap<>();
    Button nameOfThing;
    SharedPreferences prefs;

    int category;
    int categoryTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nameOfThing = (Button)findViewById(R.id.nameOfThing);

        Bundle extras = getIntent().getExtras();
        category = extras.getInt("category", 1);

        if(category == 100)
            categoryTranslation = 10;
        else
            categoryTranslation = category;

        nameOfThing.setText(Language.getCategoryFromLanguageAndInt(prefs.getInt("selectedLanguage", 1), categoryTranslation));

        showScores();
    }

    public void showScores(){
        List<Leaderboard> lbList = db.getLeaderboard(category);

        if(!lbList.isEmpty()){
            for(int i = 0; i < lbList.size(); i++) {

                addRow(lbList.get(i), i);

            }
        }
    }

    public void addRow(Leaderboard record, int position) {

        View newRow =
                layoutInflater.inflate(R.layout.scoreboard_row, null);

        TextView rankView = (TextView)newRow.findViewById(R.id.newRankText);
        TextView nameView = (TextView)newRow.findViewById(R.id.newNameText);
        TextView scoreView = (TextView)newRow.findViewById(R.id.newScoreText);

        TableLayout leaderboardTable = (TableLayout)findViewById(R.id.tableLeaderboard);

        rankView.setText(String.valueOf(position + 1));
        nameView.setText(record.getName());
        scoreView.setText(String.valueOf(record.getScore()));

        leaderboardTable.addView(newRow);
    }
}
