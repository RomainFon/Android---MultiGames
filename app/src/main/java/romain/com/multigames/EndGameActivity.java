package romain.com.multigames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.realm.Realm;
import romain.com.multigames.adapter.SettingsAdapter;
import romain.com.multigames.fragment.SettingsFragment;
import romain.com.multigames.manager.PlayerManager;
import romain.com.multigames.model.Player;
import romain.com.multigames.utils.ActivityUtils;

public class EndGameActivity extends AppCompatActivity {

    private TextView gameName;
    private TextView gameScore;
    private Button btnMenu;
    private Button btnLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        gameName = findViewById(R.id.activity_end_game);
        gameScore = findViewById(R.id.activity_end_score);

        Intent intent = getIntent();
        gameName.setText(intent.getStringExtra("game"));
        gameScore.setText(intent.getStringExtra("score"));

        Player player = PlayerManager.getInstance().getPlayer();

        Realm mRealmInstance = Realm.getDefaultInstance();
        mRealmInstance.beginTransaction();

        int scoreMax;
        int currentScore = Integer.parseInt(intent.getStringExtra("score"));
        switch (intent.getStringExtra("game")) {
            case "Swipe":
                scoreMax = player.getScoreSwipe();
                if(scoreMax < currentScore){
                    player.setScoreSwipe(currentScore);
                }
                break;
            case "Drag N Drop":
                scoreMax = player.getScoreSwipe();
                if(scoreMax < currentScore){
                    player.setScoreDragNDrop(currentScore);
                }
                break;
            case "MDS Game":
                scoreMax = player.getScoreSwipe();
                if(scoreMax < currentScore){
                    player.setScoreIpacGame(currentScore);
                }
                break;
            case "FastTap":
                scoreMax = player.getScoreSwipe();
                if(scoreMax < currentScore){
                    player.setScoreFastTap(currentScore);
                }
                break;
        }

        try{
            mRealmInstance.copyToRealmOrUpdate(player);
            mRealmInstance.commitTransaction();
        }catch(Exception e){
            String a = "";
        }

        btnMenu = findViewById(R.id.btn_menu_game);
        btnLeave = findViewById(R.id.btn_leave_game);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
