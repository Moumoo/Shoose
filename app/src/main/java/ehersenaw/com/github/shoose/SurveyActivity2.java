package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

public class SurveyActivity2 extends AppCompatActivity {
    int surveyResult=0;
    boolean color[]={false,false,false,false,false,false,false,false,false,false,false,false};
    boolean brand[]={false,false,false,false,false,false,false,false,false,false,false,false};
    int colorCheck,brandCheck;

    String categoryColor[]={"red","orange","yellow","green","blue","purple","gold","silve","white","gray","black","beige"};
    String categorybrand[]={"나이키","아디다스","휠라","컨버스","반스","리복","푸마","라코스테","락포트","소다","크록스","고세"};

    int mSN=0;
    String mToken="";
    String mColor="";
    String mBrand="";
    String url = "http://13.125.41.85:3000/api/survey/profile";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        //receive data from SurveyActivity
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
                colorCheck=0;
                brandCheck=0;
                for(int i=0; i<12; i++){
                    if(color[i]==true)
                        colorCheck++;
                    if(brand[i]==true)
                        brandCheck++;
                }
                if(colorCheck*brandCheck!=0) {
                    for(int i=0; i<12; i++){
                        if(color[i]==true)
                            mColor+=" "+categoryColor[i];
                        if(brand[i]==true)
                            mBrand+=" "+categorybrand[i];
                    }

                    new SurveyActivity2.SendTask().execute(url);

                    Intent intent = new Intent(SurveyActivity2.this, SurveyActivity2_1.class);
                    intent.putExtra("Token", mToken);
                    intent.putExtra("SN",mSN);
                    startActivityForResult(intent, surveyResult);
                }
                else
                    Toast.makeText(SurveyActivity2.this,"답변하지 않은 질문이 있습니다",Toast.LENGTH_SHORT).show();
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
                if(on)
                    color[0]=true;
                else
                    color[0]=false;
                break;
            case R.id.orange:
                if(on)
                    color[1]=true;
                else
                    color[1]=false;
                break;
            case R.id.yellow:
                if(on)
                    color[2]=true;
                else
                    color[2]=false;
                break;
            case R.id.green:
                if(on)
                    color[3]=true;
                else
                    color[3]=false;
                break;
            case R.id.blue:
                if(on)
                    color[4]=true;
                else
                    color[4]=false;
                break;
            case R.id.purple:
                if(on)
                    color[5]=true;
                else
                    color[5]=false;
                break;
            case R.id.gold:
                if(on)
                    color[6]=true;
                else
                    color[6]=false;
                break;
            case R.id.silver:
                if(on)
                    color[7]=true;
                else
                    color[7]=false;
                break;
            case R.id.white:
                if(on)
                    color[8]=true;
                else
                    color[8]=false;
                break;
            case R.id.gray:
                if(on)
                    color[9]=true;
                else
                    color[9]=false;
                break;
            case R.id.black:
                if(on)
                    color[10]=true;
                else
                    color[10]=false;
                break;
            case R.id.beige:
                if(on)
                    color[11]=true;
                else
                    color[11]=false;
                break;
            //브랜드
            case R.id.nike:
                if(on)
                    brand[0]=true;
                else
                    brand[0]=false;
                break;
            case R.id.adidas:
                if(on)
                    brand[1]=true;
                else
                    brand[1]=false;
                break;
            case R.id.fila:
                if(on)
                    brand[2]=true;
                else
                    brand[2]=false;
                break;
            case R.id.convas:
                if(on)
                    brand[3]=true;
                else
                    brand[3]=false;
                break;
            case R.id.vans:
                if(on)
                    brand[4]=true;
                else
                    brand[4]=false;
                break;
            case R.id.reebok:
                if(on)
                    brand[5]=true;
                else
                    brand[5]=false;
                break;
            case R.id.fuma:
                if(on)
                    brand[6]=true;
                else
                    brand[6]=false;
                break;
            case R.id.racoste:
                if(on)
                    brand[7]=true;
                else
                    brand[7]=false;
                break;
            case R.id.rockport:
                if(on)
                    brand[8]=true;
                else
                    brand[8]=false;
                break;
            case R.id.soda:
                if(on)
                    brand[9]=true;
                else
                    brand[9]=false;
                break;
            case R.id.crocs:
                if(on)
                    brand[10]=true;
                else
                    brand[10]=false;
                break;
            case R.id.goche:
                if(on)
                    brand[11]=true;
                else
                    brand[11]=false;
                break;
        }
    }

    private class SendTask extends AsyncTask<String, Void, Void> {
        private String Content;
        private int SN=mSN;
        private String Token=mToken,Color=mColor,Brand=mBrand;
        private String Error = null;
        String data = "";
        protected void onPreExecute() {
            try {
                // 요청 파라미터 설정
                data += "&" + URLEncoder.encode("Cookie", "UTF-8") + "=" + Token
                        +"&" + URLEncoder.encode("SN", "UTF-8") + "=" + SN
                        +"&" + URLEncoder.encode("prefer_color", "UTF-8") + "=" + Color
                        +"&" + URLEncoder.encode("prefer_brand", "UTF-8") + "=" + Brand;
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
