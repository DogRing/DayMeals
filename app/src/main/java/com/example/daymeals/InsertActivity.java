package com.example.daymeals;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.daymeals.databinding.ActivityInsertBinding;

public class InsertActivity extends AppCompatActivity {

    private ActivityInsertBinding binding;
    private LinearLayout varList;
    private int count;
    private DBHelper helper;
    private SQLiteDatabase db;
    private int last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        count = 1;
        varList = binding.varList;

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        last = extras.getInt("last")+1;

        helper = new DBHelper(InsertActivity.this,"myDB",null,1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

    }

    public void addList(View view){
        EditText matText = new EditText(getApplicationContext());
        matText.setHint("다른 재료들");
        matText.setWidth(matText.getMaxWidth());
        matText.setId(count);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        matText.setLayoutParams(param);
        varList.addView(matText);
        count++;

        EditText linkText = new EditText(getApplicationContext());
        linkText.setHint("YouTube 링크");
        matText.setWidth(matText.getMaxWidth());
        linkText.setId(count);
        linkText.setLayoutParams(param);
        varList.addView(linkText);
        count++;
    }

    public void insertDB(View view){
        db.execSQL("INSERT INTO myMenu (name, mats,detail) VALUES ('"
                + binding.insertName.getText() + "','"
                + binding.insertMat.getText() + "','"
                + binding.insertDetail.getText() + "');");
        EditText current;
        for(int i = 1; i<=count/2;i++){
            String sql = "INSERT INTO vars (id, vid, mats, link) VALUES ("+last+","+i+",'";
            current = (EditText) findViewById(i*2-1);
            sql += current.getText().toString() + "','";
            current = (EditText) findViewById(i*2);
            sql += current.getText().toString() + "');";
            db.execSQL(sql);
        }
        finish();
    }
}