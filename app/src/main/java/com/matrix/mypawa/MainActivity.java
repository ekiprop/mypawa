package com.matrix.mypawa;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
                BluetoothFragment.OnFragmentInteractionListener, SMSSendFragment.OnFragmentInteractionListener{
    private TextView mTextMessage;

    private static final int RECEIVE_SMS_PERMISSIONS_REQUEST = 1;

    private BroadcastReceiver broadcastReceiver;

    private PowerViewModel powerViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;

                case R.id.navigation_bluetooth:
                    fragment = new BluetoothFragment();
                    break;

                case R.id.navigation_sms:
                    fragment = new SMSSendFragment();
                    break;

            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new HomeFragment());

        BottomNavigationView navView = findViewById(R.id.nav_view);
       // mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        powerViewModel = ViewModelProviders.of(this).get(PowerViewModel.class);
        powerViewModel.getAllPowers().observe(this, new Observer<List<Power>>() {
            @Override
            public void onChanged(List<Power> powers) {
               // adapter.setPowers(powers);

            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)){
                //do nothing as user denied

            }else{
                // pop up
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS_PERMISSIONS_REQUEST);
            }
        } else {
            //nothing
        }
        registerReceiver();

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){

        switch(requestCode){
            case RECEIVE_SMS_PERMISSIONS_REQUEST:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Thanks for permitting", Toast.LENGTH_SHORT).show();
                    //smsSendMessage1();
                }else {
                    Toast.makeText(this, "Allow", Toast.LENGTH_SHORT).show();
                }//if
            }//case
        }//switch
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void myClickMethod(View v) {
        SMSSendFragment.myClickMethod(v);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void registerReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strMsgSrc = intent.getStringExtra("com.matrix.mypawa.sender");
                String strMsgBody = intent.getStringExtra("com.matrix.mypawa.body");





                    //strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;
                    Toast.makeText(context, "yeah", Toast.LENGTH_SHORT).show();

                    if(strMsgBody.equalsIgnoreCase("TF") ){
                        Power power = new Power("Device 1","+254702565559", "TF",
                                null, null,null);
                        power.setId(1);
                        powerViewModel.update(power);

                    }else if(strMsgBody.equalsIgnoreCase("TO") ){
                        Power power = new Power("Device 1","+254702565559", "TO",
                                null, null,null);
                        power.setId(1);
                        powerViewModel.update(power);
                    }


                               }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("com.matrix.mypawa"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }
}
