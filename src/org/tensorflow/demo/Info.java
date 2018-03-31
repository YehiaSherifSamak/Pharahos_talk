package org.tensorflow.demo;

import android.app.Activity;
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
                monumentImageView.setImageResource(R.drawable.tout);
                //infoTextView.setText(R.string.toutdesc);
                descriptionTextView.setText(R.string.toutdesc);
               // topicTextView.setText("Tout Ankh Amoun");
                collapsingToolbarLayout.setTitle("Tout Ankh Amoun");
                break;
            case "nefertiti":
                monumentImageView.setImageResource(R.drawable.nefertitiinfo);
                descriptionTextView.setText(R.string.nefertitidesc);
                //topicTextView.setText("Nefertiti");
                collapsingToolbarLayout.setTitle("Nefertiti");
                break;
            case "sphinx":
                //baskael moaka2et
                monumentImageView.setImageResource(R.drawable.tout);
                descriptionTextView.setText(R.string.toutdesc);
                collapsingToolbarLayout.setTitle("Sphinx");
                break;
            case "ramsis":
                monumentImageView.setImageResource(R.drawable.ramsesinfo);
                descriptionTextView.setText(R.string.ramsisdesc);
                collapsingToolbarLayout.setTitle("Ramses ll");
                break;
            case "ikhnaton":
                monumentImageView.setImageResource(R.drawable.ikhnatouninfo);
                descriptionTextView.setText(R.string.ikhnatoundesc);
                collapsingToolbarLayout.setTitle("Ikhnaton");
                break;
            default:
                monumentImageView.setImageResource(R.drawable.tout);
                descriptionTextView.setText(R.string.toutdesc);
                collapsingToolbarLayout.setTitle("Tout Ankh Amoun");
                break;
        }
    }
}
