package romain.com.multigames.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import romain.com.multigames.R;
import romain.com.multigames.manager.PlayerManager;
import romain.com.multigames.model.Player;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private ArrayList<String> games;

    public SettingsAdapter(ArrayList<String> players){
        this.games = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_setting_game_row, viewGroup, false);
        return new SettingsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.settingGameName.setText(this.games.get(position));
        PlayerManager.getInstance().getPlayer().getScoreFastTap();
        switch (position) {
            case 0:
                viewHolder.settingsGameScore.setText(Integer.toString(PlayerManager.getInstance().getPlayer().getScoreDragNDrop()));
                break;
            case 1:
                viewHolder.settingsGameScore.setText(Integer.toString(PlayerManager.getInstance().getPlayer().getScoreSwipe()));
                break;
            case 2:
                viewHolder.settingsGameScore.setText(Integer.toString(PlayerManager.getInstance().getPlayer().getScoreFastTap()));
                break;
            case 3:
                viewHolder.settingsGameScore.setText(Integer.toString(PlayerManager.getInstance().getPlayer().getScoreIpacGame()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView settingsGameBest;
        private TextView settingGameName;
        private TextView settingsGameScore;

        ViewHolder(View itemView) {
            super(itemView);
            settingsGameBest = itemView.findViewById(R.id.settings_game_best);
            settingGameName = itemView.findViewById(R.id.settings_game_name);
            settingsGameScore = itemView.findViewById(R.id.settings_game_score);
        }
    }
}
