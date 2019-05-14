package romain.com.multigames.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import romain.com.multigames.MainActivity;
import romain.com.multigames.R;
import romain.com.multigames.utils.ActivityUtils;

public class BeginGameFragment extends Fragment {

    private Button buttonPlay;
    private TextView tvGameName;
    private String gameName;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_begin_game, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGameName = view.findViewById(R.id.textview_game_name);
        tvGameName.setText(this.getArguments().getString("name"));

        buttonPlay = view.findViewById(R.id.btn_play_game);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameName = BeginGameFragment.this.getArguments().getString("name");
                ((MainActivity) getActivity()).viewPager.setPagingEnabled(false);
                if(gameName.equals(getString(R.string.fast_tap_name)) || gameName.equals(getString(R.string.swipe_name))){
                    FastTapPlayFragment fragment = new FastTapPlayFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("game", gameName);
                    fragment.setArguments(bundle);
                    ActivityUtils.addFragmentToActivity(BeginGameFragment.this, fragment, R.id.begin_linear_layout, gameName);
                }else if(gameName.equals(getString(R.string.drag_n_drop_name))){
                    DragNDropFragment fragment = new DragNDropFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("game", gameName);
                    fragment.setArguments(bundle);
                    ActivityUtils.addFragmentToActivity(BeginGameFragment.this, fragment, R.id.begin_linear_layout, gameName);
                }
            }
        });
    }
}
