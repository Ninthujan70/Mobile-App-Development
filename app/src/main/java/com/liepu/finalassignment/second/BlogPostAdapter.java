package com.liepu.finalassignment.second;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liepu.finalassignment.R;
import com.liepu.finalassignment.second.model.Post;

import java.util.ArrayList;
import java.util.HashMap;

public class BlogPostAdapter extends ArrayAdapter<String> {

    ArrayList<HashMap<String, String>> postList;
    int [] usrAvt;
    Context context;

    public BlogPostAdapter(@NonNull Context context, ArrayList<HashMap<String, String>> postList, int[] usrAvt) {
        super(context, R.layout.f2_item_single_post);
        this.postList = postList;
        this.usrAvt = usrAvt;
        this.context = context;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.f2_item_single_post, parent, false);

            viewHolder.userAvt = convertView.findViewById(R.id.iv_image);
            viewHolder.title = convertView.findViewById(R.id.tv_title);
            viewHolder.body = convertView.findViewById(R.id.tv_body);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.userAvt.setImageResource(usrAvt[Integer.parseInt(postList.get(position).get(Post.USER_ID))]);
        viewHolder.title.setText(postList.get(position).get(Post.TITLE));
        viewHolder.body.setText(postList.get(position).get(Post.BODY));
        return convertView;
    }

    static class ViewHolder {
        ImageView userAvt;
        TextView title;
        TextView body;
    }


}
