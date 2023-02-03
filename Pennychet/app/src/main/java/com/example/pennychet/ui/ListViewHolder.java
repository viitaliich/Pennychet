package com.example.pennychet.ui;

// ViewHolder code for RecyclerView

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennychet.R;

public class ListViewHolder extends RecyclerView.ViewHolder {
    TextView tvCategory;
    TextView tvAccount;
    TextView tvDate;
    TextView tvDescription;
    TextView tvSum;
    ImageView ivIcon;
    View view;

    ListViewHolder(View itemView)
    {
        super(itemView);
        tvCategory = (TextView)itemView.findViewById(R.id.categoryName);
        tvAccount = (TextView)itemView.findViewById(R.id.accountName);
        tvDate = (TextView)itemView.findViewById(R.id.transactionDate);
        tvDescription = (TextView)itemView.findViewById(R.id.transactionDescription);
        tvSum = (TextView)itemView.findViewById(R.id.categorySum);
        ivIcon = (ImageView) itemView.findViewById(R.id.categoryIcon);
        view = itemView;
    }
}
