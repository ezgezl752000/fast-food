package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import android.widget.Toast;

import com.example.storeproject.R;
import com.example.storeproject.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        loginOnPress();
        onRegister();
    }

    private void loginOnPress(){

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnected(LoginActivity.this)){
                    showCustomDialog();
                }
                String email = binding.etUser.getText().toString();
                String pass = binding.etPass.getText().toString();

                if(TextUtils.isEmpty(email)){
                    binding.etUser.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    binding.etPass.setError("Password is required!");
                    return;
                }
                if(pass.length() < 8) {
                    binding.etPass.setError("Password must be >= 8 characters!");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getBaseContext(), HomePage.class));
                        }else{
                            Toast.makeText(getBaseContext(),"Fail",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Please connect to the internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void onRegister(){
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),RegisterActivity.class));
            }
        });
    }

    private boolean isConnected(LoginActivity loginActivity){
        ConnectivityManager connectivityManager= (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wificonn != null && wificonn.isConnected()) || (mobileconn !=null && mobileconn.isConnected())){
            return true;
        }else return false;
    }
}