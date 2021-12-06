package com.example.daymeals;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daymeals.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    private Context context;
    private ArrayList<ItemVO> items;

    public MyAdapter(Context context, ArrayList<ItemVO> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.VH holder, int position){
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{
        ItemVO item;
        RecyclerItemBinding binding;

        public VH(@NonNull View itemView){
            super(itemView);
            binding = RecyclerItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context.getApplicationContext(),MenuDetailActivity.class);
                    intent.putExtra("menuId",item);
                    context.startActivity(intent);
                }
            });
        }
        void bindItem(ItemVO item){
            this.item = item;
            binding.menuImage.setImageResource(item.imgResId);
            binding.menuTitle.setText(item.name);
            binding.menuMats.setText(item.mats);
            binding.menuDetails.setText(item.detail);
        }
    }
}
