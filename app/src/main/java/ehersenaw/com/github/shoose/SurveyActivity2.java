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
    boolean color[]={false,false,false,false,false,false,false,false,false,false,false,false};
    boolean brand[]={false,false,false,false,false,false,false,false,false,false,false,false};
    boolean colorCheck=false,brandCheck=false;

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
                colorCheck=false;
                brandCheck=false;
                for(int i=0; i<12; i++){
                    if(color[i]==true)
                        colorCheck=true;
                    if(brand[i]==true)
                        brandCheck=true;
                }

                if(colorCheck&&brandCheck) {
                    Intent intent = new Intent(SurveyActivity2.this, SurveyActivity2_1.class);
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
}
