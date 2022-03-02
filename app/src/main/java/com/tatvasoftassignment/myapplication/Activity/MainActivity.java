package com.tatvasoftassignment.myapplication.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tatvasoftassignment.myapplication.Fragment.StudentList;
import com.tatvasoftassignment.myapplication.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, new StudentList()).commit();
    }
}