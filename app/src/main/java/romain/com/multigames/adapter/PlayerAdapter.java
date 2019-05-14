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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import romain.com.multigames.MainActivity;
import romain.com.multigames.R;
import romain.com.multigames.model.Player;
import romain.com.multigames.utils.ActivityUtils;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private ArrayList<Player> players;
    private AppCompatActivity activity;


    public PlayerAdapter(ArrayList<Player> players, AppCompatActivity activity){
        this.players = players;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.activity_display_player_row, viewGroup, false);
        return new PlayerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Player player = players.get(position);
        viewHolder.firstName.setText(player.getFirstname());
        viewHolder.lastname.setText(player.getLastname());
        Picasso.get().load(player.getPicture()).into(viewHolder.image);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.launchActivity(activity, MainActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView lastname;
        private TextView firstName;
        private LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.players_row_image);
            lastname = itemView.findViewById(R.id.players_row_name);
            firstName = itemView.findViewById(R.id.players_row_firstname);
            linearLayout = itemView.findViewById(R.id.player_row_container);
        }

    }

}
