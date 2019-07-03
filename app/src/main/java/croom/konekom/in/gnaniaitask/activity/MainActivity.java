package croom.konekom.in.gnaniaitask.activity;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;



import croom.konekom.in.gnaniaitask.receiver.CallDurationReceiver;
import croom.konekom.in.gnaniaitask.R;

import static croom.konekom.in.gnaniaitask.receiver.CallDurationReceiver.ACTION_IN;
import static croom.konekom.in.gnaniaitask.receiver.CallDurationReceiver.ACTION_OUT;

public class MainActivity extends AppCompatActivity {

    private int requestCode = 1;

    CallDurationReceiver callDurationReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        } else {
            registerRecver();
        }
    }
    private void registerRecver(){
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OUT);
        filter.addAction(ACTION_IN);
        this.callDurationReceiver = new CallDurationReceiver();
        this.registerReceiver(this.callDurationReceiver, filter);
    }
    private void checkAndRequestPermissions() {
        int phone_state = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        int write_storage= ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audion = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        int outgoing_call = ContextCompat.checkSelfPermission(this,
                Manifest.permission.PROCESS_OUTGOING_CALLS);

        if ((phone_state != PackageManager.PERMISSION_GRANTED)||(write_storage != PackageManager.PERMISSION_GRANTED)||(record_audion != PackageManager.PERMISSION_GRANTED)||((outgoing_call != PackageManager.PERMISSION_GRANTED))) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,Manifest.permission.PROCESS_OUTGOING_CALLS}, requestCode);

        } else {
         registerRecver();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.requestCode == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED&&
                    grantResults[2]==PackageManager.PERMISSION_GRANTED&&grantResults[3]==PackageManager.PERMISSION_GRANTED) {
                registerRecver();
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_LONG).show();

            } else {
                checkAndRequestPermissions();
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_LONG).show();

            }
        }
}
}
