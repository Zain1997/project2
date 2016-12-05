package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuizScreen extends Activity {

    //region VARS
    dbHandler db = new dbHandler(this);
    MediaPlayer qSound = new MediaPlayer();
    MediaPlayer wrongSound = new MediaPlayer();
    MediaPlayer correctSound = new MediaPlayer();
    MediaPlayer wooshSound = new MediaPlayer();
    MediaPlayer wooshSound_2 = new MediaPlayer();
    MediaPlayer allWrongSound = new MediaPlayer();

    TextView textViewScore;
    TextView textAddScore;
    TextView nameOfThing;
    TextView nameOfThingFront;
    TextView textQuestionsRemaining;
    ImageView iViewAnswer;
    View rootQuizLayout;
    View answerSheet;
    View qLayout;
    Button buttonSoundFront;
    Button buttonSoundBack;
    BitmapDrawable imageWrong;

    String CORRECT_ANSWER;
    SharedPreferences prefs;


    int Q_MAX;
    int Q_CURRENT = 0;
    int A_MAX = 6;
    int SCORE = 0;
    int answersGiven = 0;
    int maxWrongAnswers = 2;
    int category;
    int imageSizeX;
    int imageSizeY;
    int bigImageSizeX;
    int bigImageSizeY;
    int delayBetweenAnimations = 205;

    boolean amazighExpert = false;
    boolean wrongAnswerGiven = false;

    Animation no_shake;
    Animation fade_out;
    Animation left_to_right;
    Animation right_to_left;
    Animation center_to_bottom;
    Animation top_to_center;
    Animation bottom_to_center;

    ArrayList<Integer> categoryBlacklist = new ArrayList<>();
    ArrayList<Integer> blacklist = new ArrayList<>();

    Map<Integer, String> categories = new HashMap<>();
    //endregion

    public void startQuiz() {
        if(Q_CURRENT < Q_MAX) {
            Q_CURRENT += 1;
            answersGiven = 0;

            //quiz starts after this animation
            if(Q_CURRENT == 1){
                rootQuizLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showQuestion();
                    }
                }, 300);

            }
            else {
                answerSheet.startAnimation(center_to_bottom);
                qSound.release();
            }

        } else {
            toScore();
        }
    }

    public void setCategories() {
        categories.put(1, "dieren01_");
        categories.put(2, "fruit_");
        categories.put(3, "insecten_");
        categories.put(4, "groente_");
        categories.put(5, "dieren02_");
        categories.put(6, "eten_");
        categories.put(7, "kleding_");
        categories.put(8, "kleuren_");
        categories.put(9, "weer_");
    }

    private void flipCard(View from, View to)
    {
        FlipAnimation flipAnimation = new FlipAnimation(from, to);

//        if (cardFace.getVisibility() == View.GONE)
//        {
//            flipAnimation.reverse();
//        }
        rootQuizLayout.startAnimation(flipAnimation);
        flipAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                //with expert mode, one wrong answer will be the end of the quiz
                if (amazighExpert && wrongAnswerGiven) {
                    category = 100;
                    toScore();
                } else {
                    startQuiz();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
    }

    public void toScore() {
        if(amazighExpert)
            category = 100;

        Intent myIntent = new Intent(QuizScreen.this, ScoreScreen.class);
        myIntent.putExtra("score", SCORE);
        myIntent.putExtra("category", category);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_forward, R.anim.slide_out_forward);
        finish();
    }

    public void enableButtons(boolean enable) {
        Button b;
        ImageView iV;
        for(int i = 0; i < A_MAX; i++) {
            String buttonID = "a" + i;
            String imageID = "w" + i;

            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            b = ((Button) findViewById(resID));
            b.setClickable(enable);

            //if buttons are clickable again, clear text
            if(enable) {
                resID = getResources().getIdentifier(imageID, "id", getPackageName());
                iV = ((ImageView) findViewById(resID));
                iV.setBackgroundDrawable(imageWrong);
                iV.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                buttonSoundFront.setVisibility(View.INVISIBLE);
                nameOfThingFront.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void checkAnswer(View v) {
        Button buttonk = (Button) v;

        if(buttonk.getTag().toString() == CORRECT_ANSWER)
            correctAnswer(buttonk);
        else
            wrongAnswer(buttonk);
    }

    public void correctAnswer(Button buttonk) {
        SCORE += 10;
        //soundButton.setBackgroundColor(Color.GREEN);
        enableButtons(false);
        textViewScore.setText(Integer.toString(SCORE));
        flipCard(qLayout, answerSheet);

        correctSound.start();

        textAddScore.setTextColor(Color.GREEN);
        textAddScore.setText("+10");
        textAddScore.startAnimation(fade_out);
    }

    public void wrongAnswer(Button buttonk) {
        SCORE -= 5;
        String iViewId = buttonk.getResources().getResourceEntryName(buttonk.getId()).replace('a', 'w');
        int resID = getResources().getIdentifier(iViewId, "id", getPackageName());
        final ImageView Iview = ((ImageView) findViewById(resID));

        no_shake.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                Iview.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                wrongSound.start();
            }
        });

        textAddScore.setTextColor(Color.RED);
        textAddScore.setText("-5");
        textAddScore.startAnimation(fade_out);

        wrongAnswerGiven = true;

        buttonk.startAnimation(no_shake);
        buttonk.setClickable(false);
        answersGiven+=1;
        if(answersGiven>maxWrongAnswers)

        {
            enableButtons(false);
            //soundButton.setBackgroundColor(Color.RED);
            flipCard(qLayout, answerSheet);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    allWrongSound.start();
                }
            }, 450);
        }

        textViewScore.setText(Integer.toString(SCORE));
    }

    public void showQuestion() {
        Button b;

        int delay = 0;
        Random rng = new Random();
        ArrayList<Integer> currentAnswer = new ArrayList<>();

        qLayout.setVisibility(View.VISIBLE);
        answerSheet.setVisibility(View.INVISIBLE);

        //if the expert category is chosen, get a random category
        if(amazighExpert) {
            do {
                category = rng.nextInt(9) + 1;
            }
            while(categoryBlacklist.contains(category));
        }

        //pick answer from db
        List<Question> q = db.getQuestion(category, 1, blacklist);
        if(q.size() == 0) {
            categoryBlacklist.add(category);
            showQuestion();
            return;
        } else {
            blacklist.add(q.get(0).id);
            CORRECT_ANSWER = q.get(0).answer.getDutch();
            buttonSoundFront.setText(q.get(0).answer.getAmazigh());
            buttonSoundBack.setText(q.get(0).answer.getAmazigh());
        }
        //save answer in correct language
        final String finalAnswer = getTranslation(q.get(0).answer);

        //set the large image
        int bigImageID = getResources().getIdentifier(categories.get(q.get(0).getCategory()) + CORRECT_ANSWER, "drawable", getPackageName());
        final BitmapDrawable bigImage = new BitmapDrawable(getResources(), decodeSampledBitmapFromResource(getResources(), bigImageID, bigImageSizeX, bigImageSizeY));

        //set the sound
        try {
            qSound = MediaPlayer.create(this, Uri.parse("android.resource://nl.amazingamazigh.amazigh/raw/" + categories.get(q.get(0).getCategory()) + CORRECT_ANSWER));
        }  catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error, you are send to the menu")
                    .setMessage("Soundfragment " + categories.get(category) + CORRECT_ANSWER + " is not found, please let the administrators know")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.slide_in_backward, R.anim.slide_out_backward);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        //set current question / max question in textview
        textQuestionsRemaining.setText(Q_CURRENT + "/" + Q_MAX);



        //add answer to currentAnswer to avoid double answers
        currentAnswer.add(q.get(0).id);

        //add random answers from db
        q.addAll(db.getQuestion(category, 5, currentAnswer));


        enableButtons(true);

        for (int i = 0; i < A_MAX; i++) {
            int index = rng.nextInt(q.size());
            if(i % 2 == 0)
                delay = delayBetweenAnimations * (i + 2);

            String buttonID = "a" + (i);

            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            b = ((Button) findViewById(resID));

            final Button c = b;
            final int y = i;

            b.setClickable(true);

            int imageID = getResources().getIdentifier(categories.get(q.get(index).getCategory()) + q.get(index).answer.getDutch(), "drawable", getPackageName());
            BitmapDrawable ob = new BitmapDrawable(getResources(), decodeSampledBitmapFromResource(getResources(), imageID, imageSizeX, imageSizeY));
            b.setBackgroundDrawable(ob);

            b.setTag(q.get(index).answer.getDutch());

            c.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (y % 2 == 0)
                        c.startAnimation(left_to_right);
                    else
                        c.startAnimation(right_to_left);
                    wooshSound.start();
                    c.setVisibility(View.VISIBLE);
                }
            }, delay);
            q.remove(index);
        }

        nameOfThingFront.startAnimation(top_to_center);
        nameOfThingFront.setVisibility(View.VISIBLE);
        wooshSound.start();

        buttonSoundFront.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonSoundFront.startAnimation(bottom_to_center);
                buttonSoundFront.setVisibility(View.VISIBLE);
                wooshSound.start();
                iViewAnswer.setBackgroundDrawable(bigImage);
                //set answer in dutch
                nameOfThing.setText(finalAnswer);
            }
        }, delay + (delayBetweenAnimations * 2));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                qSound.start();
            }
        }, delay + (delayBetweenAnimations * 4));

    }

    private String getTranslation(Translation answer){
        switch (Language.getLanguageFromInt(prefs.getInt("selectedLanguage", 1))) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);
        buttonSoundFront =(Button)findViewById(R.id.ButtonSoundFront);
        buttonSoundBack = (Button) findViewById(R.id.ButtonSoundBack);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        textViewScore = (TextView)findViewById(R.id.textViewScore);
        iViewAnswer = (ImageView)findViewById(R.id.iViewAnswer);
        rootQuizLayout = findViewById(R.id.rootQuizLayout);
        qLayout = findViewById(R.id.cardFront);
        nameOfThing = (TextView)findViewById(R.id.nameOfThingBack);
        nameOfThingFront = (Button) findViewById(R.id.nameOfThingFront);
        answerSheet = findViewById(R.id.cardBack);
        textQuestionsRemaining = (TextView)findViewById(R.id.textQuestionsRemaining);
        textAddScore = (TextView)findViewById(R.id.textAddScore);

        no_shake = AnimationUtils.loadAnimation(this, R.anim.no_shake);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        left_to_right = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        right_to_left = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        center_to_bottom =  AnimationUtils.loadAnimation(this, R.anim.center_to_bottom);
        top_to_center = AnimationUtils.loadAnimation(this, R.anim.top_to_center);
        bottom_to_center = AnimationUtils.loadAnimation(this, R.anim.bottom_to_center);

        fade_out.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) { textAddScore.setVisibility(View.INVISIBLE); }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) { textAddScore.setVisibility(View.VISIBLE); }
        });

        center_to_bottom.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                answerSheet.setVisibility(View.INVISIBLE);
