package ehersenaw.com.github.shoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SurveyActivity2 extends AppCompatActivity {
    Button backbtn, nextbtn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        backbtn=(Button)findViewById(R.id.backButton);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(SurveyActivity2.this, SurveyActivity.class);
                startActivity(backIntent);
            }
        });
        nextbtn=(Button)findViewById(R.id.nextButton);
        nextbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent nextIntent = new Intent(SurveyActivity2.this, SurveyActivity3.class);
                startActivity(nextIntent);
            }
        });
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()){
            //선호색상 체크박스
            case R.id.color_red:
                if(checked)
                    Toast.makeText(getApplicationContext(),"빨강색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"빨강색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_orange:
                if(checked)
                    Toast.makeText(getApplicationContext(),"주황색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"주황색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_yellow:
                if(checked)
                    Toast.makeText(getApplicationContext(),"노랑색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"노랑색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_gold:
                if(checked)
                    Toast.makeText(getApplicationContext(),"금색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"금색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_silver:
                if(checked)
                    Toast.makeText(getApplicationContext(),"은색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"은색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_yellowishGreen:
                if(checked)
                    Toast.makeText(getApplicationContext(),"연두색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"연두색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_green:
                if(checked)
                    Toast.makeText(getApplicationContext(),"초록색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"초록색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_skyBlue:
                if(checked)
                    Toast.makeText(getApplicationContext(),"하늘색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"하늘색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_blue:
                if(checked)
                    Toast.makeText(getApplicationContext(),"파랑색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"파랑색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_darkBlue:
                if(checked)
                    Toast.makeText(getApplicationContext(),"남색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"남색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_purple:
                if(checked)
                    Toast.makeText(getApplicationContext(),"보라색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"보라색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_beige:
                if(checked)
                    Toast.makeText(getApplicationContext(),"베이지색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"베이지색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_white:
                if(checked)
                    Toast.makeText(getApplicationContext(),"흰색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"흰색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_gray:
                if(checked)
                    Toast.makeText(getApplicationContext(),"회색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"회색 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_black:
                if(checked)
                    Toast.makeText(getApplicationContext(),"검정색 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"검정색 선택 해제",Toast.LENGTH_SHORT).show();
                break;

            //선호하는 브랜드
            case R.id.brand_nike:
                if(checked)
                    Toast.makeText(getApplicationContext(),"나이키 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"나이키 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_adidas:
                if(checked)
                    Toast.makeText(getApplicationContext(),"아디다스 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"아디다스 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_fila:
                if(checked)
                    Toast.makeText(getApplicationContext(),"휠라 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"휠라 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_converse:
                if(checked)
                    Toast.makeText(getApplicationContext(),"컨버스 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"컨버스 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_vans:
                if(checked)
                    Toast.makeText(getApplicationContext(),"반스 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"반스 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_reebok:
                if(checked)
                    Toast.makeText(getApplicationContext(),"리복 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"리복 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_puma:
                if(checked)
                    Toast.makeText(getApplicationContext(),"푸마 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"푸마 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_lacoste:
                if(checked)
                    Toast.makeText(getApplicationContext(),"라코스테 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"라코스테 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_rockport:
                if(checked)
                    Toast.makeText(getApplicationContext(),"락포트 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"락포트 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_rockfish:
                if(checked)
                    Toast.makeText(getApplicationContext(),"락피쉬 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"락피쉬 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_crocs:
                if(checked)
                    Toast.makeText(getApplicationContext(),"크록스 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"크록스 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.brand_kose:
                if(checked)
                    Toast.makeText(getApplicationContext(),"고세 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"고세 선택 해제",Toast.LENGTH_SHORT).show();
                break;

            //선호하는 신발 타입
            case R.id.type_slippers:
                if(checked)
                    Toast.makeText(getApplicationContext(),"슬리퍼 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"슬리퍼 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_sandals:
                if(checked)
                    Toast.makeText(getApplicationContext(),"샌들 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"샌들 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_sneakers:
                if(checked)
                    Toast.makeText(getApplicationContext(),"스니커즈 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"스니커즈 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_slipon:
                if(checked)
                    Toast.makeText(getApplicationContext(),"슬립온 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"슬립온 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_gym:
                if(checked)
                    Toast.makeText(getApplicationContext(),"운동화 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"운동화 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_loafers:
                if(checked)
                    Toast.makeText(getApplicationContext(),"로퍼 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"로퍼 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_flat:
                if(checked)
                    Toast.makeText(getApplicationContext(),"플랫 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"플랫 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_hill:
                if(checked)
                    Toast.makeText(getApplicationContext(),"힐/펌프스 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"힐/펌프스 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_walkers:
                if(checked)
                    Toast.makeText(getApplicationContext(),"워커 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"워커 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_boat:
                if(checked)
                    Toast.makeText(getApplicationContext(),"보트슈즈 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"보트슈즈 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_welt:
                if(checked)
                    Toast.makeText(getApplicationContext(),"웰트화 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"웰트화 선택 해제",Toast.LENGTH_SHORT).show();
                break;
            case R.id.type_boots:
                if(checked)
                    Toast.makeText(getApplicationContext(),"부츠 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"부츠 선택 해제",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}