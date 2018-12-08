package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SurveyActivity3 extends AppCompatActivity {
    ListView list;
    String[] names={"신발 1","신발 2", "신발 3","신발 4","신발 5","신발 6","신발 7","신발 8","신발 9",
            "신발 10","신발 11","신발 12","신발 13","신발 14","신발 15", "신발 16"};

    Integer[] images={R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5,R.drawable.s6,
            R.drawable.s7,R.drawable.s8,R.drawable.s9,R.drawable.s10,R.drawable.s11,R.drawable.s12,
            R.drawable.s13,R.drawable.s14,R.drawable.s15,R.drawable.s16};

    float[] ratings = new float[names.length];//rating 기록

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);

        CustomList adapter = new CustomList(SurveyActivity3.this);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Button backbtn=(Button)findViewById(R.id.backButton);
        backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SurveyActivity3.this, SurveyActivity2_1.class);
                startActivity(intent);
            }
        });

        Button finishbtn=(Button)findViewById(R.id.finishButton);
        finishbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SurveyActivity3.this, LoginActivity.class);
                startActivity(intent);
            }

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

            TextView nameView=(TextView)row.findViewById(R.id.name);
            nameView.setText(names[position]);

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
