package com.example.pennychet.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennychet.R;

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
        viewHolder.tvCategory.setText(list.get(position).category);
        viewHolder.tvAccount.setText(list.get(position).account);
        viewHolder.tvDate.setText(list.get(position).date);
        viewHolder.tvDescription.setText(list.get(position).description);
        viewHolder.tvSum.setText(String.format("%s UAH", String.valueOf(list.get(position).sum)));
        viewHolder.ivIcon.setImageResource(list.get(position).imgid);
        viewHolder.ivIcon.setColorFilter(list.get(position).colorid);       // ???

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
