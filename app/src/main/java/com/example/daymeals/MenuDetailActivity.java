package com.example.daymeals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daymeals.databinding.ActivityMenuDetailBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MenuDetailActivity extends AppCompatActivity {

    private ActivityMenuDetailBinding binding;
    LinearLayout listView;
    ItemVO item;

    private DBHelper helper;
    private SQLiteDatabase db;
    LinearLayout.LayoutParams param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        item = extras.getParcelable("menuId");
        listView = binding.varDetails;
        param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);

        helper = new DBHelper(MenuDetailActivity.this,"myDB",null,1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        fillViews();
    }

    @SuppressLint("ResourceAsColor")
    private void fillViews(){
        Cursor cursor = db.rawQuery("SELECT * FROM vars WHERE id = " + item.id,null);
        binding.detailTitle.setText(item.name);
        binding.detail.setText(item.detail);
        binding.detailImage.setImageResource(item.imgResId);

        int count = 0;
        while (cursor.moveToNext()){
            TextView blank = new TextView(getApplicationContext());
            blank.setTextSize(20);
            blank.setId(count++);
            blank.setBackgroundColor(R.color.mine);
            param.leftMargin = 30;
            blank.setLayoutParams(param);
            listView.addView(blank);

            Button linkView = new Button(getApplicationContext());
            String Link = "https://www.youtube.com/watch?v="+cursor.getString(3);
            linkView.setText("유튜브에서 보기");
            linkView.setTextSize(20);
            linkView.setId(count++);
            linkView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Link));
                    startActivity(intent);
                }
            });
            param.leftMargin = 30;
            linkView.setLayoutParams(param);
            listView.addView(linkView);

            TextView textView = new TextView(getApplicationContext());
            textView.setText("메인 재료 : "+cursor.getString(2));
            textView.setTextSize(20);
            textView.setId(count++);
            textView.setTextColor(0xFF000000);
            param.leftMargin = 30;
            textView.setLayoutParams(param);
            listView.addView(textView);


        }

    }

}