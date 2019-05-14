package romain.com.multigames;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import romain.com.multigames.adapter.PlayerAdapter;
import romain.com.multigames.model.Player;

/**
 * Created by romai on 26/03/2019.
 */

public class ShowPlayersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_players);

        recyclerView = findViewById(R.id.recycler_view_players);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlayerAdapter(getAllPlayers(), this));
    }

    private ArrayList<Player> getAllPlayers(){
        Realm mRealmInstance = Realm.getDefaultInstance();
        RealmQuery query = mRealmInstance.where(Player.class);
        ArrayList<Player> players = new ArrayList<Player>(query.findAll());
        return new ArrayList<Player>(query.findAll());
    }
}
