package com.example.idris_000.wall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Depart_teach extends AppCompatActivity {

    ImageButton btn;
    ListView lvg;
    TextView tv;
    EditText et;
    FirebaseDatabase fd= FirebaseDatabase.getInstance();
    DatabaseReference myRef=fd.getReference("Department");
    DatabaseReference myRef2=fd.getReference("Department").child("Message");
   // private ArrayList<String> msg=new ArrayList<>();
    private ArrayList<Adddata> msg=new ArrayList<>();
    private ArrayAdapter<Adddata> adapter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
        super.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        btn=findViewById(R.id.msg);
        tv=findViewById(R.id.name);
        et=findViewById(R.id.text);
        lvg=findViewById(R.id.dept);

        Intent in = getIntent();
        Bundle extra=in.getExtras();
        final String enroll = Wallhome.e;//extra.getString("Enrollment");
        final String dept =Wallhome.d;//extra.getString("dept");
        final String name=Wallhome.n;//extra.getString("name");
        tv.setText("Welcome: "+name);

//        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,msg);
//        lvg.setAdapter(arrayAdapter);
        adapter = new MessageAdapter(this, R.layout.activity_chat_left, msg);
        lvg.setAdapter(adapter);

        myRef.child("Message").child(dept).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Adddata add=dataSnapshot.getValue(Adddata.class);
               // String value=String.valueOf(add);
                if (add.getEnroll().equalsIgnoreCase(enroll)) {
                    msg.add(add);
                    adapter.notifyDataSetChanged();
                }
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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=et.getText().toString();
                if (msg.isEmpty())
                {
                    et.setError("Enter Something");
                    return;
                }
                Adddata add=new Adddata(name,msg,enroll);
                String a=myRef2.child(dept).push().getKey();
                myRef2.child(dept).child(a).setValue(add);
                et.setText("");
                Toast.makeText(Depart_teach.this,"Message Added",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
