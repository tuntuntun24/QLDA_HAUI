package com.example.learningapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class OnTapActivity extends AppCompatActivity {

    ListView lvBaiKiemTra;
    TextView tvDiem1, tvDiem2, tvDiem3;

    // Danh sách tên hiển thị trên ListView
    String[] danhSachBaiThi = {
            "Bài kiểm tra thường xuyên 1 (KTTX1)",
            "Bài kiểm tra thường xuyên 2 (KTTX2)",
            "Bài kiểm tra học phần (KTHP)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_tap);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ôn tập & Kiểm tra");
        }

        // --- ÁNH XẠ ---
        lvBaiKiemTra = findViewById(R.id.lvBaiKiemTra);
        tvDiem1 = findViewById(R.id.tvDiemKttx1);
        tvDiem2 = findViewById(R.id.tvDiemKttx2);
        tvDiem3 = findViewById(R.id.tvDiemKthp);

        // --- CÀI ĐẶT LISTVIEW ---
        // Sử dụng Adapter và Layout 'item_bai_hoc' giống hệt bên Đề cương
        MyAdapter adapter = new MyAdapter(this, R.layout.item_bai_hoc, danhSachBaiThi);
        lvBaiKiemTra.setAdapter(adapter);

        // --- SỰ KIỆN CLICK VÀO LIST ---
        lvBaiKiemTra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String maDeThi = "";
                String tenHienThi = ""; // Thêm cái tên cho đẹp

                if (position == 0) {
                    maDeThi = "KEY_KTTX1";
                    tenHienThi = "KTTX 1 (Bài 1-3)";
                } else if (position == 1) {
                    maDeThi = "KEY_KTTX2";
                    tenHienThi = "KTTX 2 (Bài 4-6)";
                } else if (position == 2) {
                    maDeThi = "KEY_KTHP";
                    tenHienThi = "KTHP (Toàn bộ)";
                }

                // CHUYỂN HƯỚNG SANG MÀN HÌNH TRUNG GIAN (CHI TIẾT)
                Intent intent = new Intent(OnTapActivity.this, ChiTietOnTapActivity.class);
                intent.putExtra("MA_DE_THI", maDeThi);
                intent.putExtra("TEN_HIEN_THI", tenHienThi);
                startActivity(intent);
            }
        });
    }

    // --- CẬP NHẬT BẢNG ĐIỂM KHI QUAY LẠI ---
    @Override
    protected void onResume() {
        super.onResume();
        capNhatBangDiem();
    }

    private void capNhatBangDiem() {
        SharedPreferences prefs = getSharedPreferences("LUU_DIEM_SO", Context.MODE_PRIVATE);

        // Đọc điểm dạng Float (Số thực). Mặc định là -1.0 nếu chưa thi
        float d1 = prefs.getFloat("KEY_KTTX1", -1.0f);
        float d2 = prefs.getFloat("KEY_KTTX2", -1.0f);
        float d3 = prefs.getFloat("KEY_KTHP", -1.0f);

        // Hiển thị lên màn hình
        tvDiem1.setText(d1 == -1.0f ? "Chưa thi" : d1 + " điểm");
        tvDiem2.setText(d2 == -1.0f ? "Chưa thi" : d2 + " điểm");
        tvDiem3.setText(d3 == -1.0f ? "Chưa thi" : d3 + " điểm");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --- ADAPTER TÙY CHỈNH (Giống bên DanhSachBaiActivity) ---
    class MyAdapter extends ArrayAdapter<String> {
        AppCompatActivity context;
        int resource;
        String[] data;

        public MyAdapter(AppCompatActivity context, int resource, String[] data) {
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

            // Vì item_bai_hoc.xml root là TextView
            TextView tvTen = (TextView) v;
            tvTen.setText(data[position]);

            // Đổi màu chút cho khác biệt với bài học (Optional)
            tvTen.setTextColor(Color.parseColor("#000000"));

            return v;
        }
    }
}