package com.example.idris_000.wall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class Personal extends AppCompatActivity {

    ImageButton btn;
    ListView lvg;
    TextView tv;
    EditText et, et2;
    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    DatabaseReference myRef = fd.getReference("Personal").child("Message");
    DatabaseReference myRef2 = fd.getReference("User");
    private ArrayList<Adddata> msg = new ArrayList<>();
    private ArrayAdapter<Adddata> adapter;
    String a, value,enrollm;
    int str;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        super.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        btn = findViewById(R.id.msg);
        tv = findViewById(R.id.name);
        et = findViewById(R.id.text);
        et2 = findViewById(R.id.uname);
        lvg = findViewById(R.id.personal);
        registerForContextMenu(lvg);

        Intent in = getIntent();
        //Bundle extra = in.getExtras();
       // final String email =Wallhome.em;
        final String name =Wallhome.n;// extra.getString("name");
        enrollm = Wallhome.e;//extra.getString("Enrollment");
        tv.setText("Welcome:  " + name);

//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, msg);
//        lvg.setAdapter(arrayAdapter);
        adapter = new MessageAdapter(this, R.layout.activity_chat_left, msg);
        lvg.setAdapter(adapter);

        myRef.child(enrollm).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Adddata add = dataSnapshot.getValue(Adddata.class);
               // String value = String.valueOf(add);
                msg.add(add);
                adapter.notifyDataSetChanged();
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
                Toast.makeText(Personal.this, "Unble To Read Data", Toast.LENGTH_SHORT).show();

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmsg();

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.personal_menu, menu);
    }

    public void addmsg() {
        final String msg = et.getText().toString();
        final String sendto = et2.getText().toString().toUpperCase().trim();
        if (msg.isEmpty()) {
            et.setError("Enter Something");
            return;
        }
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int str = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User ad = ds.getValue(User.class);
                    if ((ad.enroll).equalsIgnoreCase(sendto)) {
                        str++;
                        break;
                    }
                }
                if (str == 1) {
                    Adddata add=new Adddata(Wallhome.n,msg,enrollm);//please uncomment only for testing purpose
                    String a=myRef.push().getKey();
                    myRef.child(sendto).child(a).setValue(add);
//                reference1.push().setValue(map);
//                reference2.push().setValue(map);
                    et.setText("");
                    et2.setText("");
                    str = 0;
                    Toast.makeText(Personal.this, "Message Sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Personal.this, "Enter valid Enrollment", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//
//    @Override
//    public boolean onContextItemSelected(final MenuItem item) {
//        Intent in = getIntent();
//        Bundle extra=in.getExtras();
//        final String email = extra.getString("email");
//        final String name=extra.getString("name");
//        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int text=info.position;
//        String txt=lvg.getItemAtPosition(text).toString().trim();
//        int pos=txt.indexOf("-")+1;
//        String ftxt=txt.substring(pos);
//
//        switch (item.getItemId()) {
//            case R.id.del:
////               String bb= myRef.child(name).getRoot().child(ftxt).getKey().toString();
////               tv.setText(bb);
//                Toast.makeText(Personal.this, "Removed", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//
//        }
//    }
}