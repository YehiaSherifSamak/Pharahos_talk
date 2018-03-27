package org.tensorflow.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yahya on 3/5/2018.
 */

public class Info extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        String monumentName = getIntent().getStringExtra("Monument_name");
        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        ImageView monumentImageView = (ImageView) findViewById(R.id.monumenImageView);
        TextView topicTextView = (TextView) findViewById(R.id.topicTextView);
        topicTextView.setText(monumentName);
        switch (monumentName)
        {
            case "tutankhamun":
                monumentImageView.setImageResource(R.drawable.tout);
                infoTextView.setText(R.string.toutdesc);
                topicTextView.setText("Tout Ankh Amoun");
                break;
            case "nefertiti":
                monumentImageView.setImageResource(R.drawable.nefertitiinfo);
                infoTextView.setText(R.string.nefertitidesc);
                topicTextView.setText("Nefertiti");
                break;
            case "sphinx":
                //baskael moaka2et
                monumentImageView.setImageResource(R.drawable.tout);
                infoTextView.setText(R.string.toutdesc);
                break;
            case "ramsis":
                monumentImageView.setImageResource(R.drawable.ramsesinfo);
                infoTextView.setText(R.string.ramsisdesc);
                topicTextView.setText("Ramses ll");
                break;
            case "ikhnaton":
                monumentImageView.setImageResource(R.drawable.ikhnatouninfo);
                infoTextView.setText(R.string.ikhnatoundesc);
                topicTextView.setText("Ikhnaton");
                break;
            default:
                monumentImageView.setImageResource(R.drawable.tout);
                infoTextView.setText(R.string.toutdesc);
                break;
        }
    }
}
