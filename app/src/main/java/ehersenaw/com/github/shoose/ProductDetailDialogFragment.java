package ehersenaw.com.github.shoose;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.nhn.android.naverlogin.ui.view.OAuthLoginButton.TAG;

public class ProductDetailDialogFragment extends DialogFragment{
    Bitmap bitmap;
    String shopUrl;
    String Token;
    int pid;
    int SN;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() !=null && getActivity() instanceof TabActivity){
            Token = ((TabActivity)getActivity()).getToken();
            SN = ((TabActivity)getActivity()).getSN();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail_dialog_fragment, container);
        Button closeBtn;
        Button goToBuyBtn;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        RatingBar detailScore = (RatingBar) view.findViewById(R.id.ratingBar);
        TextView detailProduct_SN = (TextView) view.findViewById(R.id.detailProduct_SN);
        TextView detailName = (TextView) view.findViewById(R.id.detailName);
        TextView detailBrand = (TextView) view.findViewById(R.id.detailBrand);
        TextView detailPrice = (TextView) view.findViewById(R.id.detailPrice);
        TextView detailType = (TextView) view.findViewById(R.id.detailType);
        TextView detailPoint = (TextView) view.findViewById(R.id.detailPoint);
        ImageView detailImage = (ImageView) view.findViewById(R.id.product_detail_img);


        Bundle bundle = getArguments();
        if(bundle != null){
            final Product shoose = (Product) bundle.getParcelable("shoose");
            detailProduct_SN.setText("품번 : "+Integer.toString(shoose.pid));
            pid = shoose.pid;
            detailName.setText("품명 : "+shoose.pname);
            detailPrice.setText("가격 : "+Integer.toString(shoose.price));
            detailBrand.setText("상표 : "+shoose.brand);
            detailType.setText("분류 : "+shoose.type);
            detailScore.setRating((float) shoose.user_score);
            detailPoint.setText("평점 : "+Double.toString(shoose.point));
            shopUrl = "http://m.coupang.com/vm/products/17090984?vendorItemId=3111328892";
//            shopUrl = shoose.link;

            Thread imgThread = new Thread(){
                @Override
                public void run() {
                    try {
                        URL url = new URL(shoose.img_url);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is =conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            imgThread.start();

            try {
                imgThread.join();
                detailImage.setImageBitmap(bitmap);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        detailScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                double score = rating;
                //서버연동
                UpdateScoreTask updateScoreTask = new UpdateScoreTask(Token,SN,pid,rating);
                updateScoreTask.execute();
//                shoose.user_score=ratingBar.getRating();
            }
        });

        closeBtn = (Button) view.findViewById(R.id.dialogClose);
        closeBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        goToBuyBtn = (Button) view.findViewById(R.id.goToBuy);
        goToBuyBtn.setOnClickListener(new Button.OnClickListener(){
//"https://stockx.imgix.net/Nike-Air-Max-1-97-Sean-Wotherspoon-NA-Product.jpg?fit=fill&bg=FFFFFF&w=300&h=214&auto=format,compress&trim=color&q=90&dpr=2&updated_at=1538080256"
            @Override
            public void onClick(View v) {
                //서버
                LinkTask linkTask = new LinkTask(Token,SN,pid);
                linkTask.execute();
//                Log.d(TAG, "onClick: shopUrl : "+shopUrl);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopUrl));
//                startActivity(browserIntent);
            }

        });

        return view;
    }


    public class UpdateScoreTask extends AsyncTask<Void,Void,String> {
        private ContentValues values;
        private String token;
        private int SN;
        private int pid;
        private double score;
        UpdateScoreTask(String token,int SN, int pid, double score){
            this.token = token;
            this.SN = SN;
            this.pid = pid;
            this.score = score;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String url = "http://13.125.41.85:3000/api/prod/like";
            values = new ContentValues();
            values.clear();

            values.put("Cookie",token);
            int int_score = (int) score;
            values.put("score",int_score);
            values.put("pid",pid);
            values.put("SN",SN);

            String message;
            RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();

            JSONObject response = requestHTTPURLConnection.request(url, values);
            try {
                message = response.get("message").toString();
                return message;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String message) {
            if(message!=null){
//                Log.d(TAG, "onPostExecute : "+message);
//                if (message.equals("success")){
//                    return;
//                }
            }else{
                Toast.makeText(getContext(),"서버 접속에 실패했습니다.",Toast.LENGTH_SHORT);
                return;
            }
        }
    }

    public class LinkTask extends AsyncTask<Void,Void,String> {
        private ContentValues values;
        private String token;
        private int SN;
        private int pid;
        LinkTask(String token,int SN, int pid){
            this.token = token;
            this.SN = SN;
            this.pid = pid;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String url = "http://13.125.41.85:3000/api/prod/link/";
            url+= pid;
            url+="/"+SN;
//            Log.d(TAG, "doInBackground: ddd : "+url);
            values = new ContentValues();
            values.clear();

            values.put("Cookie",token);

            RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();

            String response = requestHTTPURLConnection.requestByGet(url, values);
            if (response.equals("HTTP_NOT_FOUND")) return response;
            try {
                JSONObject jsonObj_response = new JSONObject(response);
                return jsonObj_response.get("link").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(final String message) {
            if(message!=null){
                Log.d(TAG, "onPostExecute: link_return : "+message);
                if (message.equals("HTTP_NOT_FOUND")){
                    Log.d(TAG, "onPostExecute: RRR");
                    Toast.makeText(getActivity(),"죄송합니다. 현재 상품이 검색되지 않습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                    startActivity(browserIntent);
                }catch (Exception e){
                    Toast.makeText(getActivity(),"서버 접속에 실패했습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
//                }
            }else{
                Toast.makeText(getActivity(),"서버 접속에 실패했습니다.",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

}
