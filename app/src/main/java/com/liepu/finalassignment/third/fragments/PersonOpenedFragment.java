package com.liepu.finalassignment.third.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liepu.finalassignment.R;
import com.liepu.finalassignment.third.models.Artist;
import com.liepu.finalassignment.third.models.Person;
import com.liepu.finalassignment.third.models.Song;

import java.util.ArrayList;
import java.util.Objects;

public class PersonOpenedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.f3_fragment_person_opened, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Person person = getArguments().getParcelable("person");

            ImageView ivAvt = view.findViewById(R.id.iv_avt);

            if (Objects.equals(person.getGender(), "male")) {
                ivAvt.setImageResource(R.drawable.person);
            } else {
                ivAvt.setImageResource(R.drawable.girl);
            }

            TextView openedName = view.findViewById(R.id.tv_opened_name);
            openedName.setText(person.getName());

            TextView openedPhone = view.findViewById(R.id.tv_opened_phone);
            openedPhone.setText(person.getPhoneList().getHome());

            TextView openedBody = view.findViewById(R.id.tv_opened_body);
            openedBody.setText(String.format("Address: %s \n" +
                    "Gender: %s \n" +
                    "Mobile number: %s \n" +
                    "Office number: %s", person.getAddress(), person.getGender(), person.getPhoneList().getMobile(), person.getPhoneList().getOffice()));

            ArtistAdapter artistAdapter = new ArtistAdapter(person.getArtistPlaylist());
            RecyclerView rvArtist = view.findViewById(R.id.rv_artist);
            rvArtist.setLayoutManager(new LinearLayoutManager(
                    getContext(),
                    RecyclerView.HORIZONTAL,
                    false
            ));
            rvArtist.setAdapter(artistAdapter);

            SongAdapter songAdapter = new SongAdapter(person.getSongPlaylist());
            RecyclerView rvSong = view.findViewById(R.id.rv_song);
            rvSong.setLayoutManager(new LinearLayoutManager(
                    getContext(),
                    RecyclerView.HORIZONTAL,
                    false
            ));
            rvSong.setAdapter(songAdapter);
        }
    }

    class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistRV> {
        ArrayList<Artist> data;

        public ArtistAdapter(ArrayList<Artist> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ArtistRV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_rv, parent, false);
            return new ArtistRV(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ArtistRV holder, int position) {
            String url = data.get(position).getFileUrl();
            int id = getResources().getIdentifier(url, "drawable",
                    getActivity().getPackageName());

            if (id != 0) {
                holder.ivCover.setImageResource(id);
            } else {
                holder.tvCover.setText(data.get(position).getName());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ArtistRV extends RecyclerView.ViewHolder{
            TextView tvCover;
            ImageView ivCover;
            public ArtistRV(@NonNull View itemView) {
                super(itemView);
                tvCover = itemView.findViewById(R.id.tv_cover);
                ivCover = itemView.findViewById(R.id.iv_cover);
            }
        }
    }

    class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongRV> {
        ArrayList<Song> data;

        public SongAdapter(ArrayList<Song> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public SongRV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_rv, parent, false);
            return new SongRV(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongRV holder, int position) {
            String url = data.get(position).getFileUrl();
            int id = getResources().getIdentifier(url, "drawable",
                    getActivity().getPackageName());

            if (id != 0) {
                holder.ivCover.setImageResource(id);
            } else {
                holder.ivCover.setImageResource(R.drawable.ic_baseline_music_note_64);
                holder.tvCover.setText(data.get(position).getName());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class SongRV extends RecyclerView.ViewHolder {
            TextView tvCover;
            ImageView ivCover;
            public SongRV(@NonNull View itemView) {
                super(itemView);
                tvCover = itemView.findViewById(R.id.tv_cover);
                ivCover = itemView.findViewById(R.id.iv_cover);
            }
        }
    }
}
