package com.example.daymeals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.daymeals.databinding.MyFragmentBinding;

public class MyFragment extends Fragment {

    private MyFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = MyFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        binding.button.setOnClickListener(v-> binding.textview.setText("감사합니다. "));
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
