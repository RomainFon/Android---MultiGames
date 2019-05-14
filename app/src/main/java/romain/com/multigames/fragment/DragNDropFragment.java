package romain.com.multigames.fragment;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import romain.com.multigames.EndGameActivity;
import romain.com.multigames.MainActivity;
import romain.com.multigames.R;
import romain.com.multigames.utils.ActivityUtils;
import romain.com.multigames.utils.TimerUtils;

public class DragNDropFragment extends Fragment {

    private int score = 0;
    private View dnd_zone;
    private TextView shapeDrag;
    private View generalView;
    private GradientDrawable backgroundGradient;
    private TextView textViewScore;
    private RelativeLayout randomZone;

    private TimerUtils timerUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drag_n_drop, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generalView = view;

        randomZone = view.findViewById(R.id.dnd_random_zone);

        TextView textViewTimer = view.findViewById(R.id.tv_timer_drag_n_drop);
        textViewScore = view.findViewById(R.id.tv_drag_n_drop_score);
        textViewScore.setText(String.valueOf(score));

        shapeDrag = view.findViewById(R.id.shape_drag_n_drop);
        //randomPositionCircle(shapeDrag);

        shapeDrag.setTag("TextView");
        shapeDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String tag = (String)v.getTag();
                ClipData clipData = ClipData.newPlainText("", tag);
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(clipData, dragShadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        backgroundGradient = (GradientDrawable)shapeDrag.getBackground();
        randomColor(view, backgroundGradient);

        timerUtils = new TimerUtils(textViewTimer) {
            @Override
            protected void endGame() {
                finishGame();
            }
        };
        timerUtils.startTimer();
    }

    public void randomColor(View view, GradientDrawable backgroundGradient){
        int nbRandom = (int)(Math.random() * 5); //nb entre 0 et 5
        switch (nbRandom) {
            case 0:
                DragNDropFragment.this.dnd_zone = view.findViewById(R.id.dnd_zone_red);
                backgroundGradient.setColor(getResources().getColor(R.color.colorDndRed));
                break;
            case 1:
                DragNDropFragment.this.dnd_zone = view.findViewById(R.id.dnd_zone_blue);
                backgroundGradient.setColor(getResources().getColor(R.color.colorDndBlue));
                break;
            case 2:
                DragNDropFragment.this.dnd_zone = view.findViewById(R.id.dnd_zone_green);
                backgroundGradient.setColor(getResources().getColor(R.color.colorDndGreen));
                break;
            case 3:
                DragNDropFragment.this.dnd_zone = view.findViewById(R.id.dnd_zone_orange);
                backgroundGradient.setColor(getResources().getColor(R.color.colorDndOrange));
                break;
            case 4:
                DragNDropFragment.this.dnd_zone = view.findViewById(R.id.dnd_zone_yellow);
                backgroundGradient.setColor(getResources().getColor(R.color.colorDndYellow));
                break;
            case 5:
                DragNDropFragment.this.dnd_zone = view.findViewById(R.id.dnd_zone_pink);
                backgroundGradient.setColor(getResources().getColor(R.color.colorDndPink));
                break;
        }

        DragNDropFragment.this.dnd_zone.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragAction = event.getAction();
                if(dragAction == event.ACTION_DRAG_STARTED) {
                    return true;
                }else if(dragAction == event.ACTION_DROP){
                    randomColor(DragNDropFragment.this.generalView, DragNDropFragment.this.backgroundGradient);
                    randomPositionCircle(shapeDrag);
                    score ++;
                    textViewScore.setText(String.valueOf(score));
                    shapeDrag.setVisibility(View.VISIBLE);
                    v.setOnDragListener(null);
                    return true;
                }else if(dragAction == event.ACTION_DRAG_ENDED){
                    shapeDrag.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

    public void finishGame(){
        ((MainActivity) getActivity()).viewPager.setPagingEnabled(true);
        getFragmentManager().popBackStack();
        Intent intent = new Intent(DragNDropFragment.this.getActivity(), EndGameActivity.class);
        intent.putExtra("game", this.getArguments().getString("game"));
        intent.putExtra("score", Integer.toString(this.score));
        ActivityUtils.launchActivity((AppCompatActivity) DragNDropFragment.this.getActivity(), intent, false);
    }

    public void randomPositionCircle(TextView shapeDrag){
        int width = randomZone.getWidth() - shapeDrag.getWidth();
        int height = randomZone.getHeight() - shapeDrag.getHeight();
        float widthRandom = (int)(Math.random() * width);
        float heightRandom = (int)(Math.random() * height);
        shapeDrag.setX(widthRandom);
        shapeDrag.setY(heightRandom);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerUtils.stopTimer();
    }
}
