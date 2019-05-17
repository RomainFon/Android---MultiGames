package romain.com.multigames;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmQuery;
import romain.com.multigames.manager.PlayerManager;
import romain.com.multigames.model.Player;
import romain.com.multigames.utils.ActivityUtils;

/**
 * Created by romai on 26/03/2019.
 */

public class CreatePlayerActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_PICTURE = 1001;
    private static final int REQUEST_LOCATION = 1002;

    private Button showPlayersButton;
    private Button validateButton;

    private EditText firstNamePlayer;
    private EditText lastNamePlayer;
    private EditText agePlayer;
    private EditText locationPlayerET;

    private ImageView locationPlayer;
    private ImageView imageView;

    private String firstName;
    private String lastName;
    private int age;
    private String location;
    private String picture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        firstNamePlayer = findViewById(R.id.firstname_player);
        lastNamePlayer = findViewById(R.id.lastname_player);
        locationPlayerET = findViewById(R.id.location_player);
        agePlayer = findViewById(R.id.old_player);

        showPlayersButton = findViewById(R.id.btn_show_player);

        showPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayViewShowPlayer();
            }
        });

        imageView = findViewById(R.id.image_player);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(Intent.createChooser(intent, "Choix de la photo"), REQUEST_PICK_PICTURE);
            }
        });

        locationPlayer = findViewById(R.id.image_location_player);

        locationPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreatePlayerActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    getUserLocation();
                }
            }
        });

        validateButton = findViewById(R.id.btn_validate_create_player);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstNamePlayer.getText().toString();
                lastName = lastNamePlayer.getText().toString();
                //
                location = locationPlayerET.getText().toString();

                if(firstName.isEmpty() || lastName.isEmpty() || agePlayer.getText().toString().isEmpty() || location.isEmpty()){
                    Toast.makeText(CreatePlayerActivity.this, "Informations manquantes !", Toast.LENGTH_SHORT).show();
                }else{
                    age = Integer.parseInt(agePlayer.getText().toString());
                    Player player = new Player(lastName, firstName, age, location, picture);
                    addPlayerInDb(player);
                    PlayerManager.getInstance().setPlayer(player);
                    Toast.makeText(CreatePlayerActivity.this, "Player créer !", Toast.LENGTH_SHORT).show();
                    ActivityUtils.launchActivity(CreatePlayerActivity.this, MainActivity.class);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_PICTURE && resultCode == RESULT_OK) {
            Picasso.get().load(data.getData().toString()).into(imageView);
            picture = data.getData().toString();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            }
        }

    }

    private void displayViewShowPlayer() {
        ActivityUtils.launchActivity(this, ShowPlayersActivity.class, false);
    }

    @SuppressLint("MissingPermission")
    private void getUserLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestSingleUpdate(new Criteria(), new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationPlayerET.setText(location.getLatitude() + ":" + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        }, null);
    }

    private void addPlayerInDb(Player player){
        Realm mRealmInstance = Realm.getDefaultInstance();
        mRealmInstance.beginTransaction();
        try{
            mRealmInstance.copyToRealmOrUpdate(player);
            mRealmInstance.commitTransaction();
        }catch(Exception e){
            String a = "";
        }
    }
}
