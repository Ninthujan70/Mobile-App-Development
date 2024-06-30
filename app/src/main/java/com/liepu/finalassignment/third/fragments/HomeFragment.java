package com.liepu.finalassignment.third.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liepu.finalassignment.R;
import com.liepu.finalassignment.ServiceHandler;
import com.liepu.finalassignment.third.FragmentThird;
import com.liepu.finalassignment.third.MusicListAdapter;
import com.liepu.finalassignment.third.models.Artist;
import com.liepu.finalassignment.third.models.Person;
import com.liepu.finalassignment.third.models.Phone;
import com.liepu.finalassignment.third.models.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    View view;
    private ProgressDialog progressDialog;
    MusicListAdapter adapter;
    RecyclerView list;
    ArrayList<Person> personArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f3_fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new GetPersonData().execute();
    }

    public ArrayList<Person> getPersonArrayList() {
        return personArrayList;
    }

    private class GetPersonData extends AsyncTask<Void, Void, Void> implements MusicListAdapter.OnContactListener {

        private String URL;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            list = requireView().findViewById(R.id.rv_list);
            adapter = new MusicListAdapter(getPersonArrayList(), this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    getContext(),
                    RecyclerView.VERTICAL,
                    false
            );
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandler sh = new ServiceHandler(URL);
            String jsonString = sh.makeServiceCall(FragmentThird.URL);
            if (jsonString != null) {
                jsonString = jsonString.replace("<pre>", "");
                jsonString = jsonString.replace("</pre>", "");
                Log.d("NEW DATA", jsonString);

                personArrayList = new ArrayList<>();
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(jsonString);
                    JSONArray contacts = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        ArrayList<Song> songs = new ArrayList<>();
                        ArrayList<Artist> artists = new ArrayList<>();
                        JSONArray songJson = c.getJSONObject("song_play_list").getJSONArray("song");
                        JSONArray artistJson = c.getJSONObject("song_play_list").getJSONArray("artists");
                        for (int j = 0; j < songJson.length(); j++) {
                            songs.add(new Song(songJson.getString(j), songJson.getString(j).replaceAll("\\s+", "").toLowerCase()));
                        }
                        for (int j = 0; j < artistJson.length(); j++) {
                            artists.add(new Artist(artistJson.getString(j), artistJson.getString(j).replaceAll("\\s+", "").toLowerCase()));
                        }

                        Phone phone = new Phone(c.getJSONObject("phone").getString("mobile"),
                                c.getJSONObject("phone").getString("home"),
                                c.getJSONObject("phone").getString("office"));

                        personArrayList.add(new Person(
                                id,
                                name,
                                address,
                                gender,
                                songs,
                                artists,
                                phone
                        ));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        public void onContactClick(int position) {
//            Log.d(TAG, "onContactClick: clicked." + position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("person", personArrayList.get(position));
            PersonOpenedFragment personOpenedFragment = new PersonOpenedFragment();
            personOpenedFragment.setArguments(bundle);

            AppCompatActivity appCompatActivity = (AppCompatActivity) requireView().getContext();

            FragmentTransaction transaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.fragment_container, personOpenedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
