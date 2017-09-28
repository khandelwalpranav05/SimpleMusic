package com.example.pranav.playmusic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pranav on 01-07-2017.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicItemHolder>{

    private ArrayList<File> mySong;
    private Context context;

    public MusicListAdapter(ArrayList<File> mySong,Context context) {
        this.context=context;
        this.mySong=mySong;
    }

    @Override
    public MusicItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = li.inflate(R.layout.list_style,parent,false);

        return new MusicItemHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MusicItemHolder holder, final int position) {

        final File thisMusic = mySong.get(position);

        holder.name_Song.setText(thisMusic.getName().toString().replace(".mp3",""));

        holder.name_Song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,Player.class);
                i.putExtra("MySong",mySong);
                i.putExtra("pos",position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mySong.size();
    }

    public class MusicItemHolder extends RecyclerView.ViewHolder {

        TextView name_Song;

        public MusicItemHolder(View itemView) {
            super(itemView);
            this.name_Song = (TextView) itemView.findViewById(R.id.musicName);
        }
    }

}
