package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    String mGender="";
    String mAge="";
    String mSize="";

    String url = "http://13.125.41.85:3000/api/survey/profile";

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
                   mGender=answer[0];
                   mAge=answer[1];
                   mSize=answer[2];

                    new SendTask().execute(url);

                   Intent intent = new Intent(SurveyActivity.this, SurveyActivity2.class);
                    intent.putExtra("Token", mToken);
                    intent.putExtra("SN",mSN);
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
    private class SendTask extends AsyncTask<String, Void, Void> {
        private String Content;
        private int SN=mSN;
        private String Token=mToken,Gender=mGender,Age=mAge,Size=mSize;
        private String Error = null;
        String data = "";
        protected void onPreExecute() {
            try {
                // 요청 파라미터 설정
                data += "&" + URLEncoder.encode("Cookie", "UTF-8") + "=" + Token
                +"&" + URLEncoder.encode("SN", "UTF-8") + "=" + SN
                +"&" + URLEncoder.encode("gender", "UTF-8") + "=" + Gender
                +"&" + URLEncoder.encode("age", "UTF-8") + "=" + Age
                +"&" + URLEncoder.encode("size", "UTF-8") + "=" + Size;
            } catch (UnsupportedEncodingException e) {
                // 에러 표시
                e.printStackTrace();
            }
        }
        // onPreExecute 메소드 이후 호출
        protected Void doInBackground(String... urls) {//클릭이벤트 실행시 파라미터값 urls= LongOperation().execute(serverURL);
            BufferedReader reader = null;
            // 데이터 전송
            try
            {
                // Lab code here....
                //Data를 보낼 URL =
                URL url = new URL(urls[0]);

                //POST data 요청사항을 OutputStreamWriter 를 이용하여 전송
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);//onPreExecute 메소드의 data 변수의 파라미터 내용을 POST 전송명령
                wr.flush();//OutputStreamWriter 버퍼 메모리 비우기
                //PHP 서버 응답값을 변수에 저장
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                //서버 응답변수 reader 에서 내용을 라인단위 문자열로 만듬
                while ((line = reader.readLine()) != null) {
                    //여기가 결과 값 가져오는데!
                    //Log.e("결과",line);
                    Log.e("messgae",line);
                }
                //위 StringBuilder 값을 : sb를 Content 변수에 저장함
                Content = sb.toString();
            } catch (Exception ex)
            {
                Error = ex.getMessage();
            } finally
            {
                try
                {
                    reader.close();
                } catch (Exception ex) {
                }
            }
            return null;
        }
        protected void onPostExecute(Void unused) {
            // UI Element 호출 가능

            //여기서는 후처리

        }
    }
}


