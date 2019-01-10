package com.imeet.bartp.imeetadminandroid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EventVisitorRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView txt_name;

    IItemClickListener iItemClickListener;

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public EventVisitorRecyclerViewHolder(View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(R.id.txt_name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iItemClickListener.onClick(v,getAdapterPosition());
    }
}
