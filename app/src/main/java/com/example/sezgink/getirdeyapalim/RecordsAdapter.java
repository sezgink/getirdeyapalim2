package com.example.sezgink.getirdeyapalim;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Asus on 3.02.2018.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.MyViewHolder> {
    private List<Record> recordList;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView key,date,count;
        public MyViewHolder(View view) {
            super(view);
            key = (TextView) view.findViewById(R.id.Key);
            count = (TextView) view.findViewById(R.id.Count);
            date = (TextView) view.findViewById(R.id.Date);
        }

    }

    public RecordsAdapter(List recordList) {
        this.recordList = recordList;


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Record record = recordList.get(position);

        holder.count.setText(Integer.toString(record.totalCount));
        holder.key.setText(record._id.key);
        holder.date.setText( record._id.createdAt);



    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

}
