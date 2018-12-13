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
import android.widget.Toast;
import android.widget.ToggleButton;

public class SurveyActivity2_1 extends AppCompatActivity {
    //POST user data to server(style)

    int surveyResult=0;
    int styleCheck;
    boolean style[] = {false,false,false,false,false,false,false,false,false,false};
    String categoryStyle[]={"슬리퍼","샌들","스니커즈","슬립온","운동화","로퍼/플랫","힐/펌프스","워커","보트슈즈","부츠"};

    int mSN=0;
    String mToken="";
    String mPreferStyle[];

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
                    int order=0;
                    //delete null
                    for(int i=0; i<styleCheck; i++){
                        if(style[i]==true){
                            mPreferStyle[order]=categoryStyle[i];
                            order++;
                        }
                    }

                    final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SurveyActivity2_1.this);
                    alertDialogBuilder.setMessage("상품 평가를 시작합니다");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("네",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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
}
