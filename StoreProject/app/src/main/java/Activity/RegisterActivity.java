package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.storeproject.R;
import com.example.storeproject.databinding.ActivityRegister1Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import Data.User;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegister1Binding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    User user;
    String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register1);
        initial();
        onCreateAccount();
        onBack();
    }

    private void initial() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }
    private void writeNewUser(User _user){
        currentUserId =FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("users").child(currentUserId).setValue(user);
    }
    private void onBack(){
        binding.btnRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
            }
        });
    }

    private void onCreateAccount(){
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.etRegisterUser.getText().toString();
                String pass = binding.etRigisterPass.getText().toString();
                String confirmPass = binding.etRegisterConfirm.getText().toString();
                String phone = binding.etRegisterPhone.getText().toString();
                String name = binding.etRegisterFullName.getText().toString();

                if(TextUtils.isEmpty(email)){
                    binding.etRegisterUser.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    binding.etRigisterPass.setError("Password is required!");
                    return;
                }
                if(pass.length() < 8) {
                    binding.etRigisterPass.setError("Password must be >= 8 characters!");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    binding.etRegisterPhone.setError("Phone is required!");
                    return;
                }
                if(TextUtils.isEmpty(confirmPass)){
                    binding.etRegisterPhone.setError("Must confirm password");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    binding.etRegisterFullName.setError("Name is required!");
                    return;
                }
                if(!pass.equals(confirmPass)) {
                    binding.etRegisterConfirm.setError("Must equals password");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getBaseContext(),"Create Success",Toast.LENGTH_LONG).show();
                            user = new User(name,email,phone,"");
                            writeNewUser(user);
                            startActivity(new Intent(getBaseContext(),HomePage.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getBaseContext(),"Fail",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}