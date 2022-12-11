package com.example.pennychet;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_screen);

        ListView listView = findViewById(R.id.historyListView);

        // определяем строковый массив
        final String[] categories = new String[] {
                "Groceries", "Cafe", "Transport", "Shopping"
        };

        final String[] accounts = new String[] {
                "Card", "Card", "Cash", "Card"
        };

        final String[] date = new String[] {
                "10.12.22", "10.12.22", "10.12.22", "10.12.22"
        };

        final String[] sum = new String[] {
                "455", "100", "16", "1000"
        };

// используем адаптер данных
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, categories, accounts, date, sum);
//                android.R.layout.simple_list_item_1, catNames);

        listView.setAdapter(adapter);

    }
}

