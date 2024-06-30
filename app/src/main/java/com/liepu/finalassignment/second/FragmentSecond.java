package com.liepu.finalassignment.second;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.liepu.finalassignment.R;
import com.liepu.finalassignment.ServiceHandler;
import com.liepu.finalassignment.second.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentSecond extends ListFragment {

    private ProgressDialog progressDialog;
    String url = "https://jsonplaceholder.typicode.com/posts";
    JSONArray blogPosts = null;
    ArrayList<HashMap<String, String>> blogPostList;

    int[] userAvt = {
            R.drawable.ic_baseline_face_24,
            R.drawable.ic_baseline_face_2_24,
            R.drawable.ic_baseline_face_3_24,
            R.drawable.ic_baseline_face_4_24,
            R.drawable.ic_baseline_face_5_24,
            R.drawable.ic_baseline_face_6_24,
            R.drawable.ic_outline_face_24,
            R.drawable.ic_outline_face_2_24,
            R.drawable.ic_outline_face_3_24,
            R.drawable.ic_outline_face_4_24,
            R.drawable.ic_outline_face_5_24,
            R.drawable.ic_outline_face_6_24
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        blogPostList = new ArrayList<>();

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(Post.ID, blogPostList.get(i).get(Post.ID));
                bundle.putString(Post.USER_ID, blogPostList.get(i).get(Post.USER_ID));
                bundle.putString(Post.TITLE, blogPostList.get(i).get(Post.TITLE));
                bundle.putString(Post.BODY, blogPostList.get(i).get(Post.BODY));
                bundle.putInt("avt", userAvt[Integer.parseInt(blogPostList.get(i).get(Post.USER_ID))]);

                OpenedPostFragment fragment = new OpenedPostFragment();
                fragment.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();

                FragmentTransaction transaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        new GetPostList().execute();
    }

    private class GetPostList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Downloading Data... Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            ListAdapter listAdapter = new BlogPostAdapter(getContext(), blogPostList, userAvt);
            setListAdapter(listAdapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandler sh = new ServiceHandler(url);
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    blogPosts = new JSONArray(jsonStr);
                    for (int i = 0; i < blogPosts.length(); i++) {
                        JSONObject b = blogPosts.getJSONObject(i);

                        HashMap<String, String> oneBlogPost = new HashMap<>();
                        oneBlogPost.put(Post.ID, b.getString(Post.ID));
                        oneBlogPost.put(Post.USER_ID, b.getString(Post.USER_ID));
                        oneBlogPost.put(Post.TITLE, b.getString(Post.TITLE));
                        oneBlogPost.put(Post.BODY, b.getString(Post.BODY));

                        blogPostList.add(oneBlogPost);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
