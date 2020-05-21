package com.example.idris_000.wall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {


    EditText ed1,ed2;
    Button btn1,btn2,btn3,btn4;
    TextView display;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calci);
        ed1=findViewById(R.id.no1);
        ed2=findViewById(R.id.no2);
        display=findViewById(R.id.textview1);
        btn1=findViewById(R.id.btnadd);
        btn2=findViewById(R.id.btnsub);
        btn3=findViewById(R.id.btnmul);
        btn4=findViewById(R.id.btndiv);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                double a=Double.parseDouble(ed1.getText().toString());
                double b=Double.parseDouble(ed2.getText().toString());
                double c=a+b;
                display.setText("Your ans is: "+c);
                ed1.setText("");
                ed2.setText("");
                Toast.makeText(Calculator.this, "Addition", Toast.LENGTH_LONG).show();

            }

        });
        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                double a=Double.parseDouble(ed1.getText().toString());
                double b=Double.parseDouble(ed2.getText().toString());
                double c=a-b;
                display.setText("Your ans is: "+c);
                ed1.setText("");
                ed2.setText("");
                Toast.makeText(Calculator.this, "Subtraction", Toast.LENGTH_LONG).show();

            }

        });
        btn3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                double a=Double.parseDouble(ed1.getText().toString());
                double b=Double.parseDouble(ed2.getText().toString());
                double c=a*b;
                display.setText("Your ans is: "+c);
                ed1.setText("");
                ed2.setText("");
                Toast.makeText(Calculator.this, "Multiplication", Toast.LENGTH_LONG).show();

            }

        });
        btn4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                double a=Double.parseDouble(ed1.getText().toString());
                double b=Double.parseDouble(ed2.getText().toString());
                double c=a/b;
                display.setText("Your ans is: "+c);
                ed1.setText("");
                ed2.setText("");
                Toast.makeText(Calculator.this,"Division",Toast.LENGTH_LONG).show();

            }

        });
    }
}
