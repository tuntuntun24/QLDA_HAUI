package com.example.learningapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TracNghiemActivity extends AppCompatActivity {

    private ListView lvCauHoi;
    private TextView tvDongHo;
    private Button btnNopBai;

    private List<Question> questionList;
    private CauHoiAdapter adapter;
    private String maDeThi;
    private QuizDatabaseHelper dbHelper;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_lam_bai); // Đổi tiêu đề
        }

        // 1. Nhận dữ liệu
        Intent intent = getIntent();
        maDeThi = intent.getStringExtra("MA_DE_THI");
        if (maDeThi == null) maDeThi = "KEY_KTTX1";

        setupTime(); // Cài đặt thời gian

        // 2. Ánh xạ
        lvCauHoi = findViewById(R.id.lvCauHoi);
        tvDongHo = findViewById(R.id.tvDongHo);
        btnNopBai = findViewById(R.id.btnNopBai);

        // 3. Lấy dữ liệu câu hỏi
        dbHelper = new QuizDatabaseHelper(this);
        questionList = dbHelper.getQuestionsForExam(maDeThi);

        // Trộn câu hỏi (nếu không phải ôn tập theo bài học)
        if (!maDeThi.startsWith("LESSON_")) {
            Collections.shuffle(questionList);
        }

        if (questionList.isEmpty()) {
            Toast.makeText(this, "Không có câu hỏi!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 4. Đưa dữ liệu vào Adapter
        adapter = new CauHoiAdapter(this, questionList);
        lvCauHoi.setAdapter(adapter);

        // 5. Bắt đầu đếm giờ
        startCountDown();

        // 6. Xử lý nút NỘP BÀI
        btnNopBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiXacNhanNopBai();
            }
        });
    }

    private void setupTime() {
        if (maDeThi.equals("KEY_KTTX1")) {
            timeLeftInMillis = 15 * 60 * 1000;
        } else if (maDeThi.equals("KEY_KTTX2") || maDeThi.equals("KEY_KTHP")) {
            timeLeftInMillis = 20 * 60 * 1000;
        } else {
            timeLeftInMillis = 5 * 60 * 1000; // 5 phút cho bài ôn tập nhỏ
        }
    }

    private void hienThiXacNhanNopBai() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.msg_nop_bai_tieu_de))
                .setMessage(getString(R.string.msg_nop_bai_hoi))
                .setPositiveButton(getString(R.string.btn_nop_ngay), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chamDiemVaKetThuc();
                    }
                })
                .setNegativeButton(getString(R.string.btn_lam_tiep), null)
                .show();
    }

    private void chamDiemVaKetThuc() {
        if (countDownTimer != null) countDownTimer.cancel();

        int soCauDung = 0;
        for (Question q : questionList) {
            // So sánh chuỗi đáp án (A, B, C, D)
            if (q.getUserAnswer() != null && q.getUserAnswer().equals(q.getCorrectAnswer())) {
                soCauDung++;
            }
        }

        int tongSoCau = questionList.size();

        // Tính điểm thang 10
        float diemThang10 = ((float) soCauDung / tongSoCau) * 10;
        diemThang10 = (float) (Math.round(diemThang10 * 10.0) / 10.0);

        // Lưu điểm
        luuDiem(diemThang10);

        // Hiển thị kết quả
        String msg = getString(R.string.txt_ket_qua_cau_dung) + soCauDung + "/" + tongSoCau + "\n" +
                getString(R.string.txt_ket_qua_diem) + diemThang10;

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.msg_ket_qua_tieu_de))
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.btn_thoat), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void luuDiem(float diem) {
        if (maDeThi != null && !maDeThi.startsWith("LESSON_")) {
            // Chỉ lưu điểm cho các bài kiểm tra lớn, bài ôn tập nhỏ (LESSON_) có thể không cần lưu hoặc lưu riêng
            SharedPreferences prefs = getSharedPreferences("LUU_DIEM_SO", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putFloat(maDeThi, diem);

            String lichSuCu = prefs.getString("HISTORY_" + maDeThi, "");
            String diemMoiStr = String.valueOf(diem);
            String lichSuMoi = lichSuCu.isEmpty() ? diemMoiStr : lichSuCu + "," + diemMoiStr;
            editor.putString("HISTORY_" + maDeThi, lichSuMoi);

            editor.apply();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                Toast.makeText(TracNghiemActivity.this, getString(R.string.msg_het_gio), Toast.LENGTH_SHORT).show();
                chamDiemVaKetThuc(); // Tự động nộp bài khi hết giờ
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvDongHo.setText(timeFormatted);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Hỏi xác nhận trước khi thoát nếu chưa nộp bài
            hienThiXacNhanNopBai();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}