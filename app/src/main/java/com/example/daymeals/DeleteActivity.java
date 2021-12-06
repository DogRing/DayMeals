package com.example.daymeals;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.daymeals.databinding.ActivityDeleteBinding;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private ActivityDeleteBinding binding;
    private LinearLayout deleteList;
    private DBHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        helper = new DBHelper(DeleteActivity.this,"myDB",null,1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        deleteList = binding.deleteList;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);

        Cursor cursor = db.rawQuery("select * from myMenu;",null);

        int count = 0;
        while(cursor.moveToNext()){
            LinearLayout item = new LinearLayout(getApplicationContext());
            item.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imageView = new ImageView(getApplicationContext());
            int imgResId;
            if(cursor.getString(4) != null) {
                imgResId = getResources().getIdentifier(cursor.getString(4),"drawable",getPackageName());
                imageView.setImageResource(imgResId);
            }
            else{
                imageView.setImageResource(R.drawable.chef);
            }
            item.addView(imageView,200,200);




            String iid = cursor.getString(0);
            String text = cursor.getString(1) + "\n메뉴 삭제";
            Button button = new Button(getApplicationContext());
            button.setId(count++);
            button.setText(text);
            button.setPadding(0,10,0,10);
            button.setLayoutParams(param);
            button.setWidth(deleteList.getWidth()-100);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    deleteDB(iid);
                    view.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                }
            });
            item.addView(button);
            deleteList.addView(item);
        }
        cursor.close();
    }
    private void deleteDB(String iid){
        db.execSQL("DELETE FROM vars WHERE id = " + iid + ";");
        db.execSQL("DELETE FROM myMenu WHERE id = " + iid +";");
    }
}