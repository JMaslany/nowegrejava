package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    Button btn;
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

    Handler handler = new Handler();

    boolean btnLock = true;

    int counter = 0;
    int lastIndex = 0;
    int exposedTiles = 0;
    int score = 0;

    int[] collar = {
            R.drawable.collarless_icon,
            R.drawable.butcher_icon,
            R.drawable.cleric_icon,
            R.drawable.druid_icon,
            R.drawable.fighter_icon,
            R.drawable.hunter_icon,
            R.drawable.jester_icon,
            R.drawable.mage_icon,
            R.drawable.monk_icon,
            R.drawable.necromancer_icon,
            R.drawable.psychic_icon,
            R.drawable.tank_icon,
            R.drawable.thief_icon,
            R.drawable.tinkerer_icon
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

    boolean[] isTileExposed = {
            false,false,false,
            false,false,false,
            false,false,false,
            false,false,false,
    };

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

        timer = findViewById(R.id.timer);

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

        View.OnClickListener clickImg = new View.OnClickListener() {
            public void onClick(View v)
            {
                if(!btnLock)
                    GameUpdate(CheckImg(v));
            }
        };

        img1.setOnClickListener(clickImg);
        img2.setOnClickListener(clickImg);
        img3.setOnClickListener(clickImg);
        img4.setOnClickListener(clickImg);
        img5.setOnClickListener(clickImg);
        img6.setOnClickListener(clickImg);
        img7.setOnClickListener(clickImg);
        img8.setOnClickListener(clickImg);
        img9.setOnClickListener(clickImg);
        img10.setOnClickListener(clickImg);
        img11.setOnClickListener(clickImg);
        img12.setOnClickListener(clickImg);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                resetGame();
                initGame();
                initTimer();
            }
        });
    }

    private void GameUpdate(int imgIndex)
    {
        if(!isTileExposed[imgIndex])
        {
            updateImg(imgIndex, collar[assignments[imgIndex]]);
            isTileExposed[imgIndex] = true;
            exposedTiles++;

            if ((assignments[imgIndex] != assignments[lastIndex] || imgIndex == lastIndex) && exposedTiles == 2) {
                btnLock = true;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isTileExposed[imgIndex] = false;
                        isTileExposed[lastIndex] = false;
                        updateImg(imgIndex, collar[0]);
                        updateImg(lastIndex, collar[0]);
                        lastIndex = imgIndex;
                        exposedTiles = 0;
                        btnLock = false;
                    }
                }, 500);

            } else if (assignments[imgIndex] == assignments[lastIndex] && imgIndex != lastIndex && exposedTiles == 2) {
                lastIndex = imgIndex;
                exposedTiles = 0;
                score++;
            } else
            {
                lastIndex = imgIndex;
            }
        }
    }
    //
    //----------------------------------------------------------------------
    //

    //game initializer. it will randomize active collars and imageview assignments and probably act as a reset.
    private void initGame()
    {
        Random rand = new Random();
        int temp=0;
        btnLock = false;

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

    //---------------------------------------------------

    private void resetGame()
    {
        isTileExposed = new boolean[] {
                false,false,false,
                false,false,false,
                false,false,false,
                false,false,false,
        };

        activeCollars = new int[] {0,0,0,0,0,0};
        assignments = new int[] {
                0,0,0,
                0,0,0,
                0,0,0,
                0,0,0
        };

        timer.setText("Czas: 0:0");
        counter = 0;
        score = 0;
        exposedTiles = 0;
        lastIndex = 0;

        img1.setImageResource(collar[0]);
        img2.setImageResource(collar[0]);
        img3.setImageResource(collar[0]);
        img4.setImageResource(collar[0]);
        img5.setImageResource(collar[0]);
        img6.setImageResource(collar[0]);
        img7.setImageResource(collar[0]);
        img8.setImageResource(collar[0]);
        img9.setImageResource(collar[0]);
        img10.setImageResource(collar[0]);
        img11.setImageResource(collar[0]);
        img12.setImageResource(collar[0]);
    }

    //-------------------------------------------------

    private void initTimer()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(score<6)
                {
                    counter++;
                    int mins = counter/60;
                    int sec = counter%60;
                    java.lang.String time = "Czas: " + mins + ":" + sec;
                    timer.setText(time);

                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private int CheckImg(View v)
    {
        if (v.equals(img1))
            return 0;
        else if (v.equals(img2))
            return 1;
        else if (v.equals(img3))
            return 2;
        else if (v.equals(img4))
            return 3;
        else if (v.equals(img5))
            return 4;
        else if (v.equals(img6))
            return 5;
        else if (v.equals(img7))
            return 6;
        else if (v.equals(img8))
            return 7;
        else if (v.equals(img9))
            return 8;
        else if (v.equals(img10))
            return 9;
        else if (v.equals(img11))
            return 10;
        else if (v.equals(img12))
            return 11;
        else
            return 0;
    }

    private void updateImg(int imgIndex, int img)
    {
        switch (imgIndex)
        {
            case 0:
                img1.setImageResource(img);
                break;
            case 1:
                img2.setImageResource(img);
                break;
            case 2:
                img3.setImageResource(img);
                break;
            case 3:
                img4.setImageResource(img);
                break;
            case 4:
                img5.setImageResource(img);
                break;
            case 5:
                img6.setImageResource(img);
                break;
            case 6:
                img7.setImageResource(img);
                break;
            case 7:
                img8.setImageResource(img);
                break;
            case 8:
                img9.setImageResource(img);
                break;
            case 9:
                img10.setImageResource(img);
                break;
            case 10:
                img11.setImageResource(img);
                break;
            case 11:
                img12.setImageResource(img);
                break;
            default:
                break;
        }
    }

    private boolean arrContainsVal(int[] arr, int val)
    {
        for (int j : arr) {
            if (j == val)
                return true;
        }
        return false;
    }

}