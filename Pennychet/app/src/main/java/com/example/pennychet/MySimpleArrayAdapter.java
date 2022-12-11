package com.example.pennychet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] categories;
    private final String[] accounts;
    private final String[] date;
    private final String[] sum;

    public MySimpleArrayAdapter(Context context, String[] categories, String[] accounts, String[] date, String[] sum) {
        super(context, -1, categories);
        this.context = context;
        this.categories = categories;
        this.accounts = accounts;
        this.sum = sum;
        this.date = date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.history_list_item, parent, false);

        TextView textViewCategories = (TextView) rowView.findViewById(R.id.textViewHistoryCategory);
        textViewCategories.setText(categories[position]);

        TextView textViewAccounts = (TextView) rowView.findViewById(R.id.textViewHistoryAccount);
        textViewAccounts.setText(accounts[position]);

        TextView textViewSum = (TextView) rowView.findViewById(R.id.textViewHistorySum);
        textViewSum.setText("UAH " + sum[position]);

        TextView textViewDate = (TextView) rowView.findViewById(R.id.textViewHistoryDate);
        textViewDate.setText(date[position]);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewHistoryItem);
        imageView.setImageResource(R.drawable.ic_baseline_category_24);
        imageView.setColorFilter(Color.BLACK);

        rowView.bringToFront();

        return rowView;
    }
}
