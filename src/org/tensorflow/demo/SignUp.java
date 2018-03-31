package org.tensorflow.demo;

/**
 * Created by yahya on 3/2/2018.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SignUp extends Activity implements View.OnClickListener
{
    EditText name, email, password;
    Button register;
    TextView login;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    ProgressDialog dialog;
    String Name, Email, Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ImageView signupImageView = (ImageView)findViewById(R.id.signupImageView);
        signupImageView.setImageResource(R.drawable.photo1);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.signup);
        login = (TextView) findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }
    @Override
    public void onClick(View view) {
        if (view == register)
            UserRegister();
        else if (view == login)
            startActivity(new Intent(SignUp.this, SignIn.class));
    }
    public void UserRegister() {
        Name = name.getText().toString().trim();
        Password = password.getText().toString().trim();
        Email = email.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Name)) {
            Toast.makeText(SignUp.this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (Password.length() < 6) {

            Toast.makeText(SignUp.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Creating Account,please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    //OnAuth(task.getResult().getUser());
                    Toast.makeText(SignUp.this, "Registred succefully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "Error Occurred, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        Intent backIntent = new Intent(SignUp.this, SignIn.class);
        startActivity(backIntent);
    }


}
