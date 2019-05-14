package romain.com.multigames.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import romain.com.multigames.EndGameActivity;
import romain.com.multigames.MainActivity;
import romain.com.multigames.R;
import romain.com.multigames.utils.ActivityUtils;

public class IpacGameFragment extends Fragment {

    private static final int NUMBER_OF_TRY = 10;

    private TextView numberOfTryTv;
    private EditText numberEd;
    private TextView lessOrMore;
    private Button validate;

    private int numberToFind;
    private int numberOfTry = NUMBER_OF_TRY;

    private int score = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ipac_game, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numberOfTryTv = view.findViewById(R.id.ipac_game_main_number_try);
        numberEd = view.findViewById(R.id.ipac_game_main_number);
        lessOrMore = view.findViewById(R.id.ipac_game_main_more_or_less);
        validate = view.findViewById(R.id.ipac_game_btn_valide);

        numberOfTryTv.setText(getResources().getString(R.string.ipac_game_number_try, numberOfTry));
        numberToFind = (int)(Math.random() * 100);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!numberEd.getText().toString().isEmpty()){
                    int number = Integer.valueOf(numberEd.getText().toString());
                    if(number == numberToFind){
                        finishGame();
                    }else{
                        numberOfTry--;
                        if(numberOfTry == 0){
                            finishGame();
                        }
                        numberOfTryTv.setText(getResources().getString(R.string.ipac_game_number_try, numberOfTry));
                        numberEd.setText("");

                        lessOrMore.setText(getResources().getString(number < numberToFind ? R.string.ipac_game_more : R.string.ipac_game_less));
                        displayTextWithFade(lessOrMore);
                    }
                }else{
                    lessOrMore.setText(getResources().getString(R.string.ipac_game_number_enter));
                }

            }
        });
    }

    private void displayTextWithFade(final View view){
        view .setVisibility(View.VISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(IpacGameFragment.this.getActivity(), R.anim.fade_in));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideTextWithFade(view);
            }
        }, 1000);
    }

    private void hideTextWithFade(View view){
        view .setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(IpacGameFragment.this.getActivity(), R.anim.fade_out));
    }

    public void finishGame(){
        ((MainActivity) getActivity()).viewPager.setPagingEnabled(true);
        getFragmentManager().popBackStack();
        Intent intent = new Intent(IpacGameFragment.this.getActivity(), EndGameActivity.class);
        intent.putExtra("game", this.getArguments().getString("game"));
        this.score = numberOfTry;
        intent.putExtra("score", Integer.toString(this.score));
        ActivityUtils.launchActivity((AppCompatActivity) IpacGameFragment.this.getActivity(), intent, false);
    }
}
