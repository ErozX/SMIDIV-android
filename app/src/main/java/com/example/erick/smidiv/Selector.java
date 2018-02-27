package com.example.erick.smidiv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Selector extends AppCompatActivity {

    FragmentManager fragmentManager =  getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.inicio:
                    fragmentTransaction.replace(R.id.container,new Inicio()).commit();
                    return true;
                case R.id.estatus:
                    fragmentTransaction.replace(R.id.container,new Estatus()).commit();
                    return true;
                case R.id.alerta:
                    fragmentTransaction.replace(R.id.container,new Alerta()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager1 = getSupportFragmentManager();
        FragmentTransaction trans =  fragmentManager1.beginTransaction();
        trans.replace(R.id.container,new Inicio()).commit();
    }

}
