package romain.com.multigames.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import romain.com.multigames.EndGameActivity;
import romain.com.multigames.MainActivity;
import romain.com.multigames.R;
import romain.com.multigames.utils.ActivityUtils;
import romain.com.multigames.utils.OnSwipeTouchListener;
import romain.com.multigames.utils.TimerUtils;

public class FastTapPlayFragment extends Fragment {


    private TextView textViewTapOrLongtap;
    private TextView textViewScore;
    private int tapOrLongTap;
    private double swipeTRBL;
    private int score = 0;

    private TimerUtils timerUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fast_tap_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewTimer = view.findViewById(R.id.tv_timer_fast_tap);
        LinearLayout linearLayoutToTap = view.findViewById(R.id.fast_tap_play_linear_layout);
        textViewScore = view.findViewById(R.id.tv_fasttap_score);
        textViewScore.setText(String.valueOf(score));
        if(this.getArguments().getString("game").equals(getString(R.string.fast_tap_name))){
            tapOrLongTap = tapRandom(view);

            linearLayoutToTap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tapOrLongTap == 0){//Si tap
                        score++;
                        textViewScore.setText(String.valueOf(score));
                        tapOrLongTap = tapRandom(view);
                    }
                }
            });

            linearLayoutToTap.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(tapOrLongTap == 1){//Si longtap
                        score = score + 5;
                        textViewScore.setText(String.valueOf(score));
                        tapOrLongTap = tapRandom(view);
                    }
                    return true;
                }
            });
        }
        else{
            swipeTRBL = swipeRandom(view);
            linearLayoutToTap.setOnTouchListener(new OnSwipeTouchListener(FastTapPlayFragment.this.getContext()){
                public void onSwipeTop() {
                    System.out.println("top");
                    if(swipeTRBL < 0.25){
                        score ++;
                        textViewScore.setText(String.valueOf(score));
                        swipeTRBL = swipeRandom(view);
                    }
                }
                public void onSwipeRight() {
                    System.out.println("right");
                    if(swipeTRBL >= 0.25 && swipeTRBL < 0.5){
                        score ++;
                        textViewScore.setText(String.valueOf(score));
                        swipeTRBL = swipeRandom(view);
                    }
                }
                public void onSwipeLeft() {
                    System.out.println("left");
                    if(swipeTRBL >= 0.5 && swipeTRBL < 0.75){
                        score ++;
                        textViewScore.setText(String.valueOf(score));
                        swipeTRBL = swipeRandom(view);
                    }
                }
                public void onSwipeBottom() {
                    System.out.println("bot");
                    if(swipeTRBL >= 0.75){
                        score ++;
                        textViewScore.setText(String.valueOf(score));
                        swipeTRBL = swipeRandom(view);
                    }
                }
            });
        }


        timerUtils = new TimerUtils(textViewTimer) {
            @Override
            protected void endGame() {
                finishGame();
            }
        };
        timerUtils.startTimer();
    }

    public int tapRandom(View view){
        textViewTapOrLongtap = view.findViewById(R.id.tv_tap_or_longtap);
        int nbRandom = (int)Math.round(Math.random());
        if(nbRandom == 0){
            textViewTapOrLongtap.setText("tap");
        }else{
            textViewTapOrLongtap.setText("longtap");
        }
        return nbRandom;
    }

    public double swipeRandom(View view){
        textViewTapOrLongtap = view.findViewById(R.id.tv_tap_or_longtap);
        double nbRandom = Math.random();
        if(nbRandom < 0.25){
            textViewTapOrLongtap.setText("top");
        }else if(nbRandom < 0.5){
            textViewTapOrLongtap.setText("right");
        }
        else if(nbRandom < 0.75){
            textViewTapOrLongtap.setText("left");
        }
        else{
            textViewTapOrLongtap.setText("bot");
        }
        return nbRandom;
    }

    public void finishGame(){
        ((MainActivity) Objects.requireNonNull(getActivity())).viewPager.setPagingEnabled(true);
        getFragmentManager().popBackStack();
        Intent intent = new Intent(FastTapPlayFragment.this.getActivity(), EndGameActivity.class);
        intent.putExtra("game", this.getArguments().getString("game"));
        intent.putExtra("score", Integer.toString(this.score));
        ActivityUtils.launchActivity((AppCompatActivity) FastTapPlayFragment.this.getActivity(), intent, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerUtils.stopTimer();
    }
}
