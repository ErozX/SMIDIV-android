package com.example.erick.smidiv;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button inicio = (Button) findViewById(R.id.button);
        TextView registrar = (TextView) findViewById(R.id.textView7);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Haz iniciado sesion", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Selector.class));
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Por Favor llenar todos los datos", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Registrar.class));
            }
        });
    }
}
