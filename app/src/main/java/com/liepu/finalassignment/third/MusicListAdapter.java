package com.liepu.finalassignment.third;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liepu.finalassignment.R;
import com.liepu.finalassignment.third.models.Person;

import java.util.ArrayList;
import java.util.Objects;

public class MusicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Person> list;
    private OnContactListener mOnContactListener;

    public MusicListAdapter(ArrayList<Person> list, OnContactListener onContactListener) {
        this.list = list;
        this.mOnContactListener = onContactListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_person_list, parent, false), mOnContactListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MusicItemViewHolder) {
            ((MusicItemViewHolder) holder).name.setText(list.get(position).getName());
            ((MusicItemViewHolder) holder).phone.setText(list.get(position).getPhoneList().getHome());
            ((MusicItemViewHolder) holder).song.setText(list.get(position).getSongPlaylist().get(0).getName());

            if (Objects.equals(list.get(position).getGender(), "male")) {
                ((MusicItemViewHolder) holder).icon.setImageResource(R.drawable.person);
            } else {
                ((MusicItemViewHolder) holder).icon.setImageResource(R.drawable.girl);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    class MusicItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, phone, song;
        ImageView icon;
        OnContactListener onContactListener;

        public MusicItemViewHolder(@NonNull View itemView, OnContactListener onContactListener) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            phone = itemView.findViewById(R.id.tv_phone);
            song = itemView.findViewById(R.id.tv_favorite_song);
            icon = itemView.findViewById(R.id.iv_icon);
            this.onContactListener = onContactListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onContactListener.onContactClick(getAdapterPosition());
        }
    }

    public interface OnContactListener {
        void onContactClick(int position);
    }
}
