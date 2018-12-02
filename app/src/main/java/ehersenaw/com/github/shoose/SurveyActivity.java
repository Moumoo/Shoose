package ehersenaw.com.github.shoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {
    private EditText text;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        text=(EditText)findViewById(R.id.getSize);

        btn=(Button)findViewById(R.id.nextButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SurveyActivity.this, SurveyActivity2.class);
                startActivity(intent);
            }
        });

    }

    public void onRadioButtonClicked(View view){
        boolean checked=((RadioButton) view).isChecked();
        int getSex=0;

        switch (view.getId()){
            case R.id.man:
                if(checked) {
                    Toast.makeText(getApplicationContext(), ((RadioButton) view).getText(), Toast.LENGTH_SHORT).show();
                    getSex = 1;
                }
                break;
            case R.id.woman:
                if(checked) {
                    Toast.makeText(getApplicationContext(), ((RadioButton) view).getText(), Toast.LENGTH_SHORT).show();
                    getSex = 2;
                }
                break;
        }

        if(text.getText().length()==0){
            Toast.makeText(this, "정확한 값을 입력하시오.", Toast.LENGTH_LONG).show();
            return;
        }

        int getFeetsize= Integer.parseInt(text.getText().toString());

    }
}
