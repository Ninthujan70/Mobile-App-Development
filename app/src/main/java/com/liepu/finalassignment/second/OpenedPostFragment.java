package com.liepu.finalassignment.second;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liepu.finalassignment.R;
import com.liepu.finalassignment.second.model.Post;

public class OpenedPostFragment extends Fragment {

    Boolean isTouch = false;
    private final int maxLines = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f2_fragment_opened, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ImageButton showMore = view.findViewById(R.id.btn_show_more);

            String title = bundle.getString(Post.TITLE);
            String body = bundle.getString(Post.BODY);
            String id = bundle.getString(Post.ID);
            String userId = bundle.getString(Post.USER_ID);
            int avt = bundle.getInt("avt", 0);

            TextView openedTitle = view.findViewById(R.id.tv_post_header_text);
            TextView openedBody = view.findViewById(R.id.tv_opened_body);
            TextView idPost = view.findViewById(R.id.tv_post_id);
            TextView userIdPost = view.findViewById(R.id.tv_post_userId);
            ImageView userAvt = view.findViewById(R.id.iv_user_avt);

            openedTitle.setText(title);
            openedBody.setText(body);
            idPost.setText("Identification: " +id);
            userIdPost.setText("User Id: " +userId);
            openedBody.setMaxLines(maxLines);
            userAvt.setImageResource(avt);

            showMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isTouch) {
                        isTouch = true;
                        expandTextView(openedBody);
//                    openedBody.setMaxLines(Integer.MAX_VALUE);
                        idPost.setVisibility(View.VISIBLE);
                        userIdPost.setVisibility(View.VISIBLE);
                        showMore.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    }
                    else {
                        isTouch = false;
                        collapseTextView(openedBody, maxLines);
//                    openedBody.setMaxLines(maxLines);
                        idPost.setVisibility(View.GONE);
                        userIdPost.setVisibility(View.GONE);
                        showMore.setImageResource(R.drawable.baseline_expand_more_24);
                    }

                }
            });
        }
    }

    private void expandTextView(TextView tv){
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getLineCount());
        animation.setDuration(100).start();
    }

    private void collapseTextView(TextView tv, int numLines){
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", numLines);
        animation.setDuration(200).start();
    }
}
