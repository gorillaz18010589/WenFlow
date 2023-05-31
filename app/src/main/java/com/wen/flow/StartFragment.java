package com.wen.flow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wen.flow.databinding.FragmentStartBinding;
import com.wen.flow.support.base.BaseFragment;


public class StartFragment extends BaseFragment<FragmentStartBinding> {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_start;
    }


    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

}




//public class StartFragment extends Fragment {
//    FragmentStartBinding fragmentStartBinding;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View root = inflater.inflate(R.layout.fragment_start,container,false);
//        fragmentStartBinding = DataBindingUtil.bind(root);
//        return fragmentStartBinding.getRoot();
//    }
//}