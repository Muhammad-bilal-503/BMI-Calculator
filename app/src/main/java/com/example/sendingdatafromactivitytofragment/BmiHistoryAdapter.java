package com.example.sendingdatafromactivitytofragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendingdatafromactivitytofragment.db.BmiRecord;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BmiHistoryAdapter extends RecyclerView.Adapter<BmiHistoryAdapter.BmiViewHolder> {

    private List<BmiRecord> records = Collections.emptyList();

    @NonNull
    @Override
    public BmiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BmiViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BmiViewHolder holder, int position) {
        BmiRecord current = records.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String date = sdf.format(current.timestamp);

        holder.text1.setText(String.format(Locale.getDefault(), "BMI: %.2f (%s)", current.bmi, current.category));
        holder.text2.setText(date);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void setRecords(List<BmiRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    static class BmiViewHolder extends RecyclerView.ViewHolder {
        private final TextView text1;
        private final TextView text2;

        private BmiViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}