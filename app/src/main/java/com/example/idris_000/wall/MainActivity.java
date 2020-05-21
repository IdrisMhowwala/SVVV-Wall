package com.example.idris_000.wall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn1,btn2;
    boolean connected;
    AlertDialog.Builder builder;
    static int backpress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = findViewById(R.id.signup);
        btn1 = findViewById(R.id.student);
        btn2 = findViewById(R.id.teacher);
        builder = new AlertDialog.Builder(MainActivity.this,R.style.CustomDialogTheme);

        tv.setPaintFlags(tv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);



        ConnectivityManager cn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cn.getActiveNetworkInfo();
        connected = info != null && info.isConnected();

        if (connected==false)
        {
            AlertDialog al= builder.setMessage("No Internet Connectivity").setTitle("Alert").setCancelable(true).create();
            al.show();;
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(MainActivity.this, MainActivitystu.class);
                startActivity(inte);
                MainActivity.this.finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,MainActivityTeac.class);
                startActivity(in);
                MainActivity.this.finish();
            }
        });
    }

    public void wow(View view)
    {
        Intent in=null;
        switch (view.getId()) {
            case R.id.signup:
                in = new Intent(MainActivity.this, Signup.class);
                break;
        }
        startActivity(in);
    }

    public void onBackPressed(){
        backpress = backpress +1;
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

        if (backpress>1) {
            MainActivity.this.finish();
        }
    }
}

