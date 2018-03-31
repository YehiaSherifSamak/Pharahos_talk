package org.tensorflow.demo;

/**
 * Created by yahya on 2/13/2018.
 */
import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bridou_n.beaconscanner.features.beaconList.BeaconListActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.bridou_n.beaconscanner;
public class SignIn extends Activity{
    Button loginButton;
    TextView registerButton;
    EditText emailEditText, passwordEditText;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser mUser;
    public static final String TAG="Login";
    ProgressDialog mDialog;
    String mEmail,mPassword;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Fabric.with(this, new Crashlytics());
        loginButton = (Button) findViewById(R.id.signinButton);
        registerButton = (TextView) findViewById(R.id.signupTextView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
     //   googleButton = (Button) findViewById(R.id.google_sign_in_button);
        signUpButton = (Button) findViewById(R.id.signupButton);
        ImageView signinImageView = (ImageView) findViewById(R.id.signinImageView);
        signinImageView.setImageResource(R.drawable.photo1);
        signUpButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)
          {
              startActivity(new Intent(SignIn.this, SignUp.class));
            //  startActivity(new Intent(SignIn.this, BeaconListActivity.class));
          }

      });
        mDialog = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=FirebaseAuth.getInstance().getCurrentUser();
        mAuthListener= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(mUser!=null)
                {
                    Intent intent = new Intent(SignIn.this, MainMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Log.d(TAG,"AuthStateChange:LogOut");
                }
            }

        };
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userSign();
            }
        });


    }
    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(mAuth!=null)
            mAuth.removeAuthStateListener(mAuthListener);

    }

  /*  @Override
    public void onBackPressed()
    {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();

        finish();
        //finish();
       // System.exit(0);
    }*/
  public void onBackPressed(){
      Intent a = new Intent(Intent.ACTION_MAIN);
      a.addCategory(Intent.CATEGORY_HOME);
      a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(a);

  }
    public void userSign()
    {
        mEmail=emailEditText.getText().toString().trim();
        mPassword=passwordEditText.getText().toString().trim();
        if(TextUtils.isEmpty(mEmail))
        {
            Toast.makeText(SignIn.this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mPassword))
        {
            Toast.makeText(SignIn.this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Login please wait...");
        mDialog.setIndeterminate(true);
        mDialog.show();
        mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this,"Wrong Email or Password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mDialog.dismiss();
                    startActivity(new Intent(SignIn.this,MainMenu.class));
                }
            }
        });
    }

}
