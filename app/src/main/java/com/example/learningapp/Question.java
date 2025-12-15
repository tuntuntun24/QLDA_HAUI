package com.example.learningapp;

public class Question {
    private int id;
    private String content; // Chuỗi gốc từ DB (chứa cả câu hỏi + đáp án)
    private String questionText; // Chỉ nội dung câu hỏi
    private String optionA, optionB, optionC, optionD; // 4 đáp án tách ra
    private String correctAnswer; // Đáp án đúng từ DB ('A', 'B', 'C', 'D')
    private String userAnswer = ""; // Đáp án người dùng chọn ('A', 'B', 'C', 'D' hoặc rỗng)
    private int lessonId; // maDeCuong

    public Question() { }

    // --- HÀM QUAN TRỌNG: Tách chuỗi từ DB ---
    public void setFullContent(String rawContent) {
        this.content = rawContent;
        try {
            // Giả định định dạng trong DB là:
            // "Nội dung câu hỏi?\nA. Đáp án A\nB. Đáp án B..."
            // Sử dụng regex để tìm vị trí các đáp án bắt đầu bằng xuống dòng và ký tự in hoa

            // Tìm vị trí bắt đầu của các đáp án
            int idxA = rawContent.lastIndexOf("\nA. ");
            int idxB = rawContent.lastIndexOf("\nB. ");
            int idxC = rawContent.lastIndexOf("\nC. ");
            int idxD = rawContent.lastIndexOf("\nD. ");

            if (idxA != -1 && idxB != -1 && idxC != -1 && idxD != -1) {
                // Cắt chuỗi
                this.questionText = rawContent.substring(0, idxA).trim();
                this.optionA = rawContent.substring(idxA + 4, idxB).trim(); // +4 để bỏ qua "\nA. "
                this.optionB = rawContent.substring(idxB + 4, idxC).trim();
                this.optionC = rawContent.substring(idxC + 4, idxD).trim();
                this.optionD = rawContent.substring(idxD + 4).trim();
            } else {
                // Fallback nếu dữ liệu không đúng định dạng
                this.questionText = rawContent;
                this.optionA = "Lựa chọn A";
                this.optionB = "Lựa chọn B";
                this.optionC = "Lựa chọn C";
                this.optionD = "Lựa chọn D";
            }
        } catch (Exception e) {
            this.questionText = rawContent;
        }
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }

    public int getLessonId() { return lessonId; }
    public void setLessonId(int lessonId) { this.lessonId = lessonId; }
}