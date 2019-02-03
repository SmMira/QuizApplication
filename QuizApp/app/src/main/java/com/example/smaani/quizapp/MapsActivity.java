package com.example.smaani.quizapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private static final String TAG = "TAG";
    private GoogleMap mMap;
    VerifyData db;
    TextView tvQuestionPays, tvScoreMap;
    Button buttonTest, buttonNew, buttonValider;
    Random randLigne = new Random();
    String question[] = new String[]{"Où se situe le pays : "};
    int min=0, max=question.length-1, minLigne = 1, maxLigne= 39;
    int diffLigne = maxLigne - minLigne;
    int iLigne, score=0;
    String lieu ="", paysClickedOn="";
    Geocoder geocoder, geo;
    List<Address> addresses, addresse;
    double latitud, longitud, latClickedOn, longClickedOn;
    boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = new VerifyData(this);
        try {
            db.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
        tvQuestionPays = findViewById(R.id.TvQuestion);
        tvScoreMap = findViewById(R.id.TvScoreMap);
        buttonTest = findViewById(R.id.ButtonTest);
        buttonNew = findViewById(R.id.ButtonNew);
        buttonValider = findViewById(R.id.ButtonValider);
        tvScoreMap.setText(score+"");

        trouverPays();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * la fonction regroupe l'ensemble des instruction qui permette de trouver un pays ou une ville de maniere aléatoire a partir de la base de données
     */
    private void trouverPays(){
        bool=false;
        buttonValider.setEnabled(false);
        buttonNew.setEnabled(false);
        geocoder = new Geocoder(this);
        iLigne = randLigne.nextInt(diffLigne + 1);
        iLigne+=minLigne;
        getData(iLigne);
        tvQuestionPays.setText("Où se situe "+getData(iLigne));
        try {
            addresses = geocoder.getFromLocationName(getData(iLigne), 1);
            if (addresses.size() > 0) {
                latitud=addresses.get(0).getLatitude();
                longitud=addresses.get(0).getLongitude();
                Log.i(TAG, "onCreate: le lieu que vous devez trouver est "+lieu);
                Log.i(TAG, "onCreate: les coordonné géographique du lieu sont "+latitud+" "+longitud);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * la fonction getData() nous retrourne de maniére aléatoire une ville ou un pays qu'on va devoir localiser sur la map
     * @param k numéro de la ligne donc du pays ou de la ville qui se situe a la ligne k
     * @return
     */

    private String getData(int k) {
        Cursor cursor = db.viewdata(k);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "no data to show", Toast.LENGTH_SHORT ).show();
        } else {
            while (cursor.moveToNext()){
                    lieu =cursor.getString(1);
            }
        }
        return lieu;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //UiSettings settings= mMap.getUiSettings();
        //settings.setZoomControlsEnabled(false);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
       // LatLng place = new LatLng(latitud, longitud);
        //mMap.addMarker((new MarkerOptions().position(place)));
        mMap.addMarker(new MarkerOptions().position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng arg0)
            {
                // deux variable de type double pour récuperer les coordonée géo du lieu sur lequel le user il a cliqué
                latClickedOn = arg0.latitude;longClickedOn=arg0.longitude;
                Log.i(TAG, "onMapClick: vous avez cliquer a la position en latitude  "+latClickedOn);
                Log.i(TAG, "onMapClick: vous avez cliquer a la position en longitude "+longClickedOn);
                Log.i(TAG, "onMapClick: latitude et longitude du click "+arg0);
                // on fourni en parametre al a fonction getTheLocation(latClickedOn, longClickedOn) les coordonnée géo du lieu afin de trouver le pays ou la ville correspondant a ces coordonnées
                getTheLocation(latClickedOn, longClickedOn);
            }
        });
    }
    @Override
    public void onMapClick(LatLng latLng) {
    }

    /**
     * La fonction getTheLocation() nous retrouve le nom de la ville ou du pays correpondant au coordonnée géographique en fourni en parametre
     * @param Lati correspond a la latitude
     * @param Longi correpond a la longitude
     */
    public void getTheLocation(double Lati, double Longi){
        try {
            geocoder = new Geocoder(this, Locale.FRANCE);
            addresse = geocoder.getFromLocation(Lati,Longi, 1);
            if (addresse.size() > 0 && addresse != null) {
                paysClickedOn = addresse.get(0).getCountryName();
                Log.i(TAG, "onMapClick: Le pays sur laquelle vous avez cliquer est "+ paysClickedOn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * la fonction correspond au bouton valider de l'activité
     * fonction qui permet de valider la réponse donné par l'utilisateur en choisissant un lieu sur la map en cliquant deçu
     * @param view
     */

    public void validerClick(View view) {
        if(bool==true) {
            bool = false;
            score++;
        } else {
        }
        buttonTest.setEnabled(false);
        buttonNew.setEnabled(true);
        tvScoreMap.setText(score+"");
    }

    /**
     * la fonction rejouer  correspond au bouton New de l'activité
     * elle permet de relancer une nouvelle question d'enlever lesm arker déja existant sur la map qui correspondent a la précédente question
     * et de placer de nouveau marker dans les nouvelle coordonnée géo générées
     * @param view
     */

    public void rejouer(View view) {
        trouverPays();
        mMap.clear();
        buttonTest.setEnabled(true);
        onMapReady(mMap);
    }

    /**
     * la fonction tester correspond au bouton Tester de l'activité.
     * cette fonction permet de tester la reponse donnée par le user sans la valider
     * @param view
     */
    public void tester(View view) {
        if (paysClickedOn.toLowerCase().equals(lieu.toLowerCase())){
            bool=true;
            buttonValider.setEnabled(true);

            Log.i(TAG, "tester: "+lieu);
            Toast.makeText(this, "Bien jouer !!! ", Toast.LENGTH_LONG).show();
            Log.i(TAG, "tester: Bien jouer !!! ");
        } else {
            Toast.makeText(this, "Faux !!! ", Toast.LENGTH_LONG).show();
            Log.i(TAG, "tester: Faux !!! "+paysClickedOn+"    "+lieu);
        }
    }


}
