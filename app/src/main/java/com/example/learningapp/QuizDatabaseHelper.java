package com.example.learningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LearningApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "quiz_questions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_OPTION1 = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3 = "option3";
    private static final String COLUMN_OPTION4 = "option4";
    private static final String COLUMN_ANSWER_NR = "answer_nr";
    private static final String COLUMN_LESSON_ID = "lesson_id";

    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_OPTION4 + " TEXT, " +
                COLUMN_ANSWER_NR + " INTEGER, " +
                COLUMN_LESSON_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE);

        // --- NHẬP DỮ LIỆU CÂU HỎI (35 CÂU) ---
        fillQuestionsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(SQLiteDatabase db) {
        // Bài 1
        addQuestion(db, new Question("Theo PMI, dự án là gì?", "Quá trình liên tục", "Nỗ lực tạm thời tạo ra sản phẩm duy nhất", "Hoạt động quản lý nhân sự", "Chương trình phần mềm", 2, 1));
        addQuestion(db, new Question("Ràng buộc bộ ba gồm những gì?", "Phạm vi, Thời gian, Chi phí", "Con người, Máy móc, Tiền", "Chất lượng, Rủi ro, Nguồn lực", "Lập kế hoạch, Thực hiện, Giám sát", 1, 1));
        addQuestion(db, new Question("Đâu KHÔNG phải là tuyên ngôn Agile?", "Cá nhân hơn quy trình", "Phản hồi hơn kế hoạch", "Hợp đồng hơn cộng tác", "Sản phẩm chạy được hơn tài liệu", 3, 1));
        addQuestion(db, new Question("Chức năng nào thuộc quản lý dự án?", "Lập trình", "Kiểm thử", "Lập kế hoạch", "Bán hàng", 3, 1));
        addQuestion(db, new Question("Dự án kết thúc khi nào?", "Trưởng dự án nghỉ", "Đạt mục tiêu hoặc không thể đạt nữa", "Hết giờ làm", "Tất cả đồng ý dừng", 2, 1));

        // Bài 2
        addQuestion(db, new Question("Cấu trúc User Story chuẩn?", "If-Then", "As a... I want... So that...", "Project... will do...", "User needs...", 2, 2));
        addQuestion(db, new Question("Chữ M trong MoSCoW là gì?", "Maybe have", "Must have", "Money have", "Most have", 2, 2));
        addQuestion(db, new Question("Tiêu chí INVEST gồm?", "Independent, Negotiable...", "Important, Nice...", "Internet, Network...", "Independent, New...", 1, 2));
        addQuestion(db, new Question("Yêu cầu phi chức năng mô tả gì?", "Tính năng cụ thể", "Đặc tính chất lượng (Hiệu năng, Bảo mật)", "Quy tắc nghiệp vụ", "Danh sách nhân sự", 2, 2));
        addQuestion(db, new Question("Product Backlog là gì?", "Danh sách lỗi", "Danh sách ưu tiên các tính năng cần làm", "Báo cáo tài chính", "Hướng dẫn sử dụng", 2, 2));

        // Bài 3
        addQuestion(db, new Question("Ai quản lý Product Backlog?", "Scrum Master", "Dev Team", "Product Owner", "Khách hàng", 3, 3));
        addQuestion(db, new Question("Mục đích Sprint Planning?", "Demo sản phẩm", "Xác định mục tiêu và việc cần làm trong Sprint", "Họp rút kinh nghiệm", "Báo cáo ngày", 2, 3));
        addQuestion(db, new Question("Daily Scrum tối đa bao lâu?", "15 phút", "1 tiếng", "4 tiếng", "Không giới hạn", 1, 3));
        addQuestion(db, new Question("3 câu hỏi trong Daily Scrum?", "Tên? Tuổi? Lương?", "Hôm qua làm gì? Nay làm gì? Khó khăn gì?", "Khách là ai? Tiền bao nhiêu?", "Tại sao chậm?", 2, 3));
        addQuestion(db, new Question("Sprint Backlog là gì?", "Việc cả dự án", "Việc cam kết làm trong 1 Sprint", "Danh sách nhân viên", "Kế hoạch marketing", 2, 3));

        // Bài 4
        addQuestion(db, new Question("CI trong Agile là gì?", "Triển khai liên tục", "Tích hợp liên tục mã nguồn và test tự động", "Họp liên tục", "Viết tài liệu liên tục", 2, 4));
        addQuestion(db, new Question("Ai làm Unit Testing?", "Khách hàng", "Product Owner", "Lập trình viên (Dev)", "Giám đốc", 3, 4));
        addQuestion(db, new Question("Mục tiêu Kiểm thử chấp nhận?", "Tìm lỗi chính tả", "Test hàm nhỏ", "Xác nhận đáp ứng yêu cầu nghiệp vụ", "Test tốc độ", 3, 4));
        addQuestion(db, new Question("Thay đổi yêu cầu trong Agile?", "Cấm tuyệt đối", "Được chào đón và quản lý linh hoạt", "Chỉ khi trả thêm tiền", "Phải hủy dự án", 2, 4));
        addQuestion(db, new Question("Phương pháp triển khai song song giảm rủi ro?", "Waterfall", "Blue-Green Deployment", "Manual", "Hard Reset", 2, 4));

        // Bài 5
        addQuestion(db, new Question("Story Point dùng để làm gì?", "Đếm nhân viên", "Ước lượng độ phức tạp/nỗ lực", "Tính thưởng", "Đánh giá hài lòng", 2, 5));
        addQuestion(db, new Question("Kỹ thuật ước lượng Story Point?", "Planning Poker", "Bốc thăm", "Biểu quyết", "PO quyết định", 1, 5));
        addQuestion(db, new Question("Velocity đo lường gì?", "Tốc độ gõ phím", "Khối lượng việc hoàn thành trong 1 Sprint", "Thời gian chạy app", "Số lượng lỗi", 2, 5));
        addQuestion(db, new Question("Biểu đồ theo dõi tiến độ Sprint?", "Pie Chart", "Burndown Chart", "Gantt Chart", "Flowchart", 2, 5));
        addQuestion(db, new Question("Definition of Done (DoD) giúp gì?", "Biết giờ tan làm", "Thống nhất tiêu chuẩn hoàn thành", "Biết khi nào trả tiền", "Viết báo cáo", 2, 5));

        // Bài 6
        addQuestion(db, new Question("BDD dùng cấu trúc nào?", "If-Then-Else", "Given-When-Then", "Start-Process-End", "Input-Output", 2, 6));
        addQuestion(db, new Question("Nhóm Dev chia nhỏ User Story thành gì?", "Themes", "Epics", "Tasks", "Projects", 3, 6));
        addQuestion(db, new Question("WIP trong Kanban là gì?", "Việc đã xong", "Số việc đang làm đồng thời (cần giới hạn)", "Việc chưa làm", "Việc hủy bỏ", 2, 6));
        addQuestion(db, new Question("Ai chịu trách nhiệm viết code?", "Scrum Master", "Product Owner", "Development Team", "Stakeholders", 3, 6));
        addQuestion(db, new Question("Mục đích của Refactoring?", "Viết lại từ đầu", "Cải thiện code mà không đổi hành vi", "Thêm tính năng mới", "Xóa bớt code", 2, 6));

        // Bài 7
        addQuestion(db, new Question("Sự kiện cuối cùng của Sprint?", "Sprint Planning", "Daily Scrum", "Sprint Review", "Sprint Retrospective", 4, 7));
        addQuestion(db, new Question("Hoạt động chính của Sprint Review?", "Phê bình nhau", "Demo sản phẩm và nhận phản hồi", "Lập kế hoạch", "Viết code", 2, 7));
        addQuestion(db, new Question("Lý do đóng dự án bài bản?", "Ăn mừng", "Rút bài học kinh nghiệm (Lessons Learned)", "Đuổi việc", "Xóa tài liệu", 2, 7));
        addQuestion(db, new Question("Kỹ thuật tìm nguyên nhân gốc rễ?", "5 Whys", "Planning Poker", "MoSCoW", "SWOT", 1, 7));
        addQuestion(db, new Question("Tài liệu KHÔNG bàn giao khi kết thúc?", "Hướng dẫn sử dụng", "Mã nguồn", "Biên bản nghiệm thu", "Thực đơn ăn trưa", 4, 7));
    }

    private void addQuestion(SQLiteDatabase db, Question q) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, q.getQuestion());
        cv.put(COLUMN_OPTION1, q.getOption1());
        cv.put(COLUMN_OPTION2, q.getOption2());
        cv.put(COLUMN_OPTION3, q.getOption3());
        cv.put(COLUMN_OPTION4, q.getOption4());
        cv.put(COLUMN_ANSWER_NR, q.getAnswerNr());
        cv.put(COLUMN_LESSON_ID, q.getLessonId());
        db.insert(TABLE_NAME, null, cv);
    }

    // --- HÀM LẤY ĐỀ THI THEO YÊU CẦU ---
    public List<Question> getQuestionsForExam(String examType) {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";

        // 1. XỬ LÝ CÁC BÀI KIỂM TRA TỔNG HỢP (ÔN TẬP)
        if (examType.equals("KEY_KTTX1")) {
            // KTTX1: 10 câu ngẫu nhiên từ bài 1-3
            query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_LESSON_ID + " BETWEEN 1 AND 3" +
                    " ORDER BY RANDOM() LIMIT 10";
        }
        else if (examType.equals("KEY_KTTX2")) {
            // KTTX2: 15 câu ngẫu nhiên từ bài 1-6
            query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_LESSON_ID + " BETWEEN 1 AND 6" +
                    " ORDER BY RANDOM() LIMIT 15";
        }
        else if (examType.equals("KEY_KTHP")) {
            // KTHP: 15 câu ngẫu nhiên từ bài 1-7
            query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_LESSON_ID + " BETWEEN 1 AND 7" +
                    " ORDER BY RANDOM() LIMIT 15";
        }
        // 2. XỬ LÝ TRẮC NGHIỆM THEO TỪNG BÀI (MỚI THÊM)
        else if (examType.startsWith("LESSON_")) {
            // Cắt chuỗi để lấy số bài. Ví dụ "LESSON_5" -> lấy số 5
            String soBaiStr = examType.replace("LESSON_", "");

            // Câu lệnh: Lấy 5 câu ngẫu nhiên của ĐÚNG bài đó
            query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_LESSON_ID + " = " + soBaiStr +
                    " ORDER BY RANDOM() LIMIT 5";
        }
        else {
            // Mặc định phòng hờ
            query = "SELECT * FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 5";
        }

        // Thực thi truy vấn
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(c.getInt(c.getColumnIndexOrThrow(COLUMN_ID)));
                q.setQuestion(c.getString(c.getColumnIndexOrThrow(COLUMN_QUESTION)));
                q.setOption1(c.getString(c.getColumnIndexOrThrow(COLUMN_OPTION1)));
                q.setOption2(c.getString(c.getColumnIndexOrThrow(COLUMN_OPTION2)));
                q.setOption3(c.getString(c.getColumnIndexOrThrow(COLUMN_OPTION3)));
                q.setOption4(c.getString(c.getColumnIndexOrThrow(COLUMN_OPTION4)));
                q.setAnswerNr(c.getInt(c.getColumnIndexOrThrow(COLUMN_ANSWER_NR)));
                q.setLessonId(c.getInt(c.getColumnIndexOrThrow(COLUMN_LESSON_ID)));
                questionList.add(q);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}