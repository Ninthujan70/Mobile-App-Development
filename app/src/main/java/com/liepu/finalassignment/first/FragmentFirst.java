package com.liepu.finalassignment.first;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.liepu.finalassignment.R;

import java.io.IOException;
import java.io.InputStream;

public class FragmentFirst extends Fragment {

    ImageView bigPicture;
    Button setWallpaper;
    LinearLayout linearLayout;
    TextView myTextView;
    private ProgressDialog progressDialog;


    public int toPhone;

    View.OnClickListener listener, listener2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bigPicture = view.findViewById(R.id.iv_big_picture);
        setWallpaper = view.findViewById(R.id.btn_change_wallpaper);
        linearLayout = view.findViewById(R.id.ll_view);
        myTextView = view.findViewById(R.id.tv_changeable_text);

        setupImageList();

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaper();
            }
        };

        listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(new BottomSheetDialog.OnCalculationListener() {
                    @Override
                    public void onValidateClicked(boolean clicked) {
                        if (clicked) {
                            setWallpaper();
                            myTextView.setText("DONE");
                        } else {
                            Toast.makeText(getContext(), "Wrong answer", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();

                bottomSheetDialog.show(appCompatActivity.getSupportFragmentManager(), "TAG");
                bottomSheetDialog.setCancelable(false);
            }
        };

        setWallpaper.setOnClickListener(listener2);
    }

    private void setWallpaper() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        InputStream is = getResources().openRawResource(toPhone);
        Bitmap picture = BitmapFactory.decodeStream(is);
        WallpaperManager myWallpaper = WallpaperManager.getInstance(getContext());

        try {
            myWallpaper.setBitmap(picture);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void setupImageList() {
        for (int i = 1; i <= 13; i++) {
            final ImageView iv = new ImageView(getContext());
            String a = "spic" + Integer.toString(i);
            int id = getResources().getIdentifier(a, "drawable",
                    getContext().getPackageName());

            iv.setImageResource(id);
            iv.setId(i);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    500,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            lp.setMargins(10, 20, 20, 30);
            iv.setLayoutParams(lp);
            linearLayout.addView(iv);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ide = iv.getId();
                    String b = "pic"+Integer.toString(ide);

                    int id = getResources().getIdentifier(b, "drawable",
                            getContext().getPackageName());

                    bigPicture.setImageResource(id);
                    toPhone = id;
                }
            });
        }
    }
}
