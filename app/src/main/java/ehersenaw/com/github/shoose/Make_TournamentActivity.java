package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Make_TournamentActivity extends AppCompatActivity{
    //receive SN from TabActivity
    //GET data from server(pid,img_url) and POST data to server(pid, score)
    //GET link from server and provide it to user

    ImageView imgbtn1, imgbtn2, imgwin;

    Integer[] images={R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5,R.drawable.s6,
            R.drawable.s7,R.drawable.s8,R.drawable.s9,R.drawable.s10,R.drawable.s11,R.drawable.s12,
            R.drawable.s13,R.drawable.s14,R.drawable.s15,R.drawable.s16};//16강


    int index=0, phase_num=1,win_index=-1;
    Integer phase2[]=new Integer[8];
    Integer phase3[]=new Integer[4];
    Integer phase4[]=new Integer[2];

    float winnerScore=0;

    int mSN=0;
    String mToken="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_tournament);

        Bundle bundle = getIntent().getExtras();
        mToken=bundle.getString("Token");
        mSN=bundle.getInt("SN");

        imgbtn1=(ImageView)findViewById(R.id.can1);
        imgbtn2=(ImageView)findViewById(R.id.can2);

        //이미지 생성
        imgbtn1.setImageResource(images[index]);
        imgbtn2.setImageResource(images[index + 1]);

        imgbtn1.setOnTouchListener(imgTouch);
        imgbtn2.setOnTouchListener(imgTouch);
    }
    public View.OnTouchListener imgTouch = new View.OnTouchListener(){
        public boolean onTouch(View view, MotionEvent event) {
            switch (phase_num) {
                case 1:
                    if (view.getId() == R.id.can1)
                        phase2[index / 2] = index;
                    else
                        phase2[index / 2] = index + 1;

                    index = index + 2;

                    if (index == images.length) {
                        index = 0;
                        phase_num++;
                        imgbtn1.setImageResource(images[phase2[index]]);
                        imgbtn2.setImageResource(images[phase2[index + 1]]);

                        return false;
                    }

                    imgbtn1.setImageResource(images[index]);
                    imgbtn2.setImageResource(images[index + 1]);

                    break;

                case 2:
                    if (view.getId() == R.id.can1)
                        phase3[index / 2] = phase2[index];
                    else
                        phase3[index / 2] = phase2[index + 1];

                    index = index + 2;

                    if (index == phase2.length) {
                        index = 0;
                        phase_num++;
                        imgbtn1.setImageResource(images[phase3[index]]);
                        imgbtn2.setImageResource(images[phase3[index + 1]]);

                        return false;
                    }

                    imgbtn1.setImageResource(images[phase2[index]]);
                    imgbtn2.setImageResource(images[phase2[index + 1]]);
                    break;

                case 3:
                    if (view.getId() == R.id.can1)
                        phase4[index / 2] = phase3[index];
                    else
                        phase4[index / 2] = phase3[index + 1];

                    index = index + 2;

                    if (index == phase3.length) {
                        index = 0;
                        phase_num++;
                        imgbtn1.setImageResource(images[phase4[index]]);
                        imgbtn2.setImageResource(images[phase4[index + 1]]);

                        return false;
                    }

                    imgbtn1.setImageResource(images[phase3[index]]);
                    imgbtn2.setImageResource(images[phase3[index + 1]]);
                    break;

                case 4:
                    if (view.getId() == R.id.can1)
                        win_index = phase4[index];
                    else
                        win_index = phase4[index + 1];

                    phase_num++;

                    break;
            }
            if (phase_num == 5) {
                final Dialog winnerDialog = new Dialog(Make_TournamentActivity.this);
                winnerDialog.setContentView(R.layout.dialog_winner);
                winnerDialog.setTitle("토너먼트 우승자");

                winnerDialog.setCanceledOnTouchOutside(false);
                winnerDialog.setCancelable(false);

                imgwin = (ImageView) winnerDialog.findViewById(R.id.winner);
                imgwin.setImageResource(images[win_index]);

                final RatingBar score = (RatingBar) winnerDialog.findViewById(R.id.score);

                score.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if(fromUser)
                            winnerScore=rating;
                    }
                });

                Button linkbtn = (Button) winnerDialog.findViewById(R.id.goshop);
                linkbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //쇼핑몰로 출발!!
                        if(winnerScore>0){
                            winnerDialog.setCancelable(true);
                        }
                        else
                            Toast.makeText(Make_TournamentActivity.this, "점수를 매겨주세요",Toast.LENGTH_SHORT).show();
                    }
                });

                Button closebtn = (Button) winnerDialog.findViewById(R.id.close);
                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(winnerScore>0){
                            winnerDialog.setCancelable(true);
                            finish();
                        }
                        else
                            Toast.makeText(Make_TournamentActivity.this, "점수를 매겨주세요",Toast.LENGTH_SHORT).show();
                    }
                });

                winnerDialog.show();
            }
            return false;
        }
    };

}
