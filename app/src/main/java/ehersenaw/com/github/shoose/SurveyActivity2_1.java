package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SurveyActivity2_1 extends AppCompatActivity {
    //POST user data to server(style)

    int surveyResult=0;
    int styleCheck;
    boolean style[] = {false,false,false,false,false,false,false,false,false,false};
    String categoryStyle[]={"슬리퍼","샌들","스니커즈","슬립온","운동화","로퍼/플랫","힐/펌프스","워커","보트슈즈","부츠"};

    int mSN=0;
    String mToken="",mType="";

    String url = "http://13.125.41.85:3000/api/survey/profile";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2_1);

        //receive data from SurveyActivity2
        Bundle bundle = getIntent().getExtras();
        mToken=bundle.getString("Token");
        mSN=bundle.getInt("SN");


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
                styleCheck=0;
                for(int i=0; i<10; i++){
                    if(style[i]==true)
                        styleCheck++;
                }
                if(styleCheck==0)
                    Toast.makeText(SurveyActivity2_1.this,"답변하지 않은 질문이 있습니다",Toast.LENGTH_SHORT).show();
                else {
                    final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SurveyActivity2_1.this);
                    alertDialogBuilder.setMessage("상품 평가를 시작합니다");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("네",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for(int i=0; i<10; i++){
                                        if(style[i]==true)
                                            mType+=" "+categoryStyle[i];
                                    }

                                    new SurveyActivity2_1.SendTask().execute(url);

                                    alertDialogBuilder.setCancelable(true);
                                    Intent intent = new Intent(SurveyActivity2_1.this, SurveyActivity3.class);
                                    intent.putExtra("Token", mToken);
                                    intent.putExtra("SN",mSN);
                                    startActivityForResult(intent, surveyResult);
                                }
                            });
                    alertDialogBuilder.setNegativeButton("아니요",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialogBuilder.setCancelable(true);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
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
                if(on)
                    style[0]=true;
                else
                    style[0] = false;
                break;
            case R.id.sandle:
                if(on)
                    style[1]=true;
                else
                    style[1]=false;
                break;
            case R.id.sneakers:
                if(on)
                    style[2]=true;
                else
                    style[2]=false;
                break;
            case R.id.slipon:
                if(on)
                    style[3]=true;
                else
                    style[3]=false;
                break;
            case R.id.gym:
                if(on)
                    style[4]=true;
                else
                    style[4]=false;
                break;
            case R.id.loafer:
                if(on)
                    style[5]=true;
                else
                    style[5]=false;
                break;
            case R.id.hill:
                if(on)
                    style[6]=true;
                else
                    style[6]=false;
                break;
            case R.id.boat:
                if(on)
                    style[7]=true;
                else
                    style[7]=false;
                break;
            case R.id.walker:
                if(on)
                    style[8]=true;
                else
                    style[8]=false;
                break;
            case R.id.boots:
                if(on)
                    style[9]=true;
                else
                    style[9]=false;
                break;
        }
    }
    private class SendTask extends AsyncTask<String, Void, Void> {
        private String Content;
        private int SN=mSN;
        private String Token=mToken,Type=mType;
        private String Error = null;
        String data = "";
        protected void onPreExecute() {
            try {
                // 요청 파라미터 설정
                data += "&" + URLEncoder.encode("Cookie", "UTF-8") + "=" + Token
                        +"&" + URLEncoder.encode("SN", "UTF-8") + "=" + SN
                        +"&" + URLEncoder.encode("prefer_type", "UTF-8") + "=" + Type;
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
