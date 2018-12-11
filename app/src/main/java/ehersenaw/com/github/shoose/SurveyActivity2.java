package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SurveyActivity2 extends AppCompatActivity {
    int surveyResult=0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

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
                Intent intent = new Intent(SurveyActivity2.this, SurveyActivity2_1.class);
                startActivityForResult(intent, surveyResult);
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
            //색상
            case R.id.red:
                if (on) {

                }
                break;
            case R.id.orange:
                if (on) {

                }
                break;
            case R.id.yellow:
                if (on) {

                }
                break;
            case R.id.green:
                if (on) {

                }
                break;
            case R.id.blue:
                if (on) {

                }
                break;
            case R.id.purple:
                if (on) {

                }
                break;
            case R.id.gold:
                if (on) {

                }
                break;
            case R.id.silver:
                if (on) {

                }
                break;
            case R.id.white:
                if (on) {

                }
                break;
            case R.id.gray:
                if (on) {

                }
                break;
            case R.id.black:
                if (on) {

                }
                break;
            case R.id.beige:
                if (on) {

                }
                break;
            //브랜드
            case R.id.nike:
                if (on) {

                }
                break;
            case R.id.adidas:
                if (on) {

                }
                break;
            case R.id.fila:
                if (on) {

                }
                break;
            case R.id.convas:
                if (on) {

                }
                break;
            case R.id.vans:
                if (on) {

                }
                break;
            case R.id.reebok:
                if (on) {

                }
                break;
            case R.id.fuma:
                if (on) {

                }
                break;
            case R.id.racoste:
                if (on) {

                }
                break;
            case R.id.rockport:
                if (on) {

                }
                break;
            case R.id.soda:
                if (on) {

                }
                break;
            case R.id.crocs:
                if (on) {

                }
                break;
            case R.id.goche:
                if (on) {

                }
                break;
        }
    }
}
