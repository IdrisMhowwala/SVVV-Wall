package com.example.idris_000.wall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


public class Wallhome extends AppCompatActivity {

    FirebaseDatabase fd= FirebaseDatabase.getInstance();
    DatabaseReference myRef2=fd.getReference("User");
    LinearLayout ll;
    private FirebaseAuth fa;
    Button bt2,bt3,bt4,btn5;
    TextView tv,tv2,tv3;
    ProgressBar pg;

public static String a,d,n,e,u,em;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.wallh);
        ll=findViewById(R.id.wall);
        bt2=findViewById(R.id.general);
        bt3=findViewById(R.id.dept);
        bt4=findViewById(R.id.personal);
        btn5=findViewById(R.id.prof);
        pg=findViewById(R.id.pbar);
        tv=findViewById(R.id.name);
        tv2=findViewById(R.id.logout);

        pg.setVisibility(View.VISIBLE);
        tv2.setPaintFlags(tv2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        Intent in=getIntent();
        final String email=in.getStringExtra("email");
        //tv.setText(email);

        myRef2.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                a = dataSnapshot.getKey().toString();
                ValueEventListener postlist=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        d=dataSnapshot.child(a).child("dept").getValue(String.class);
                        n=dataSnapshot.child(a).child("name").getValue(String.class);
                        e=dataSnapshot.child(a).child("enroll").getValue(String.class);
                        u=dataSnapshot.child(a).child("url").getValue(String.class);
                        em=dataSnapshot.child(a).child("email").getValue(String.class);
                        tv.setText("Welcome: "+n);
                        pg.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Wallhome.this,"Unable to fetch data",Toast.LENGTH_LONG).show();

                    }
                };
                myRef2.addValueEventListener(postlist);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Wallhome.this,General.class);
                Bundle extra=new Bundle();
                extra.putString("Enrollment",e);
                extra.putString("dept",d);
                extra.putString("name",n);
                extra.putString("url",u);
                i.putExtras(extra);
                startActivity(i);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e.contains("TEACHER")) {
                    Intent i = new Intent(Wallhome.this, Depart_teach.class);
                    Bundle extra = new Bundle();
//                    extra.putString("Enrollment", e);
//                    extra.putString("dept", d);
//                    extra.putString("name", n);
//                    extra.putString("url", u);
                    i.putExtras(extra);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(Wallhome.this, Department.class);
                    Bundle extra = new Bundle();
//                    extra.putString("Enrollment", e);
//                    extra.putString("dept", d);
//                    extra.putString("name", n);
//                    extra.putString("url", u);
                    i.putExtras(extra);
                    startActivity(i);
                }
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Wallhome.this,Personal.class);
                Bundle extra=new Bundle();
//                extra.putString("Enrollment",e);
//                extra.putString("email",email);
//                extra.putString("dept",d);
//                extra.putString("name",n);
//                extra.putString("url",u);
                i.putExtras(extra);
                startActivity(i);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Wallhome.this,Profile.class);
                Bundle extra=new Bundle();
//                extra.putString("email",email);
//                extra.putString("Enrollment",e);
//                extra.putString("dept",d);
//                extra.putString("name",n);
//                extra.putString("url",u);
                i.putExtras(extra);
                startActivity(i);
            }
        });
    }
    public  void click(View v)
    {
        Intent in=null;
        switch (v.getId())
        {
            case R.id.logout:
                fa.getInstance().signOut();
               in=new Intent(Wallhome.this,MainActivity.class);
               Wallhome.this.finish();
                break;
        }
        startActivity(in);
    }
}