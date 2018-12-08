package ehersenaw.com.github.shoose;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SurveyActivity2_1 extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2_1);

        Button backbtn=(Button)findViewById(R.id.backButton);
        backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SurveyActivity2_1.this, SurveyActivity2.class);
                startActivity(intent);
            }
        });

        Button nextbtn=(Button)findViewById(R.id.nextButton);
        nextbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SurveyActivity2_1.this, SurveyActivity3.class);
                startActivity(intent);
            }
        });

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
