package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Practice extends Activity {

    private dbHandler db = new dbHandler(this);
    private List<Question> answerList = new ArrayList<>();
    private List<Integer> blacklist = new ArrayList<>();
    private SharedPreferences prefs;
    private GestureDetector gestureDetector;
    private int questionID;
    private ImageView imageFront;
    private ImageView imageBack;
    private View cardFront;
    private View cardBack;
    private FrameLayout rootQuizLayout;
    private TextView nameOfThingFront;
    private TextView nameOfThingBack;
    private TextView textCurrentMaxQuestions;
    private Button ButtonSoundFront;
    private Button ButtonSoundBack;
    private MediaPlayer mp;
    private int categoryID;
    private String category;
    private boolean firstPass = true;

    private enum Categories {
        dummy, dieren01_, fruit_, insecten_, groente_, dieren02_, eten_, kleding_, kleuren_, weer_
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        // initialize properties
        Bundle extras = getIntent().getExtras();
        questionID = 0;
        categoryID = extras.getInt("category", 0);
        category = Categories.values()[categoryID].name().toString();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        nameOfThingFront = (TextView)findViewById(R.id.nameOfThingFront);
        nameOfThingBack = (TextView)findViewById(R.id.nameOfThingBack);
        textCurrentMaxQuestions = (TextView)findViewById(R.id.textCurrentMaxQuestions);
        imageFront = (ImageView) findViewById(R.id.iViewFront);
        imageBack = (ImageView) findViewById(R.id.iViewBack);
        rootQuizLayout = (FrameLayout) findViewById(R.id.rootQuizLayout);
        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);
        ButtonSoundFront = (Button) findViewById(R.id.ButtonSoundFront);
        ButtonSoundBack = (Button) findViewById(R.id.ButtonSoundBack);

        gestureDetector = new GestureDetector(
                new SwipeGestureDetector());

        // set answers in list
        answerList = db.getQuestion(categoryID, 0, blacklist);
//        for(int i = 0; i < answerList.size(); i++){
//            aList.add(answerList.get(i));
//        }

        // set sound, image and text
        setSoundImageAndText(imageFront, nameOfThingFront, ButtonSoundFront);
    }

    public void setSoundImageAndText(ImageView imageView, TextView nameView, Button soundView){

        try {
            //set Text
            textCurrentMaxQuestions.setText((questionID + 1) + "/" + answerList.size());
            nameView.setText(getTranslatedAnswer(answerList.get(questionID).answer));
            soundView.setText(answerList.get(questionID).answer.getAmazigh());

            // set Image
            int imageID = getResources().getIdentifier(category + answerList.get(questionID).answer.getDutch(), "drawable", getPackageName());
            imageView.setBackgroundResource(imageID);

            // set Sound
            mp = MediaPlayer.create(this, Uri.parse("android.resource://nl.amazingamazigh.amazigh/raw/" + category + answerList.get(questionID).answer.getDutch()));
            if(firstPass){
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        firstPass = false;
                    }
                });
            }
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("U wordt naar het menu gestuurd")
                    .setMessage("Geluidsfragment " + category + answerList.get(questionID).answer.getDutch() + " is niet gevonden, laat het de beheerders weten")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.slide_in_backward, R.anim.slide_out_backward);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public String getTranslatedAnswer(Translation answer){
        switch(Language.getLanguageFromInt(prefs.getInt("selectedLanguage", 1))){
            case german:
                return answer.getGerman();
            case spanish:
                return answer.getSpanish();
            case french:
                return answer.getFrench();
            case english:
                return answer.getEnglish();
            default:
                return answer.getDutch();
        }
    }

    public void playSound(View v) {
        mp.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onLeftSwipe() {
        questionID += 1;
        if(answerList.size() <= questionID) {
            questionID -= 1;
            return;
        } else {
            mp.release();
            if(questionID % 2 == 0) {
                setSoundImageAndText(imageFront, nameOfThingFront, ButtonSoundFront);
                flipCard(cardBack, cardFront, true);
            } else {
                setSoundImageAndText(imageBack, nameOfThingBack, ButtonSoundBack);
                flipCard(cardFront, cardBack, true);
            }
        }
    }

    private void onRightSwipe() {
        questionID -= 1;
        if(questionID < 0) {
            questionID += 1;
            return;
        } else {
            mp.release();
            if(questionID % 2 == 0) {
                setSoundImageAndText(imageFront, nameOfThingFront, ButtonSoundFront);
                flipCard(cardFront, cardBack, false);
            } else {
                setSoundImageAndText(imageBack, nameOfThingBack, ButtonSoundBack);
                flipCard(cardBack, cardFront, false);
            }
        }
    }

    private void flipCard(View from, View to, boolean forward)
    {
        boolean isQuiz = false;
        FlipAnimation flipAnimation = new FlipAnimation(from, to, isQuiz);

        if (!forward)
        {
            flipAnimation.reverse();
        }
        rootQuizLayout.startAnimation(flipAnimation);
        flipAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                mp.start();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
    }

    // Private class for gestures
    private class SwipeGestureDetector
            extends GestureDetector.SimpleOnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 50;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 50;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            try {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Practice.this.onLeftSwipe();

                    // Right swipe
                } else if (-diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Practice.this.onRightSwipe();
                }
            } catch (Exception e) {
                Log.e("YourActivity", "Error on gestures");
            }
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
