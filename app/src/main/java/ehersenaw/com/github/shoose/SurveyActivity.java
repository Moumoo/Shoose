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

public class SurveyActivity extends AppCompatActivity {
    int surveyResult=0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Button btn=(Button)findViewById(R.id.nextButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SurveyActivity.this, SurveyActivity2.class);
                startActivityForResult(intent, surveyResult);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==surveyResult && resultCode== Activity.RESULT_OK){
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        switch (view.getId()) {
            //성별
            case R.id.man:
                if (on) {

                }
                break;
            case R.id.woman:
                if (on) {

                }
                break;
            //나이
            case R.id.one:
                if (on) {

                }
                break;
            case R.id.ten:
                if (on) {

                }
                break;
            case R.id.twenty:
                if (on) {

                }
                break;
            case R.id.thirty:
                if (on) {

                }
                break;
            case R.id.forty:
                if (on) {

                }
                break;
            case R.id.fifty:
                if (on) {

                }
                break;
            //사이즈
            case R.id.feet:
                if (on) {

                }
                break;
        }
    }

    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("발 사이즈를 선택하세요");

        final CharSequence[] sizes = {"200이하","200", "205","210", "215","220", "225","230","235","240","245",
                "250","260","265","270","275","280","285","290","295","300이상"};

        alertDialogBuilder.setItems(sizes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int size) {
                Button feetbtn = (Button)findViewById(R.id.feet);
                feetbtn.setText(sizes[size]);
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
