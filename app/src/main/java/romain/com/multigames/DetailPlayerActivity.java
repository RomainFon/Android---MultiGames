package romain.com.multigames;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import romain.com.multigames.manager.PlayerManager;
import romain.com.multigames.model.Player;
import romain.com.multigames.utils.ActivityUtils;

public class DetailPlayerActivity extends AppCompatActivity {

    private Player player = PlayerManager.getInstance().getPlayer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_player);

        TextView firstnamePlayerDetail = findViewById(R.id.firstname_player_detail);
        firstnamePlayerDetail.setText(player.getFirstname());
        TextView lastnamePlayerDetail = findViewById(R.id.lastname_player_detail);
        lastnamePlayerDetail.setText(player.getLastname());
        TextView agePlayerDetail = findViewById(R.id.age_player_detail);
        agePlayerDetail.setText(player.getAge()+" ans");
        TextView locationPlayerDetail = findViewById(R.id.location_player_detail);
        locationPlayerDetail.setText(player.getLocation());

        TextView swipePlayerDetail = findViewById(R.id.swipe_player_detail);
        swipePlayerDetail.setText("Swipe: "+player.getScoreSwipe());
        TextView dragPlayerDetail = findViewById(R.id.dragndrop_player_detail);
        dragPlayerDetail.setText("DragNDrop: "+player.getScoreDragNDrop());
        TextView ipacPlayerDetail = findViewById(R.id.ipacgame_player_detail);
        ipacPlayerDetail.setText("Ipac Game: "+player.getScoreIpacGame());
        TextView fastTapPlayerDetail = findViewById(R.id.fasttap_player_detail);
        fastTapPlayerDetail.setText("FastTap: "+player.getScoreFastTap());

        System.out.println(player);

        Button btnDeletePlayer = findViewById(R.id.btn_delete_player);
        btnDeletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm mRealmInstance = Realm.getDefaultInstance();
                mRealmInstance.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Player> result = realm.where(Player.class)
                                .equalTo("lastname",player.getLastname())
                                .equalTo("firstname",player.getFirstname())
                                .findAll();
                        result.deleteAllFromRealm();
                    }
                });
            Toast.makeText(DetailPlayerActivity.this, "Le joueur a bien été supprimer.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailPlayerActivity.this, ShowPlayersActivity.class);
            ActivityUtils.launchActivity(DetailPlayerActivity.this, intent);
            }
        });
    }
}
