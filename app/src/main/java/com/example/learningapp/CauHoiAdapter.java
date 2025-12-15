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
    private List<Question> questionList;

    public CauHoiAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public int getCount() { return questionList.size(); }

    @Override
    public Object getItem(int position) { return questionList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cau_hoi, parent, false);
            holder = new ViewHolder();
            holder.tvCauHoi = convertView.findViewById(R.id.tvNoiDungCauHoi);
            holder.rgDapAn = convertView.findViewById(R.id.rgDapAn);
            holder.rbA = convertView.findViewById(R.id.rbA);
            holder.rbB = convertView.findViewById(R.id.rbB);
            holder.rbC = convertView.findViewById(R.id.rbC);
            holder.rbD = convertView.findViewById(R.id.rbD);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question question = questionList.get(position);

        // 1. Hiển thị nội dung (Đã tách từ Question.java)
        holder.tvCauHoi.setText("Câu " + (position + 1) + ": " + question.getQuestionText());

        holder.rbA.setText("A. " + question.getOptionA());
        holder.rbB.setText("B. " + question.getOptionB());
        holder.rbC.setText("C. " + question.getOptionC());
        holder.rbD.setText("D. " + question.getOptionD());

        // 2. Xóa listener cũ để tránh vòng lặp khi set trạng thái
        holder.rgDapAn.setOnCheckedChangeListener(null);

        // 3. Khôi phục trạng thái đã chọn (nếu người dùng cuộn danh sách)
        holder.rgDapAn.clearCheck();
        String userAns = question.getUserAnswer();
        if ("A".equals(userAns)) holder.rbA.setChecked(true);
        else if ("B".equals(userAns)) holder.rbB.setChecked(true);
        else if ("C".equals(userAns)) holder.rbC.setChecked(true);
        else if ("D".equals(userAns)) holder.rbD.setChecked(true);

        // 4. Lắng nghe sự kiện chọn đáp án mới
        holder.rgDapAn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbA) question.setUserAnswer("A");
                else if (checkedId == R.id.rbB) question.setUserAnswer("B");
                else if (checkedId == R.id.rbC) question.setUserAnswer("C");
                else if (checkedId == R.id.rbD) question.setUserAnswer("D");
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvCauHoi;
        RadioGroup rgDapAn;
        RadioButton rbA, rbB, rbC, rbD;
    }
}