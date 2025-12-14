package com.example.learningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;

public class CauHoiAdapter extends BaseAdapter {

    private Context context;
    private List<Question> listCauHoi;

    public CauHoiAdapter(Context context, List<Question> listCauHoi) {
        this.context = context;
        this.listCauHoi = listCauHoi;
    }

    @Override
    public int getCount() {
        return listCauHoi.size();
    }

    @Override
    public Object getItem(int position) {
        return listCauHoi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cau_hoi, parent, false);
            holder = new MyViewHolder();
            holder.tvNoiDung = convertView.findViewById(R.id.tvNoiDungCauHoi);
            holder.rgDapAn = convertView.findViewById(R.id.rgDapAn);
            holder.rbA = convertView.findViewById(R.id.rbA);
            holder.rbB = convertView.findViewById(R.id.rbB);
            holder.rbC = convertView.findViewById(R.id.rbC);
            holder.rbD = convertView.findViewById(R.id.rbD);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        Question q = listCauHoi.get(position);

        // Hiển thị nội dung
        holder.tvNoiDung.setText("Câu " + (position + 1) + ": " + q.getQuestion());
        holder.rbA.setText(q.getOption1());
        holder.rbB.setText(q.getOption2());
        holder.rbC.setText(q.getOption3());
        holder.rbD.setText(q.getOption4());

        // --- QUAN TRỌNG: XỬ LÝ CHECKED CHANGE ĐỂ LƯU ĐÁP ÁN ---

        // 1. Gỡ bỏ listener cũ để tránh vòng lặp vô tận khi setChecked
        holder.rgDapAn.setOnCheckedChangeListener(null);

        // 2. Set trạng thái check dựa vào dữ liệu đã lưu trong Model (Question)
        switch (q.getUserAnswer()) {
            case 1: holder.rbA.setChecked(true); break;
            case 2: holder.rbB.setChecked(true); break;
            case 3: holder.rbC.setChecked(true); break;
            case 4: holder.rbD.setChecked(true); break;
            default: holder.rgDapAn.clearCheck(); break;
        }

        // 3. Gán lại listener để lắng nghe khi người dùng bấm chọn
        holder.rgDapAn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbA) q.setUserAnswer(1);
                else if (checkedId == R.id.rbB) q.setUserAnswer(2);
                else if (checkedId == R.id.rbC) q.setUserAnswer(3);
                else if (checkedId == R.id.rbD) q.setUserAnswer(4);
            }
        });

        return convertView;
    }

    static class MyViewHolder {
        TextView tvNoiDung;
        RadioGroup rgDapAn;
        RadioButton rbA, rbB, rbC, rbD;
    }
}