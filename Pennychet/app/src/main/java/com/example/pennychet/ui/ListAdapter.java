package com.example.pennychet.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennychet.R;

import java.util.Collections;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    List<ListData> list;

    Context context;
    ListClickListener listener;

    public ListAdapter(List<ListData> list, Context context, ListClickListener listener)
    {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ListViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View photoView = inflater.inflate(R.layout.list_card_item, parent, false);

        ListViewHolder viewHolder = new ListViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final ListViewHolder viewHolder, final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.examName.setText(list.get(position).name);
        viewHolder.examDate.setText(list.get(position).date);
        viewHolder.examMessage.setText(list.get(position).message);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listener.click(index);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
