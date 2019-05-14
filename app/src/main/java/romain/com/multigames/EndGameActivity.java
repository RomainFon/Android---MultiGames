package romain.com.multigames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
