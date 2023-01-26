package com.example.pennychet.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennychet.R;

import java.util.Collections;
import java.util.List;

public class listAdapter
        extends RecyclerView.Adapter<listViewHolder> {

    List<listData> list
            = Collections.emptyList();

    Context context;
    listClickListener listener;

    public listAdapter(List<listData> list,
                                Context context,listClickListener listiner)
    {
        this.list = list;
        this.context = context;
        this.listener = listiner;
    }

    @Override
    public listViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType)
    {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.list_card_item,
                        parent, false);

        listViewHolder viewHolder
                = new listViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final listViewHolder viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.examName
                .setText(list.get(position).name);
        viewHolder.examDate
                .setText(list.get(position).date);
        viewHolder.examMessage
                .setText(list.get(position).message);
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
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


}