//                btnplay.setBackgroundColor(Color.parseColor("#035F84"));
                showQuestion();
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) { }
        });

        //set the category
        Bundle extras = getIntent().getExtras();
        category = extras.getInt("category", 1);

        //assign sounds
        correctSound = MediaPlayer.create(this, R.raw._general_happy_kids);
        wrongSound = MediaPlayer.create(this, R.raw._general_wrong_buzzer);
        allWrongSound = MediaPlayer.create(this, R.raw._general_all_wrong);
        wooshSound = MediaPlayer.create(this, R.raw._general_woosh);
        wooshSound_2 = MediaPlayer.create(this, R.raw._general_woosh_2);

        //set balanced volume
        allWrongSound.setVolume(0.5f, 0.5f);
        wooshSound.setVolume(0.2f, 0.2f);
        wooshSound_2.setVolume(0.2f, 0.2f);
        correctSound.setVolume(0.7f, 0.7f);


        //get best sized image for user display
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        imageSizeX = (int)Math.round(size.x * 0.35);
        imageSizeY = (int)Math.round(size.y * 0.20);
        bigImageSizeX = (int)Math.round(size.x * 0.85);
        bigImageSizeY = (int)Math.round(size.y * 0.45);

        imageWrong = new BitmapDrawable(getResources(), decodeSampledBitmapFromResource(getResources(), R.drawable.wrongu, imageSizeX, imageSizeY));

        //set the categories
        setCategories();


        //check for Amazigh Expert category (= 100)
        if(category == 100)
            amazighExpert = true;

        //get question count
        if(!amazighExpert)
            Q_MAX = db.getCategoryCount(category);
        else
            Q_MAX = 100;

        //set maxWrongAnswers to 0 for expert mode
        if(amazighExpert)
            maxWrongAnswers = 0;


        buttonSoundFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qSound.start();
            }
        });

        startQuiz();
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
