package com.example.learningapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    // Lưu ý: Tên file khi lưu vào máy vẫn có thể giữ là data-real.db hoặc data_real.db tùy bạn
    private static final String DATABASE_NAME = "data_real.db";
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "";
    private final Context mContext;

    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        // Đường dẫn lưu DB trên thiết bị người dùng
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";

        // Kiểm tra xem DB đã có trên máy chưa, nếu chưa thì copy từ res/raw
        if (!checkDataBase()) {
            copyDataBase();
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        try {
            // --- THAY ĐỔI Ở ĐÂY ---
            // Thay vì dùng assets, ta dùng openRawResource với ID của file
            // R.raw.data_real là ID được tạo ra sau khi bạn copy file data_real.db vào res/raw
            InputStream mInput = mContext.getResources().openRawResource(R.raw.data_real);

            String outFileName = DB_PATH + DATABASE_NAME;
            File f = new File(DB_PATH);
            if (!f.exists()) f.mkdir();

            OutputStream mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
            mOutput.close();
            mInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không cần tạo bảng vì chúng ta copy file DB có sẵn
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý khi update version nếu cần
    }

    // --- HÀM LẤY CÂU HỎI (GIỮ NGUYÊN LOGIC CŨ) ---
    public List<Question> getQuestionsForExam(String examType) {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query;

        // Bảng: Cau (maCau, noiDungCauHoi, dapAnDung, maDeCuong)
        if (examType.equals("KEY_KTTX1")) {
            // KTTX1: Bài 1-4 (Ngẫu nhiên 10 câu)
            query = "SELECT * FROM Cau WHERE maDeCuong BETWEEN 1 AND 4 ORDER BY RANDOM() LIMIT 10";
        }
        else if (examType.equals("KEY_KTTX2")) {
            // KTTX2: Bài 5-8 (Ngẫu nhiên 10 câu)
            query = "SELECT * FROM Cau WHERE maDeCuong BETWEEN 5 AND 8 ORDER BY RANDOM() LIMIT 10";
        }
        else if (examType.equals("KEY_KTHP")) {
            // KTHP: Tất cả bài 1-10 (Ngẫu nhiên 15 câu)
            query = "SELECT * FROM Cau ORDER BY RANDOM() LIMIT 15";
        }
        else if (examType.startsWith("LESSON_")) {
            // Theo bài học cụ thể
            String lessonId = examType.replace("LESSON_", "");
            query = "SELECT * FROM Cau WHERE maDeCuong = " + lessonId + " LIMIT 10";
        } else {
            query = "SELECT * FROM Cau ORDER BY RANDOM() LIMIT 10";
        }

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(c.getInt(c.getColumnIndexOrThrow("maCau")));

                // Lấy nội dung gốc và tách (Hàm setFullContent trong Question.java)
                String fullContent = c.getString(c.getColumnIndexOrThrow("noiDungCauHoi"));
                q.setFullContent(fullContent);

                q.setCorrectAnswer(c.getString(c.getColumnIndexOrThrow("dapAnDung")));
                q.setLessonId(c.getInt(c.getColumnIndexOrThrow("maDeCuong")));

                questionList.add(q);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}