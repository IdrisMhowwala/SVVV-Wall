package com.example.idris_000.wall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivitystu extends AppCompatActivity {

    Button btn2;
    EditText et1,et2;
    TextView tv;
    private FirebaseAuth fa;
    boolean connected;
    AlertDialog.Builder builder;
    SharedPreferences sharedPreferences;

    void loginUser()
    {
        final String email=et1.getText().toString().trim().toLowerCase();
        String pass=et2.getText().toString().trim().toLowerCase();

        if (email.isEmpty())
        {
            et1.setError("E-mail is Required");
            return;
        }
        if (pass.isEmpty())
        {
            et2.setError("Password is Required");
            return;
        }

            fa.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Intent i = new Intent(MainActivitystu.this, Wallhome.class);
                                i.putExtra("email", email);
                                startActivity(i);
                                MainActivitystu.this.finish();

                            } else {
                                Toast.makeText(MainActivitystu.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivitystu.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);
        super.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        et1 = findViewById(R.id.uname);
        et2 = findViewById(R.id.pass);
        btn2 = findViewById(R.id.login);
        fa = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(MainActivitystu.this,R.style.CustomDialogTheme);

        final String email=et1.getText().toString().trim().toLowerCase();

        ConnectivityManager cn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cn.getActiveNetworkInfo();
        connected = info != null && info.isConnected();

//        if (fa.getInstance().getCurrentUser() != null) {
//            Intent i = new Intent(MainActivitystu.this, Wallhome.class);
//            i.putExtra("email", "idrismhowwala@yahoo.in");
//            startActivity(i);
//        }

            btn2.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (connected == true) {
                        loginUser();
                    } else {
                        //  Toast.makeText(MainActivitystu.this,"Internet is not Connected",Toast.LENGTH_LONG).show();
                        AlertDialog al = builder.setMessage("No Internet Connectivity").setTitle("Alert").setCancelable(true).create();
                        al.show();
                        ;
                    }
                }
            }));


        }

}
