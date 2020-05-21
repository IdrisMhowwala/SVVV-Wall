package com.example.idris_000.wall;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    Button btn2;
    ImageView imv;
    EditText etn,ete,etenrol, etp,etno,etrp;
    Spinner sp;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 52;
    AlertDialog.Builder builder;
    private FirebaseAuth fa;
    private StorageReference Sref;
    FirebaseDatabase fd=FirebaseDatabase.getInstance();
    DatabaseReference myRef2=fd.getReference("User");
    User user;
    boolean connected;

    void registerUser() {
        String mail = ete.getText().toString().trim();
        String pass = etp.getText().toString();
        String pass2=etrp.getText().toString();

        if (pass.isEmpty()) {
            etp.setError("Password is Required");
            return;
        }

        if (!pass.equals(pass2))
        {
            etrp.setError("Password not matched");
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
//            ete.setError("Enter a Valid E-mail");
//            return;
//        }
        fa.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user registered
                            Toast.makeText(Signup.this, "User Registered", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Signup.this,MainActivity.class));
                            // FirebaseUser user=fa.getCurrentUser();
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(Signup.this, "User not Registered" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void adddata()
    {
         String name=etn.getText().toString().toUpperCase();
         String mobile=etno.getText().toString();
         String MobilePattern="[0-9]{10}";
        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(name);
         String email=ete.getText().toString().trim();
        String pass = etp.getText().toString();
        String pass2=etrp.getText().toString();
        String enroll=etenrol.getText().toString().toUpperCase().trim();
         String dept=sp.getSelectedItem().toString();

        if(name.isEmpty())
        {
            etn.setError("Name is Required");
            return;
        }
        if(!matcher.matches())
        {
            etn.setError("Enter Valid Name");
            return;
        }
        if (email.isEmpty()) {
            ete.setError("E-mail is Required");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ete.setError("Enter Valid E-mail Address");
            return;
        }
        if (enroll.isEmpty())
        {
            etenrol.setError("Enrollment is Required");
            return;
        }
        if (mobile.isEmpty())
        {
            etno.setError("Mobile Number is Required");
            return;
        }
        if (!mobile.matches(MobilePattern))
        {
            etno.setError("Enter a valid Mobile Number");
            return;
        }
        if (pass.isEmpty()) {
            etp.setError("Password is Required");
            return;
        }
        if (!pass.equals(pass2))
        {
            etrp.setError("Password not matched");
            return;
        }
        uploadImage();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.signup);
        etn=findViewById(R.id.name);
        ete=findViewById(R.id.uname);
        etp=findViewById(R.id.pass);
        etrp=findViewById(R.id.pass2);
        etno=findViewById(R.id.mob);
        btn2=findViewById(R.id.regis);
        etenrol=findViewById(R.id.enroll);
        sp=findViewById(R.id.dept);
        imv=findViewById(R.id.profile);

        builder = new AlertDialog.Builder(Signup.this,R.style.CustomDialogTheme);

        fa=FirebaseAuth.getInstance();
        Sref= FirebaseStorage.getInstance().getReference();

        ConnectivityManager cn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cn.getActiveNetworkInfo();
        connected = info != null && info.isConnected();


        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btn2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected==false)
                {
                    AlertDialog al= builder.setMessage("No Internet Connectivity").setTitle("Alert").setCancelable(true).create();
                    al.show();
                }
                else {
                    adddata();
                }
            }
        }));
    }

    private void uploadImage() {

        etenrol=findViewById(R.id.enroll);
        final String enroll=etenrol.getText().toString().toUpperCase();

        final String name=etn.getText().toString().toUpperCase();
        final String mobile=etno.getText().toString();
        final String email=ete.getText().toString().trim();
        final String pass = etp.getText().toString();
        final String dept=sp.getSelectedItem().toString();

        final String[] urll = new String[1];

        if (filePath == null)
        {
            AlertDialog al= builder.setMessage("Chose an Image").setTitle("Alert").setCancelable(true).create();
            al.show();
        }

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = Sref.child("images/"+ enroll);
            ref.putFile(filePath)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    urll[0] = uri.toString();

                                    user=new User(email,name,dept,mobile,enroll,pass,urll[0]);
                                    myRef2.push().getKey();
                                    myRef2.child(enroll).setValue(user);
                                    Toast.makeText(Signup.this,"Adding Data...",Toast.LENGTH_SHORT).show();

                                    registerUser();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imv.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}