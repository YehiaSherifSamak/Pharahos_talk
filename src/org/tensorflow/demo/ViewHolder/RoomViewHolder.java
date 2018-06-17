package org.tensorflow.demo.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.demo.Interface.ItemClickListener;
import org.tensorflow.demo.R;

/**
 * Created by mohamed fathy on 12/10/2017.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtroomnumber;
    public ImageView roomimageview;
    ItemClickListener itemClickListener;

    public RoomViewHolder(View itemView) {
        super(itemView);
        txtroomnumber =(TextView)itemView.findViewById(R.id.roomnumber);
        roomimageview=(ImageView)itemView.findViewById(R.id.roomimage);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view ,getAdapterPosition(),false );

    }
}
