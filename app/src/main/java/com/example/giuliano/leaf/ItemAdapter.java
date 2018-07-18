package com.example.giuliano.leaf;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuliano.leaf.controller.DetailActivity;
import com.example.giuliano.leaf.controller.MainActivity;
import com.example.giuliano.leaf.model.Classification;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Classification> items;
    private Context context;

    public ItemAdapter(Context applicationContext, List<Classification> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(items.get(i).getName());
        viewHolder.githublink1.setText(items.get(i).get_id());

        Picasso.with(context)
                .load("http://192.168.43.205:3000/"+items.get(i).getImageUrl())
                .placeholder(R.drawable.load)
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, githublink1;
        private ImageView imageView;


        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            githublink1 = (TextView) view.findViewById(R.id.githublink1);
            imageView = (ImageView) view.findViewById(R.id.cover);

            //on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Classification clickedDataItem = items.get(pos);
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("login", items.get(pos).getName());
                        intent.putExtra("classificacao", items.get(pos).getSeveridadeAll());
                        intent.putExtra("avatar_url", "http://192.168.43.205:3000/"+items.get(pos).getImageUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.get_id(), Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
}