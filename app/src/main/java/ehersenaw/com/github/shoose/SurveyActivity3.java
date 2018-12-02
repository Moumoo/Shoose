package ehersenaw.com.github.shoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SurveyActivity3 extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);

        Button btn=(Button)findViewById(R.id.finishButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SurveyActivity3.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onClick(View view){
        Toast.makeText(getApplicationContext(),"설문조사가 끝났습니다",Toast.LENGTH_SHORT).show();
    }

    /*
   후에 데이터 연동
    ListView list;
    String[] names={
            "샌들",
            "슬리퍼",
            "슬립온",
            "운동화",
            "워커"
    };

    Integer[] images={
            R.drawable.sandle,
            R.drawable.slippers,
            R.drawable.slip,
            R.drawable.gym,
            R.drawable.walkers
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);
        CustomList adapter = new CustomList(SurveyActivity3.this);
        list=(ListView)findViewById(R.id.list_item);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),names[+position],Toast.LENGTH_SHORT).show();
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

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context){
            super(context, R.layout.list_preprefer, names);
            this.context=context;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_preprefer,null,true);
            ImageView imageView=(ImageView) rowView.findViewById(R.id.image);
            TextView name = (TextView) rowView.findViewById(R.id.name);
            name.setText(names[position]);
            imageView.setImageResource(images[position]);
            return rowView;
        }
    }*/
}
