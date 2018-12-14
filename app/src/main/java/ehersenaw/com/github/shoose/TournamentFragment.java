package ehersenaw.com.github.shoose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TournamentFragment extends Fragment{
    String Token="";
    int SN=0;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tournament_fragment,container,false);

        return inflater.inflate(R.layout.tournament_fragment,container,false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() !=null && getActivity() instanceof TabActivity){
            Token = ((TabActivity)getActivity()).getToken();
            SN = ((TabActivity)getActivity()).getSN();
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        Button startbtn = (Button)getView().findViewById(R.id.start_tournament);
        startbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), Make_TournamentActivity.class);
                intent.putExtra("Token", Token);
                intent.putExtra("SN",SN);
                startActivity(intent);
            }
        });
    }
}
