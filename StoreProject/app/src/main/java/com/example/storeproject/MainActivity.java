package com.example.storeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.storeproject.databinding.ActivityMainBinding;

import Activity.HomePage;
import Presenter.LoginPresenter;
import Presenter.iLogin;

public class MainActivity extends AppCompatActivity implements iLogin{

    ActivityMainBinding binding;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        loginPresenter = new LoginPresenter((iLogin) this);
//        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = binding.etUser.getText().toString();
//                String password = binding.etPass.getText().toString();
//
//                loginPresenter.onLogin(username,password);
//            }
//        });
    }

    @Override
    public void onLoginSuccess(String mess) {
        startActivity(new Intent(getBaseContext(), HomePage.class));
        Toast.makeText(getBaseContext(),mess,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginError(String mess) {
        Toast.makeText(getBaseContext(),mess,Toast.LENGTH_LONG).show();
    }
}