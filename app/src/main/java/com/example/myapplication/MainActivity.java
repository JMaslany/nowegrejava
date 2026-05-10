package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;
    ImageView img6;
    ImageView img7;
    ImageView img8;
    ImageView img9;
    ImageView img10;
    ImageView img11;
    ImageView img12;

    int[] collar = {
            R.drawable.Collarless_Icon,
            R.drawable.Butcher_Icon,
            R.drawable.Cleric_Icon,
            R.drawable.Druid_Icon,
            R.drawable.Fighter_Icon,
            R.drawable.Hunter_Icon,
            R.drawable.Jester_Icon,
            R.drawable.Mage_Icon,
            R.drawable.Monk_Icon,
            R.drawable.Necromancer_Icon,
            R.drawable.Psychic_Icon,
            R.drawable.Tank_Icon,
            R.drawable.Thief_Icon,
            R.drawable.Tinkerer_Icon
    };

    int[] activeCollars = {0,0,0,0,0,0};
    //this stores indices of collars that are already assigned somewhere.

    int[] assignments = {
            0,0,0,
            0,0,0,
            0,0,0,
            0,0,0
    };
    //in case i forget, these are supposed to be individual collar indices for corresponding image views.
    //so assignment[0] == 5 means its img1 and it points to collar[5], which is hunter.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img8 = findViewById(R.id.img8);
        img9 = findViewById(R.id.img9);
        img10 = findViewById(R.id.img10);
        img11 = findViewById(R.id.img11);
        img12 = findViewById(R.id.img12);

        View.OnClickListener click = new View.OnClickListener() {
            public void onClick(View v) {}
        };
    }


    //game initializer. it will randomize active collars and imageview assignments and probably act as a reset.
    private void initGame()
    {
        Random rand = new Random();
        int temp=0;

        //--- determines which collars to use as graphics.
        for (int i=0; i<activeCollars.length; i++)
        {
            do { temp = rand.nextInt(collar.length-1)+1; }
            while (arrContainsVal(activeCollars, temp));

            activeCollars[i]=temp;
            rand = new Random();
        }
        Log.d("collarAssignmentRes", activeCollars.toString());
        //---

        //--- assigns collars to certain fields. could be concatenated with the previous loop, but isn't for clarity reasons... for now.
        for (int i=0; i<activeCollars.length; i++)
        {
            while(true)
            {
                temp = rand.nextInt(assignments.length);
                if(assignments[temp] == 0)
                {
                    assignments[temp] = activeCollars[i];
                    break;
                }
            }

            while(true)
            {
                temp = rand.nextInt(assignments.length);
                if(assignments[temp] == 0)
                {
                    assignments[temp] = activeCollars[i];
                    break;
                }
            }

            rand = new Random();
        }
        //---
    }


    //simple search function that android studio changed from a normal for statement.
    private boolean arrContainsVal(int[] arr, int val)
    {
        for (int j : arr) {
            if (j == val)
                return true;
        }
        return false;
    }

}
