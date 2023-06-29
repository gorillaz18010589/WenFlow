package com.wen.flow;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;

import com.wen.flow.databinding.FragmentStartBinding;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.rx.RxTimer;
import com.wen.flow.ui.dash.DashActivity;


public class StartFragment extends BaseFragment<FragmentStartBinding> {
//    private LogoViewModel logoViewModel;
    private MediaPlayer mMediaPlayer;
    private NavController navController;
    private String TAG = getClass().getSimpleName() + " ->";
    private int videoViewDuration = 1500;
    private RxTimer checkTimer = new RxTimer();
    private boolean isConnected = false;
    private int fragment_id;
    boolean isDone = false;
    public boolean isNext = false;
    private Dialog errorDialog2;


    @Override
    public void onPause() {
        super.onPause();
        binding.videoView.pause();
//        LogUtils.d(TAG + "onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) mMediaPlayer.release();
        ;
//        LogUtils.d(TAG + "onDestroy()");
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_start;
    }


    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        setVideoView();
    }

    @Override
    protected void initListeners() {

    }

    private void setVideoView() {
        Uri uri = Uri.parse("android.resource://"
                + mActivity.getPackageName()
                + "/"
                + R.raw.logo);

        binding.videoView.setVideoURI(uri);
        binding.videoView.start();
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                binding.videoView.setBackgroundColor(Color.TRANSPARENT);
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);
                startDashActivity(videoViewDuration);
            }
        });
    }

    private void startDashActivity(int videoViewDuration){
        new RxTimer().timer(videoViewDuration, action -> {
            startActivity(new Intent(getActivity(), DashActivity.class));
        });
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