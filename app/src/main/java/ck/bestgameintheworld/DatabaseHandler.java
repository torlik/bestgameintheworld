package ck.bestgameintheworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Torlik on 2016.04.17..
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questions";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        context.deleteDatabase("questions");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_QUESTIONS = "CREATE TABLE questions" +
                "(ID INTEGER PRIMARY KEY NOT NULL," +
                "Question CHAR(200)," +
                "Answer1 CHAR(200)," +
                "Answer2 CHAR(200)," +
                "Answer3 CHAR(200)," +
                "Answer4 CHAR(200)," +
                "Correct INTEGER);";
        sqLiteDatabase.execSQL(CREATE_QUESTIONS);
        String CREATE_RESULTS = "CREATE TABLE results" +
                "(ID INTEGER PRIMARY KEY NOT NULL," +
                "Name CHAR(200)," +
                "Score INTEGER);";
        sqLiteDatabase.execSQL(CREATE_RESULTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public Cursor getQuestionData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select rowid, * from questions where rowid=" + id + "", null);
        return res;
    }

    public Cursor getResultData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from results order by Score desc", null);
        return res;
    }

    public boolean insertResult(String name, int score){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Score", score);
        db.insert("results", null, contentValues);
        return true;
    }

    public boolean insertQuestion(String Question, String Answer1, String Answer2, String Answer3, String Answer4, int Correct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Question", Question);
        contentValues.put("Answer1", Answer1);
        contentValues.put("Answer2", Answer2);
        contentValues.put("Answer3", Answer3);
        contentValues.put("Answer4", Answer4);
        contentValues.put("Correct", Correct);
        db.insert("questions", null, contentValues);
        return true;
    }

    public int getSize() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) from questions", null);
        res.moveToFirst();
        return res.getInt(0);
    }

    public void resetQuestions(){
        String DROP_QUESTIONS = "DROP TABLE questions";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DROP_QUESTIONS);
        String CREATE_QUESTIONS = "CREATE TABLE questions" +
                "(ID INTEGER PRIMARY KEY NOT NULL," +
                "Question CHAR(200)," +
                "Answer1 CHAR(200)," +
                "Answer2 CHAR(200)," +
                "Answer3 CHAR(200)," +
                "Answer4 CHAR(200)," +
                "Correct INTEGER);";
        db.execSQL(CREATE_QUESTIONS);
        for(int i = 1; i < 21; i++){
            insertQuestion("Dummy question " + i, "Dummy answer1", "Dummy answer2", "Dummy answer3", "Dummy answer4", (i % 4) + 1);
        }
    }

    public void resetResults(){
        String DROP_RESULTS = "DROP TABLE results";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DROP_RESULTS);
        String CREATE_RESULTS = "CREATE TABLE results" +
                "(ID INTEGER PRIMARY KEY NOT NULL," +
                "Name CHAR(200)," +
                "Score INTEGER);";
        db.execSQL(CREATE_RESULTS);
    }
}

