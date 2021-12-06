package com.example.daymeals;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.daymeals.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyAdapter adapter;
    private ArrayList<ItemVO> itemList;
    private ActivityResultLauncher<Intent> resultLauncher;
    private int last = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent data = result.getData();
                            int[] deleted;
                            deleted = data.getExtras().getIntArray("returnData");
                            for(int i = 0 ; i< deleted.length;i++){
                                itemList.remove(deleted[i]);
                            }
                        }
                    }
                }
        );

        itemList = new ArrayList<>();
        addInList();
    }

    @Override
    protected void onResume(){
        super.onResume();

        adapter = new MyAdapter(this, itemList);
        binding.recycler.setAdapter(adapter);
    }

    public void insertMenu(View view){
        Intent intent = new Intent(this,InsertActivity.class);
        intent.putExtra("last",last);
        startActivity(intent);
    }

    public void deleteMenu(View view){
        Intent intent = new Intent(this,DeleteActivity.class);
        resultLauncher.launch(intent);
    }

    private void addInList(){
        DBHelper helper = new DBHelper(MainActivity.this,"myDB",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.onCreate(db);

        ItemVO itemVO = null;
        Cursor cursor = db.rawQuery("SELECT * FROM myMenu;",null);
        int id, imgResId;
        while(cursor.moveToNext()){
            id = Integer.parseInt(cursor.getString(0));
            if(cursor.getString(4) != null){
                imgResId = getResources().getIdentifier(cursor.getString(4),"drawable",getPackageName());
            }
            else{
                imgResId = R.drawable.cau;
            }
            itemVO = new ItemVO(id,imgResId,cursor.getString(1),cursor.getString(2),cursor.getString(3));
            itemList.add(itemVO);
        }
        last = itemVO.id;
    }
}