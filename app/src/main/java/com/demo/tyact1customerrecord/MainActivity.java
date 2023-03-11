package com.demo.tyact1customerrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button CreateNewEmp, ManageRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateNewEmp = (Button) findViewById(R.id.CreateNewEmp);

        CreateNewEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(MainActivity.this,CreateNewCustomer.class);
                startActivity(in);
            }
        });
    }
}