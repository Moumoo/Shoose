package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class SurveyActivity2_1 extends AppCompatActivity {
    int surveyResult=0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2_1);

        Button backbtn=(Button)findViewById(R.id.backButton);
        backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        Button nextbtn=(Button)findViewById(R.id.nextButton);
        nextbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SurveyActivity2_1.this);
                alertDialogBuilder.setMessage("상품 평가를 시작합니다");
                alertDialogBuilder.setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SurveyActivity2_1.this, SurveyActivity3.class);
                                startActivityForResult(intent, surveyResult);
                            }
                        });
                alertDialogBuilder.setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==surveyResult && resultCode==Activity.RESULT_OK){
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        switch (view.getId()) {
            case R.id.slipper:
                if (on) {

                }
                break;
            case R.id.sandle:
                if (on) {

                }
                break;
            case R.id.sneakers:
                if (on) {

                }
                break;
            case R.id.slipon:
                if (on) {

                }
                break;
            case R.id.gym:
                if (on) {

                }
                break;
            case R.id.loafer:
                if (on) {

                }
                break;
            case R.id.hill:
                if (on) {

                }
                break;
            case R.id.boat:
                if (on) {

                }
                break;
            case R.id.walker:
                if (on) {

                }
                break;
            case R.id.boots:
                if (on) {

                }
                break;
        }
    }
}
