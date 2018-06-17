package org.tensorflow.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

//import org.tensorflow.Test;
import org.tensorflow.demo.features.beaconList.BeaconListActivity;

//import com.bridou_n.beaconscanner.features.beaconList.BeaconListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

import static org.tensorflow.demo.SignIn.TAG;


public class MainMenu extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        ImageView mainMenuImageView = (ImageView) findViewById(R.id.mainMenuImageView);
        mainMenuImageView.setImageResource(R.drawable.samak_abyad_2);




       /* explore=(Button)findViewById(R.id.explore);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenu.this,Info.class);
                startActivity(intent);

            }
        })*/
      /*  gridLayout=(GridLayout)findViewById(R.id.gridlayout);
       for(int i=0;i<gridLayout.getChildCount();i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);

                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

       setSingleEvent(gridLayout);

     //setToggleEvent(gridLayout);

*/
      CardView exploreImageView = (CardView) findViewById(R.id.exploreCardView);
      CardView locationImageView = (CardView) findViewById(R.id.locationCardView);
      CardView catalogeImageView = (CardView) findViewById(R.id.catalogueCardView);
     // ImageView languageImageView = (ImageView) findViewById(R.id.languageImageView);
      CardView languageCardView = (CardView) findViewById(R.id.languageCardView);
      exploreImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(MainMenu.this, ClassifierActivity.class));

          }
      });
      locationImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(MainMenu.this, BeaconListActivity.class));
          }
      });
      languageCardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              startActivity(new Intent(MainMenu.this, Language.class));
          }
      });
      catalogeImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(MainMenu.this, CatalogMainActivity.class));
          }
      });
    }


    @Override
    public void onBackPressed()
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
        builder.setMessage(R.string.are_you_sure);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /* finish();
                System.exit(0);*/
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        builder.setNeutralButton(R.string.logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent backIntent = new Intent(MainMenu.this, SignIn.class);
                startActivity(backIntent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
