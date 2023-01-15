package com.example.pennychet.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pennychet.R;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<GridModel> {

    public GridAdapter(@NonNull Context context, ArrayList<GridModel> gridModelArrayList) {
        super(context, 0, gridModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        GridModel gridModel = getItem(position);
        TextView categoryName = listitemView.findViewById(R.id.category_name);
        TextView categorySum = listitemView.findViewById(R.id.category_sum);
        ImageView categoryImage = listitemView.findViewById(R.id.image_ctg);

        categoryName.setText(gridModel.getCategoryName());
        categoryImage.setImageResource(gridModel.getImgid());
        categoryImage.setBackgroundColor(gridModel.getColorId());
        categorySum.setText(String.format("%s %s", gridModel.getSum(), "UAH"));

        return listitemView;
    }
}

