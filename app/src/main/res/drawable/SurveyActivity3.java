package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyActivity3 extends AppCompatActivity {
    //GET data from server(pname,img_url) and POST data to server(SN, score)

    ListView list;
    String[] names={"15","95","150","250","300","350","400","500","550","600","700","730","800","900","1000"};

    Integer[] images={R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5,R.drawable.s6,
            R.drawable.s7,R.drawable.s8,R.drawable.s9,R.drawable.s10,R.drawable.s11,R.drawable.s12,
            R.drawable.s13,R.drawable.s14,R.drawable.s15};

    float[] ratings = new float[15];

    boolean finish=true;

    private boolean lastitemFlag=false;

    int mSN=0;
    String mToken="";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);

        //receive data from SurveyActivity2_1
        Bundle bundle = getIntent().getExtras();
        mToken=bundle.getString("Token");
        mSN=bundle.getInt("SN");


        Button backbtn = (Button)findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //평점들 0점으로 초기화
        for(int i=0; i<15; i++){
            ratings[i]=0;
        }

        CustomList adapter = new CustomList(SurveyActivity3.this);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //리스트 뷰 스크롤이 바닥에 닿았을 경우
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE&&lastitemFlag) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyActivity3.this);
                    alertDialogBuilder.setMessage("설문조사를 끝냅니다");
                    alertDialogBuilder.setCancelable(false);

                    finish=true;
                    for(int i=0; i<15; i++){
                        if(ratings[i]==0)
                            finish=false;
                    }
                    if(!finish)
                        Toast.makeText(SurveyActivity3.this,"평가하지 않은 상품이 있습니다",Toast.LENGTH_SHORT).show();
                    else {
                        alertDialogBuilder.setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialogBuilder.setCancelable(true);
                                        setResult(Activity.RESULT_OK);
                                        finish();
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
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastitemFlag=(totalItemCount>0)&&(firstVisibleItem + visibleItemCount>=totalItemCount);
            }
            //totalItemCount==리스트의 전체 갯수
            //visibleItemCount==현재 화면에 보이는 리스트 갯수
            //firstVisibleItem==첫번째 아이템 번호
        });

}

    public class CustomList extends ArrayAdapter<String>{
        private final Activity context;
        public CustomList(Activity context){
            super(context, R.layout.listitem,names);
            this.context=context;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            View row=view;
            if(row==null){
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.listitem,parent,false);
            }
            LayoutInflater inflater=context.getLayoutInflater();

            ImageView imageView=(ImageView)row.findViewById(R.id.image);
            imageView.setImageResource(images[position]);

            RatingBar rb = (RatingBar)row.findViewById(R.id.rating);
            rb.setRating(ratings[position]);
            rb.setTag(position);
            rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
                    if(!fromUser)
                        return;
                    int index = (Integer)(ratingBar.getTag());
                    ratings[index]=rating;
                }
            });

            return row;
        }

    }
}
