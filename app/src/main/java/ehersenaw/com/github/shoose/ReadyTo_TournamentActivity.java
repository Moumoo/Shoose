package ehersenaw.com.github.shoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ReadyTo_TournamentActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readyto_tournament);

        Button startbtn = (Button)findViewById(R.id.start_tournament);
        startbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ReadyTo_TournamentActivity.this, Make_TournamentActivity.class);
                startActivity(intent);
            }
        });
    }
}
