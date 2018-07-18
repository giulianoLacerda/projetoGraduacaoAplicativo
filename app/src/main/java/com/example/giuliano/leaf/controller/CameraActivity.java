package com.example.giuliano.leaf.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.giuliano.leaf.Camera2BasicFragment;
import com.example.giuliano.leaf.R;
import com.example.giuliano.leaf.model.User;

public class CameraActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        user = (User) getIntent().getSerializableExtra("USER");


        if (null == savedInstanceState) {
            Bundle data = new Bundle();//create bundle instance
            data.putString("COOKIE", user.getCookie());//put string to pass with a key valuesetAr
            Camera2BasicFragment camera = new Camera2BasicFragment();
            camera.setArguments(data);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, camera)
                    .commit();

        }

    }

    /*@Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }*/
}
