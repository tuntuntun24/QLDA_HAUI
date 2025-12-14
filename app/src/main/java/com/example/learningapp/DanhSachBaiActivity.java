package com.example.learningapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DanhSachBaiActivity extends AppCompatActivity {

    ListView lvBaiHoc;
    // Danh sách hiển thị lên màn hình
    ArrayList<String> arrHienThi = new ArrayList<>();
    // Danh sách file PDF tương ứng
    ArrayList<String> arrFilePdf = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bai);

        // --- NHẬN DỮ LIỆU TỪ MÀN HÌNH CHƯƠNG ---
        Intent intent = getIntent();
        int viTriChuong = intent.getIntExtra("VITRI_CHUONG", 0);
        String tenChuong = intent.getStringExtra("TEN_CHUONG");

        // --- CẤU HÌNH THANH TIÊU ĐỀ ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(tenChuong);
        }

        lvBaiHoc = findViewById(R.id.lvBaiHoc);

        // --- TẠO DỮ LIỆU ---
        taoDuLieuBaiHoc(viTriChuong);

        // --- GÁN ADAPTER ---
        MyAdapter adapter = new MyAdapter(this, R.layout.item_bai_hoc, arrHienThi);
        lvBaiHoc.setAdapter(adapter);

        // --- XỬ LÝ SỰ KIỆN CLICK ---
        lvBaiHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position % 2 == 0) {
                    // === CLICK VÀO ĐỀ CƯƠNG ===
                    int pdfIndex = position / 2;

                    if (pdfIndex < arrFilePdf.size()) {
                        Intent i = new Intent(DanhSachBaiActivity.this, ChiTietBaiActivity.class);

                        // 1. Gửi tên file PDF (như cũ)
                        i.putExtra("pdfFileName", arrFilePdf.get(pdfIndex));

                        // 2. Gửi thêm TIÊU ĐỀ (Lấy đúng dòng chữ đang hiện trên danh sách)
                        // Ví dụ: "Đề cương bài 1"
                        String tieuDeCanHien = arrHienThi.get(position);
                        i.putExtra("TIEU_DE_ACTIONBAR", tieuDeCanHien);

                        startActivity(i);
                    }
                } else {
                    // === CLICK VÀO TRẮC NGHIỆM (SỬA ĐOẠN NÀY) ===
                    Intent i = new Intent(DanhSachBaiActivity.this, TracNghiemActivity.class);

                    // Tính số bài dựa trên vị trí chương (viTriChuong nhận từ Intent ở onCreate)
                    // Ví dụ: Chương 1 (index 0) -> Bài 1
                    int soBai = viTriChuong + 1;

                    // Gửi mã đề đặc biệt: "LESSON_1", "LESSON_2"...
                    i.putExtra("MA_DE_THI", "LESSON_" + soBai);

                    startActivity(i);
                }
            }
        });
    }

    // --- HÀM TẠO DỮ LIỆU ĐƯỢC SỬA LẠI ---
    private void taoDuLieuBaiHoc(int viTriChuong) {
        arrHienThi.clear();
        arrFilePdf.clear();

        // Quy tắc:
        // Chương 1 (viTri 0) -> Bài 1
        // Chương 2 (viTri 1) -> Bài 2
        // ...
        // Chương 8 (viTri 7) -> Bài 8

        int soBai = viTriChuong + 1; // Tính số bài

        // Tạo tên file PDF tự động: bai1.pdf, bai2.pdf, ..., bai8.pdf
        String tenFilePdf = "bai" + soBai + ".pdf";

        // Thêm vào danh sách
        themBai(String.valueOf(soBai), tenFilePdf);
    }

    // Hàm thêm bài: Tạo 2 dòng "Đề cương" và "Trắc nghiệm"
    private void themBai(String soBai, String filePdf) {
        // Dòng 1
        arrHienThi.add("Đề cương bài " + soBai);
        arrFilePdf.add(filePdf);

        // Dòng 2
        arrHienThi.add("Trắc nghiệm bài " + soBai);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends ArrayAdapter<String> {
        AppCompatActivity context;
        int resource;
        ArrayList<String> data;

        public MyAdapter(AppCompatActivity context, int resource, ArrayList<String> data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View v = inflater.inflate(resource, null);
            TextView tvTen = (TextView) v;

            String text = data.get(position);
            tvTen.setText(text);

//            if (text.startsWith("Trắc nghiệm")) {
//                tvTen.setTextColor(Color.parseColor("#E64A19")); // Màu cam đỏ
//            } else {
//                tvTen.setTextColor(Color.parseColor("#1565C0")); // Màu xanh
//            }

            return v;
        }
    }
}