package com.example.mortrza.myadvertismentdispaly.ADV;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mortrza.myadvertismentdispaly.R;

import java.util.List;

public class AgahiAdapter extends RecyclerView.Adapter<AgahiAdapter.AgahiViewHolder> {

    private Context context;
    private List<Agahi> agahiList;

    public AgahiAdapter(Context context, List<Agahi> agahiList){
        this.context = context;
        this.agahiList = agahiList;
    }


    @NonNull
    @Override
    public AgahiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_agahi,parent,false);

        return new AgahiViewHolder(view);
    }
 
    @Override
    public void onBindViewHolder(@NonNull final AgahiViewHolder holder, final int position) {

        holder.title.setText(agahiList.get(position).getTitle());



        //holder.description.setText(agahiList.get(position).getDescription());

        holder.description.setText(agahiList.get(position).getDescription().substring(0,6)+" ... ");



        byte[] p = agahiList.get(position).getAx();
        Bitmap bm = BitmapFactory.decodeByteArray(p,0,p.length);
        holder.ax.setImageBitmap(bm);

        ViewCompat.setTransitionName(holder.ax, "profile" );

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context,agahiList.get(position).getDescription().substring(0,5),Toast.LENGTH_LONG).show();

                Intent i = new Intent(context,DetailActivity.class);
                i.putExtra("ID",agahiList.get(position).getId());
                //MainActivity.DefaultTab = Integer.parseInt(agahiList.get(position).getCat())-1;
                context.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return agahiList.size();
    }

    public class AgahiViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView ax;
        CardView card;

        private AgahiViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_list_item_title);
            description = (TextView) itemView.findViewById(R.id.txt_list_item_des);
            ax = (ImageView) itemView.findViewById(R.id.img_list_agahi_ax);
            card = (CardView) itemView.findViewById(R.id.card);

        }
    }

}
