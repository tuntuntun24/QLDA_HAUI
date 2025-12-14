package com.example.learningapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class OnTapActivity extends AppCompatActivity {

    ListView lvBaiKiemTra;
    TextView tvDiem1, tvDiem2, tvDiem3;
    TextView tvDiemTongKet, tvDiemChu; // KHAI BÁO THÊM
    String[] danhSachBaiThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_tap);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_on_tap);
        }

        // --- ÁNH XẠ ---
        lvBaiKiemTra = findViewById(R.id.lvBaiKiemTra);
        tvDiem1 = findViewById(R.id.tvDiemKttx1);
        tvDiem2 = findViewById(R.id.tvDiemKttx2);
        tvDiem3 = findViewById(R.id.tvDiemKthp);

        // Ánh xạ 2 cái mới thêm
        tvDiemTongKet = findViewById(R.id.tvDiemTongKet);
        tvDiemChu = findViewById(R.id.tvDiemChu);

        danhSachBaiThi = getResources().getStringArray(R.array.arr_on_tap);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_bai_hoc,
                danhSachBaiThi
        );
        lvBaiKiemTra.setAdapter(adapter);

        lvBaiKiemTra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String maDeThi = "";
                if (position == 0) maDeThi = "KEY_KTTX1";
                else if (position == 1) maDeThi = "KEY_KTTX2";
                else if (position == 2) maDeThi = "KEY_KTHP";

                Intent intent = new Intent(OnTapActivity.this, ChiTietOnTapActivity.class);
                intent.putExtra("MA_DE_THI", maDeThi);
                intent.putExtra("TEN_HIEN_THI", danhSachBaiThi[position]);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhatBangDiem();
    }

    private void capNhatBangDiem() {
        SharedPreferences prefs = getSharedPreferences("LUU_DIEM_SO", Context.MODE_PRIVATE);

        // Lấy điểm (Mặc định là -1 nếu chưa thi)
        float d1 = prefs.getFloat("KEY_KTTX1", -1.0f);
        float d2 = prefs.getFloat("KEY_KTTX2", -1.0f);
        float d3 = prefs.getFloat("KEY_KTHP", -1.0f);

        // Hiển thị điểm thành phần
        if (d1 == -1.0f) tvDiem1.setText(getString(R.string.txt_chua_thi));
        else tvDiem1.setText(String.valueOf(d1));

        if (d2 == -1.0f) tvDiem2.setText(getString(R.string.txt_chua_thi));
        else tvDiem2.setText(String.valueOf(d2));

        if (d3 == -1.0f) tvDiem3.setText(getString(R.string.txt_chua_thi));
        else tvDiem3.setText(String.valueOf(d3));

        // --- TÍNH ĐIỂM TỔNG KẾT & ĐIỂM CHỮ ---
        // Chỉ tính khi CẢ 3 cột điểm đều đã thi (khác -1)
        if (d1 != -1.0f && d2 != -1.0f && d3 != -1.0f) {

            // Công thức: 20% + 30% + 50%
            double tongKet = (d1 * 0.2) + (d2 * 0.3) + (d3 * 0.5);

            // Làm tròn 1 chữ số thập phân (Ví dụ 8.46 -> 8.5)
            tongKet = Math.round(tongKet * 10) / 10.0;

            tvDiemTongKet.setText(String.valueOf(tongKet));

            // Quy đổi ra chữ
            String diemChu = quyDoiDiemChu(tongKet);
            tvDiemChu.setText(diemChu);

        } else {
            // Nếu chưa đủ 3 đầu điểm
            tvDiemTongKet.setText("--");
            tvDiemChu.setText(getString(R.string.txt_chua_du_diem));
        }
    }

    // --- HÀM QUY ĐỔI ĐIỂM (DỰA THEO ẢNH BẠN GỬI) ---
    private String quyDoiDiemChu(double diem) {
        if (diem >= 8.5) return "A";
        else if (diem >= 7.7) return "B+";
        else if (diem >= 7.0) return "B";
        else if (diem >= 6.2) return "C+";
        else if (diem >= 5.5) return "C";
        else if (diem >= 4.7) return "D+";
        else if (diem >= 4.0) return "D";
        else return "F"; // Dưới 4.0 là tạch
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}