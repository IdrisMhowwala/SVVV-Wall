package com.example.idris_000.wall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Profile extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4;
    ImageView imv;
    private StorageReference Sref;
    FirebaseDatabase fd= FirebaseDatabase.getInstance();
    DatabaseReference myRef2=fd.getReference("User");
    static String name,enroll;
    ProgressBar pg;
    final Handler handler = new Handler();


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        tv1 = findViewById(R.id.name);
        tv2 = findViewById(R.id.mail);
        tv3 = findViewById(R.id.enroll);
        tv4 = findViewById(R.id.dept);
        pg = findViewById(R.id.pbar);
        imv = findViewById(R.id.profile);

        pg.setVisibility(View.VISIBLE);

        Sref = FirebaseStorage.getInstance().getReference();

        Intent in = getIntent();
        Bundle extra = in.getExtras();
        enroll = Wallhome.e;//extra.getString("Enrollment");
        name = Wallhome.n;//extra.getString("name");
        final String email =Wallhome.em;//extra.getString("email");
        final String dept = Wallhome.d;//extra.getString("dept");
        final String url=Wallhome.u;//extra.getString("url");

        tv1.setText(name);
        tv2.setText(email);
        tv3.setText(enroll);
        tv4.setText(dept);

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // String msg="https://img.timesnownews.com/zoom/story/1513190264-srkgallery.jpg?d=600x450";
                Picasso.with(Profile.this).load(url).fit().centerCrop().into(imv);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        pg.setVisibility(View.GONE);
                    }
                }, 2000);
               // pg.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
