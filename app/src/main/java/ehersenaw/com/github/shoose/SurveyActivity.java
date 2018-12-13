package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.nhn.android.naverlogin.ui.view.OAuthLoginButton.TAG;

public class SurveyActivity extends AppCompatActivity {
    //POST user data to server(gender, age, size)
    int surveyResult=0;

    Button genderbtn,agebtn,sizebtn;

    String[] answer= new String[3];
    boolean finish;

    int mSN=-1;
    String mToken="";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        //get data from LoginActivity
        Bundle bundle = getIntent().getExtras();
        mSN=bundle.getInt("SN");
        mToken=bundle.getString("Token");

        //init the array
        answer[0]=null;
        answer[1]=null;
        answer[2]=null;

        Button btn=(Button)findViewById(R.id.nextButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish=true;
                for(int i=0; i<3; i++){
                    if(answer[i]==null)
                        finish=false;
                }
                if(!finish)
                    Toast.makeText(SurveyActivity.this,"답변하지 않은 질문이 있습니다",Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(SurveyActivity.this, SurveyActivity2.class);
                    intent.putExtra("Token", mToken);
                    intent.putExtra("SN",mSN);
                    intent.putExtra("gender",answer[0]);
                    intent.putExtra("age",answer[1]);
                    intent.putExtra("size",answer[2]);
                    startActivityForResult(intent, surveyResult);
                }
            }
        });
        genderbtn=(Button)findViewById(R.id.getsex);
        agebtn=(Button)findViewById(R.id.getage);
        sizebtn=(Button)findViewById(R.id.feet);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==surveyResult && resultCode== Activity.RESULT_OK){
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    public void open(View view){
        if(view==genderbtn){
            AlertDialog.Builder genderDialog = new AlertDialog.Builder(this);
            genderDialog.setTitle("성별을 선택하세요");

            final String[] genders = {"남성","여성"};

            genderDialog.setItems(genders, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int size) {
                    Button genderbtn = (Button)findViewById(R.id.getsex);
                    genderbtn.setText(genders[size]);
                    answer[0]=genders[size];
                }
            });
            AlertDialog genderAlert = genderDialog.create();
            genderAlert.show();
        }
        else if(view==agebtn){
            AlertDialog.Builder ageDialog = new AlertDialog.Builder(this);
            ageDialog.setTitle("연령대를 선택하세요");

            final String[] ages = {"10대 이하","10대","20대","30대","40대","50대","60대","60대 이상"};

            ageDialog.setItems(ages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int size) {
                    Button agebtn = (Button)findViewById(R.id.getage);
                    agebtn.setText(ages[size]);
                    answer[1]=ages[size];
                }
            });
            AlertDialog ageAlert = ageDialog.create();
            ageAlert.show();
        }
        else if(view==sizebtn){
            AlertDialog.Builder feetDialog = new AlertDialog.Builder(this);
            feetDialog.setTitle("발 사이즈를 선택하세요");

            final String[] sizes = {"200이하","200", "205","210", "215","220", "225","230","235","240","245",
                    "250","260","265","270","275","280","285","290","295","300이상"};

            feetDialog.setItems(sizes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int size) {
                    Button feetbtn = (Button)findViewById(R.id.feet);
                    feetbtn.setText(sizes[size]);
                    answer[2]=sizes[size];
                }
            });
            AlertDialog alert = feetDialog.create();
            alert.show();
        }
    }
}
