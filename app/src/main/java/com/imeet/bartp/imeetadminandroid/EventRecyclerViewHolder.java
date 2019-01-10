package com.imeet.bartp.imeetadminandroid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EventRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView txt_name, txt_description, txt_date, txt_starttime, txt_endtime;

    IItemClickListener iItemClickListener;

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public EventRecyclerViewHolder(View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(R.id.txt_name);
        txt_date = (TextView)itemView.findViewById(R.id.txt_date);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iItemClickListener.onClick(v,getAdapterPosition());
    }
}
