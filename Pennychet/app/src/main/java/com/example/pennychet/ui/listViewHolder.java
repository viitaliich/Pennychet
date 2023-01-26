package com.example.pennychet.ui;

// ViewHolder code for RecyclerView

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennychet.R;

public class listViewHolder
        extends RecyclerView.ViewHolder {
    TextView examName;
    TextView examMessage;
    TextView examDate;
    View view;

    listViewHolder(View itemView)
    {
        super(itemView);
        examName
                = (TextView)itemView
                .findViewById(R.id.examName);
        examDate
                = (TextView)itemView
                .findViewById(R.id.examDate);
        examMessage
                = (TextView)itemView
                .findViewById(R.id.examMessage);
        view = itemView;
    }
}
