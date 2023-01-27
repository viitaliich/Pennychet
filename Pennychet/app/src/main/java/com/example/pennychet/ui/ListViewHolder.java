package com.example.pennychet.ui;

// ViewHolder code for RecyclerView

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennychet.R;

public class ListViewHolder extends RecyclerView.ViewHolder {
    TextView examName;
    TextView examMessage;
    TextView examDate;
    View view;

    ListViewHolder(View itemView)
    {
        super(itemView);
        examName = (TextView)itemView.findViewById(R.id.categoryName);
        examDate = (TextView)itemView.findViewById(R.id.accountName);
        examMessage = (TextView)itemView.findViewById(R.id.transactionDate);
        view = itemView;
    }
}
