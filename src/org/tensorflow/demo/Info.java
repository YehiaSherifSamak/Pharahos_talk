package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yahya on 3/5/2018.
 */

public class Info extends AppCompatActivity {
    TextView  descriptionTextView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String orgin;
    String roomName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ImageView monumentImageView = (ImageView) findViewById(R.id.bgheader);
        descriptionTextView=(TextView)findViewById(R.id.description);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setTitle("Tout Ankh Amoun");
        String monumentName = getIntent().getStringExtra("Monument_name");
        orgin = getIntent().getStringExtra("came_from");
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
      /*  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        String monumentName = getIntent().getStringExtra("Monument_name");
        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());

        TextView topicTextView = (TextView) findViewById(R.id.topicTextView);
        topicTextView.setText(monumentName);*/
        switch (monumentName)
        {
            case "tutankhamun":
            case"Tutankhamun":
                monumentImageView.setImageResource(R.drawable.tout);
                //infoTextView.setText(R.string.toutdesc);
                descriptionTextView.setText(R.string.toutdesc);
               // topicTextView.setText("Tout Ankh Amoun");
                collapsingToolbarLayout.setTitle("Tout Ankh Amoun");
                roomName = "First Room";
                break;
            case "nefertiti":
            case "Nefertiti":
                monumentImageView.setImageResource(R.drawable.nefertitiinfo);
                descriptionTextView.setText(R.string.nefertitidesc);
                //topicTextView.setText("Nefertiti");
                collapsingToolbarLayout.setTitle("Nefertiti");
                roomName = "Second Room";
                break;
            case "sphinx":
            case"Sphinx":
                //baskael moaka2et
                monumentImageView.setImageResource(R.drawable.sphinxjpg);
                descriptionTextView.setText(R.string.sphincdesc);
                collapsingToolbarLayout.setTitle("Sphinx");
                roomName = "First Room";
                break;
            case "ramsis":
            case "Ramsis":
                monumentImageView.setImageResource(R.drawable.ramsesinfo);
                descriptionTextView.setText(R.string.ramsisdesc);
                collapsingToolbarLayout.setTitle("Ramses ll");
                roomName = "First Room";
                break;
            case "ikhnaton":
            case "Ikhnaton":
                monumentImageView.setImageResource(R.drawable.ikhnatouninfo);
                descriptionTextView.setText(R.string.ikhnatoundesc);
                collapsingToolbarLayout.setTitle("Ikhnaton");
                roomName = "Second Room";
                break;
            case "hatshepsut":
            case "Hatshepsut":
                monumentImageView.setImageResource(R.drawable.hatshjpg);
                descriptionTextView.setText(R.string.hatshpdesc);
                collapsingToolbarLayout.setTitle("Hatshepsut");
                roomName = "Second Room";
                break;
            default:
                monumentImageView.setImageResource(R.drawable.tout);
                descriptionTextView.setText(R.string.toutdesc);
                collapsingToolbarLayout.setTitle("Tout Ankh Amoun");
                roomName = "Second Room";
                break;
        }
    }
    @Override
    public void onBackPressed()
    {
        switch (orgin)
        {
            case "camera":
                Intent backIntent = new Intent(Info.this, ClassifierActivity.class);
                startActivity(backIntent);
                break;
            default:
                Intent backIntent2 = new Intent(Info.this, MonumentsActivity.class);
                backIntent2.putExtra("rest name", roomName);
                startActivity(backIntent2);

        }

    }
}
