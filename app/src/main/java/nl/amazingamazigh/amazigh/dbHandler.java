package nl.amazingamazigh.amazigh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DannyW on 2-6-2015.
 */
public class dbHandler extends SQLiteOpenHelper {

    private static dbHandler db;

    // Naamgeving van de database en tabel middels constanten
    private static final int DATABASE_VERSION = 11;
    private static final String DATABASE_NAME = "amazigh.db"; // Let op, gebruik .db als extensie
    private static final String QUESTION_TABLE = "question";
    private static final String LEADERBOARD_TABLE = "leaderboard";

    private static final String QUESTION_ID = "id";
    private static final String QUESTION_CATEGORY = "category";
    private static final String QUESTION_ANSWER = "answer"; // is in dutch
    private static final String QUESTION_ENGLISH = "english";
    private static final String QUESTION_SPANISH = "spanish";
    private static final String QUESTION_FRENCH = "french";
    private static final String QUESTION_GERMAN = "german";
    private static final String QUESTION_AMAZIGH = "amazigh";

    private static final String LEADERBOARD_ID = "id";
    private static final String LEADERBOARD_NAME = "name";
    private static final String LEADERBOARD_SCORE = "score";
    private static final String LEADERBOARD_CATEGORY = "category";


    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + QUESTION_TABLE +
                "(" +
                QUESTION_ID + " INTEGER PRIMARY KEY," +
                QUESTION_CATEGORY + " INTEGER," +
                QUESTION_ANSWER + " TEXT" + " NOT NULL UNIQUE," +
                QUESTION_ENGLISH + " TEXT" + " NOT NULL," +
                QUESTION_SPANISH + " TEXT" + " NOT NULL," +
                QUESTION_FRENCH + " TEXT" + " NOT NULL," +
                QUESTION_GERMAN + " TEXT" + " NOT NULL," +
                QUESTION_AMAZIGH + " TEXT" + " NOT NULL)";

        String CREATE_LEADERBOARD_TABLE = "CREATE TABLE " + LEADERBOARD_TABLE +
                "(" +
                LEADERBOARD_ID + " INTEGER PRIMARY KEY," +
                LEADERBOARD_NAME + " TEXT," +
                LEADERBOARD_SCORE + " INTEGER, " +
                LEADERBOARD_CATEGORY + " INTEGER)";

        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_LEADERBOARD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LEADERBOARD_TABLE);
        onCreate(db);
    }

    public void addScore(Leaderboard record)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        synchronized (db) {
            ContentValues values = new ContentValues();
            values.put(LEADERBOARD_NAME, record.getName());
            values.put(LEADERBOARD_SCORE, record.getScore());
            values.put(LEADERBOARD_CATEGORY, record.getCategory());

            db.insert(LEADERBOARD_TABLE, null, values);
            db.close();
        }
    }

    public int getCategoryCount(int category) {
        String selectQuery = "SELECT * FROM " + QUESTION_TABLE +" WHERE " + QUESTION_CATEGORY + " = " + category;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount;
    }

    public void addRecord(int category, Translation record)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        synchronized (db) {
            ContentValues values = new ContentValues();
            values.put(QUESTION_CATEGORY, category);
            values.put(QUESTION_ANSWER, record.getDutch());
            values.put(QUESTION_ENGLISH, record.getEnglish());
            values.put(QUESTION_SPANISH, record.getSpanish());
            values.put(QUESTION_FRENCH, record.getFrench());
            values.put(QUESTION_GERMAN, record.getGerman());
            values.put(QUESTION_AMAZIGH, record.getAmazigh());

            db.insert(QUESTION_TABLE, null, values);
            db.close();
        }
    }

    public List<Leaderboard> getLeaderboard(int category) {
        String selectQuery = "SELECT * FROM " + LEADERBOARD_TABLE +
                " WHERE category = " + category +
                " ORDER BY " + LEADERBOARD_SCORE + " DESC;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<Leaderboard> lbList = new ArrayList<>();

//       DatabaseUtils dbDebug = new DatabaseUtils();
//       String debug = dbDebug.dumpCursorToString(cursor);
        if (cursor.moveToFirst()) {
            do {
                Leaderboard lb = new Leaderboard();
                lb.setId(Integer.parseInt(cursor.getString(0)));
                lb.setName((cursor.getString(1)));
                lb.setScore(Integer.parseInt(cursor.getString(2)));
                lbList.add(lb);
            } while(cursor.moveToNext());
        }
        return lbList;
    }

    public List<Question> getQuestion(int category, int answers, List<Integer> blacklist)
    {
        List<Question> qList = new ArrayList();
        String selectQuery = "SELECT * FROM " + QUESTION_TABLE +
                " WHERE "  + QUESTION_CATEGORY + " = " + category;

        if(blacklist.size() > 0) {
            selectQuery += " AND " + QUESTION_ID + " NOT IN (";
            for (int i = 0; i < blacklist.size(); i++) {
                selectQuery += blacklist.get(i);
                if(blacklist.size() - i != 1) {
                    selectQuery += ",";
                }
            }
            selectQuery += ")";
        }
        if(answers != 0) {
            selectQuery += " ORDER BY RANDOM()";

            selectQuery += " LIMIT " + answers;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//       DatabaseUtils dbDebug = new DatabaseUtils();
//       String debug = dbDebug.dumpCursorToString(cursor);
        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                Translation t = new Translation(cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                q.setId(Integer.parseInt(cursor.getString(0)));
                q.setCategory(Integer.parseInt(cursor.getString(1)));
                q.setAnswer(t);
                qList.add(q);
            } while(cursor.moveToNext());
        }
        return qList;
    }
}
