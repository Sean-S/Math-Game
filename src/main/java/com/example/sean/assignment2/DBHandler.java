package com.example.sean.assignment2;

/**
 * Created by sean on 22-Nov-16.
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mathsQuestions";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    // tasks Table Columns names
    private static final String KEY_ID = "qid";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer"; // correct option
    private static final String KEY_OPTA = "opta"; // option a
    private static final String KEY_OPTB = "optb"; // option b
    private static final String KEY_OPTC = "optc"; // option c
    private static final String KEY_OPTD = "optd"; // option d

    private SQLiteDatabase dbase;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_QUESTION + " TEXT, "
                + KEY_ANSWER + " TEXT, "
                + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, "
                + KEY_OPTC + " TEXT, "
                + KEY_OPTD + " TEXT)";
        db.execSQL(sql);
        addQuestion();
    }

    //Questions
    private void addQuestion() {
        Question q1 = new Question("1 + 1 = ?", "0", "1", "2", "2");
        this.addQuestion(q1);
        Question q2 = new Question("2 + 2 = ?", "2", "4", "5", "4");
        this.addQuestion(q2);
        Question q3 = new Question("5 + 2 = ?", "6", "7", "8", "7");
        this.addQuestion(q3);
        Question q4 = new Question("10 - 5 = ?", "5", "4", "7", "5");
        this.addQuestion(q4);
        Question q5 = new Question("17 - 9 = ?", "10", "8", "6", "8");
        this.addQuestion(q5);
        Question q6 = new Question("25 - 13 = ?", "12", "15", "10", "12");
        this.addQuestion(q6);
        Question q7 = new Question("78 + 16 = ?", "82", "94", "86", "94");
        this.addQuestion(q7);
        Question q8 = new Question("56 + 27 = ?", "78", "80", "83", "83");
        this.addQuestion(q8);
        Question q9 = new Question("127 + 67 = ?", "194", "201", "185", "194");
        this.addQuestion(q9);
        Question q10 = new Question("5 x 4 = ?", "16", "20", "24", "20");
        this.addQuestion(q10);
        Question q11 = new Question("15 ÷ 3 = ?", "7", "6", "5", "5");
        this.addQuestion(q11);
        Question q12 = new Question("9 x 4 = ?", "36", "35", "34", "36");
        this.addQuestion(q12);
        Question q13 = new Question("33 ÷ 11 = ?", "7", "3", "5", "3");
        this.addQuestion(q13);
        Question q14 = new Question("12 x 8 = ?", "96", "108", "104", "96");
        this.addQuestion(q14);
        Question q15 = new Question("72 ÷ 8 = ?", "8", "9", "7", "9");
        this.addQuestion(q15);
        Question q16 = new Question("19 x 16 = ?", "304", "279", "251", "304");
        this.addQuestion(q16);
        Question q17 = new Question("12 x 18 = ?", "210", "216", "219", "216");
        this.addQuestion(q17);
        Question q18 = new Question("161 ÷ 7 = ?", "23", "26", "30", "23");
        this.addQuestion(q18);
        // END
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(Question quest) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());

        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));


                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

}
