package org.tensorflow.demo;

/**
 * Created by yahya on 4/17/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.tensorflow.demo.R;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        /*getSupportActionBar().setTitle("Pharaoh's Talk"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable();*/
        final Intent intent = getIntent();
        String numberOfRoom = intent.getStringExtra("room");
        final String nameOfRoom;
        Button guideMeButton = (Button) findViewById(R.id.guideMeButton);
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        switch (numberOfRoom)
        {
            case "1":
                nameOfRoom = "Entrance Hall";
                break;
            case "2":
                nameOfRoom = "Corridor";
                break;
            case "4":
                nameOfRoom = "Exit Hall";
                break;
            default:
                nameOfRoom = "I do not know";
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Statues);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView);
        textView.setAdapter(adapter);
        Toast.makeText(SearchActivity.this, nameOfRoom, Toast.LENGTH_LONG);
        guideMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target  = autoCompleteTextView.getText().toString();
                Intent intent1 = new Intent(SearchActivity.this, GuideActivity.class);
                intent1.putExtra("room", nameOfRoom);
                intent1.putExtra("target",target);
                //startActivity(intent1);
                //-------------------------------------------------------------------------------------------
                String start = nameOfRoom;
                String end = target;
                ImageView mapImageView = (ImageView) findViewById(R.id.mapImageView2);
                if(start.equals("Entrance Hall"))
                {
                    if(end.equals("nefertiti") || end.equals("ikhnaton") || end.equals("hatshepsut"))
                    {
                        mapImageView.setImageResource(R.drawable.entrance_senario_1);
                    }
                    else
                    {
                        Toast.makeText(SearchActivity.this,"ajhdabaskj", Toast.LENGTH_LONG).show();
                    }
                }
                else if(start.equals("Exit Hall"))
                {
                    if(end.equals("tutankhamun") || end.equals("sphinx") || end.equals("ramses i"))
                    {
                        mapImageView.setImageResource(R.drawable.exit_senario_1);
                    }
                    else
                    {
                        Toast.makeText(SearchActivity.this,"ajhdabaskj", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    if(end.equals("tutankhamun") || end.equals("sphinx") || end.equals("ramses i"))
                    {
                        mapImageView.setImageResource(R.drawable.corredor_senario_2);
                    }
                    else
                    {
                        mapImageView.setImageResource(R.drawable.corredor_senario_1);
                    }
                }
                mapImageView.setVisibility(View.VISIBLE);

                //-----------------------------------------------------------------------------------------------
            }
        });

    }

    private static final String[] Statues = new String[] {
            "nefertiti", "hatshepsut", "ramses i", "ramses ii", "ramses iii" ,"tutankhamun","sphinx","ikhnaton","amenhotep i",
            "amenhotep ii","amenhotep iii",
    };

}

