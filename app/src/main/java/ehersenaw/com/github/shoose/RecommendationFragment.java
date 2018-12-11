package ehersenaw.com.github.shoose;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.nhn.android.naverlogin.ui.view.OAuthLoginButton.TAG;

public class RecommendationFragment extends Fragment{
    String selected_time = "전체";
    String selected_occasion = "전체";
    JSONParser jsonparser = new JSONParser();
    ArrayList<Product> originProducts = jsonparser.doJSONParse("[]");
    ArrayList<Product> products = originProducts;
    String Token="";
    int SN=0;

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
        final View view = inflater.inflate(R.layout.recommendation_fragment,container,false);

        //time spinner
        final Spinner timeSpinner = (Spinner) view.findViewById(R.id.time_spinner);
        ArrayAdapter timeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.time, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) timeSpinner.getSelectedItem();
                selected_time = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_time = "전체";
            }
        });

        //occasion spinner
        final Spinner occasionSpinner = (Spinner) view.findViewById(R.id.occasion_spinner);
        ArrayAdapter occasionAdapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.occasion,android.R.layout.simple_spinner_item);
        occasionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occasionSpinner.setAdapter(occasionAdapter);

        occasionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) occasionSpinner.getSelectedItem();
                selected_occasion = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_occasion = "전체";
            }
        });

        final LinearLayout containerLayout = new LinearLayout(getActivity());
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout inLayout = (LinearLayout) view.findViewById(R.id.inLayout);
        inLayout.addView(containerLayout);

        //추천받기 버튼
        Button recommendation_btn = (Button) view.findViewById(R.id.recommendation_btn);
        recommendation_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                containerLayout.removeAllViewsInLayout();
                //서버연동
                GetRecommendTask getRecommendTask = new GetRecommendTask(Token, containerLayout);
                getRecommendTask.execute();

            }
        });

        return view;
    }

    public ArrayList<Product> productsFilteringByTime(ArrayList<Product> products){
        ArrayList<Product> fillteredItems = new ArrayList<Product>();
        switch (selected_time){
            case "전체" :
                return products;
            case "여름":
                for(int i=0;i<products.size();i++){
                    if(products.get(i).type.indexOf("부츠")<0) fillteredItems.add(products.get(i));
                }
                return fillteredItems;
            case "겨울":
                for(int i=0;i<products.size();i++){
                    if(products.get(i).type.indexOf("샌들")<0) fillteredItems.add(products.get(i));
                }
                return fillteredItems;
            default: return products;
        }
    }

    public ArrayList<Product> productsFilteringByOccasion(ArrayList<Product> products){
        ArrayList<Product> fillteredItems = new ArrayList<Product>();
        switch (selected_occasion){
            case "전체" :
                return products;
            case "포멀":
                for(int i=0;i<products.size();i++){
                    if(products.get(i).type.indexOf("샌들")<0 && products.get(i).type.indexOf("슬립온")<0 && products.get(i).type.indexOf("런닝화")<0 && products.get(i).type.indexOf("컨버스화")<0) fillteredItems.add(products.get(i));
                }
                return fillteredItems;
            case "캐주얼":
                for(int i=0;i<products.size();i++){
                    if(products.get(i).type.indexOf("구두")<0 && products.get(i).type.indexOf("부츠")<0&& products.get(i).type.indexOf("힐")<0 ) fillteredItems.add(products.get(i));
                }
                return fillteredItems;
            default: return products;
        }
    }

    public class CustomProductLayout extends LinearLayout {

        //        LinearLayout bg;
        TextView product_SN;
        TextView name;
        TextView price;
        TextView brand;
        TextView type;
        ImageView product_img;
        Bitmap bitmap;

        public CustomProductLayout(Context context, Product shoose) {
            super(context);
            initView(shoose);
        }

        public CustomProductLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomProductLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        private void initView(final Product shoose){
            String infService = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
            View v = li.inflate(R.layout.product_layout, this, false);
            addView(v);
            final Product shooseProduct = shoose;

            //layout click
            v.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    LoadDetailTask loadDetailTask = new LoadDetailTask(shoose,Token);
                    loadDetailTask.execute();
                }
            });

            product_SN = (TextView) findViewById(R.id.product_SN);
            name = (TextView) findViewById(R.id.name);
            price = (TextView) findViewById(R.id.price);
            brand = (TextView) findViewById(R.id.brand);
            type = (TextView) findViewById(R.id.type);
            product_img = (ImageView) findViewById(R.id.product_img);

            product_SN.setText("품번 : "+Integer.toString(shoose.pid));
            name.setText("품명 : "+shoose.pname);
            price.setText("가격 : "+Integer.toString(shoose.price)+" 원");
            brand.setText("상표 : "+shoose.brand);
            type.setText("분류 : "+shoose.type);

            Thread imgThread = new Thread(){
                @Override
                public void run() {
                    try {
                        URL url = new URL(shooseProduct.img_url);
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
                product_img.setImageBitmap(bitmap);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        public class LoadDetailTask extends AsyncTask<Void,Void,String> {
            private ContentValues values;
            private String token;
            private Product shoose;

            LoadDetailTask(Product shoose, String token){
                this.shoose = shoose;
                this.token = token;
            }

            @Override
            protected String doInBackground(Void... voids) {
                String url = "http://13.125.41.85:3000/api/prod/like/"+SN+"/"+shoose.pid;
                Log.d(TAG, "doInBackground: ddd : "+url);
                values = new ContentValues();
                values.clear();
                values.put("Cookie",token);

                String message;
                RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();

                String response = requestHTTPURLConnection.requestByGet(url, values);
                return response;
            }

            @Override
            protected void onPostExecute(final String message) {
                if(message!=null){
                    if(message.equals("HTTP_NOT_FOUND")){
                        Log.d(TAG, "onPostExecute: ddd : 0점입니당");
                        shoose.user_score = 0;

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("shoose",shoose);

                        FragmentManager fm = getFragmentManager();
                        ProductDetailDialogFragment productDetailDialogFragment = new ProductDetailDialogFragment();
                        productDetailDialogFragment.setArguments(bundle);
                        productDetailDialogFragment.show(fm,shoose.pname);
                    }
                    else {
                        try {
                            JSONArray jsonArr_response = new JSONArray(message);
                            JSONObject jsonObj = (JSONObject) jsonArr_response.get(0);

                            Log.d(TAG, "onPostExecute: ddd : "+jsonObj.get("score").getClass().getName());
                            switch (jsonObj.get("score").getClass().getName()){
                                case "java.lang.Integer":
                                    shoose.user_score = ((Integer)jsonObj.get("score")).doubleValue();
                                    break;
                                default:
                                    shoose.user_score = (double) jsonObj.get("score");
                                    break;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("shoose",shoose);

                            FragmentManager fm = getFragmentManager();
                            ProductDetailDialogFragment productDetailDialogFragment = new ProductDetailDialogFragment();
                            productDetailDialogFragment.setArguments(bundle);
                            productDetailDialogFragment.show(fm,shoose.pname);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(getActivity(),"서버 접속에 실패했습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }



    }

    public class GetRecommendTask extends AsyncTask<Void, Void, String> {
        private ContentValues values;
        private String token;
        private LinearLayout containerLayout;
        GetRecommendTask(String token, LinearLayout containerLayout) {
            this.token = token;
            this.containerLayout = containerLayout;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url= "http://13.125.41.85:3000/api/recommend/";
            url =url+SN;

            values = new ContentValues();
            values.clear();
            values.put("Cookie",token);

            String message;
            RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();
            String response = requestHTTPURLConnection.requestByGet(url, values);

            try {
                JSONObject jsonObj_response = new JSONObject(response);
                return jsonObj_response.get("items").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Unreachable with normal situation.
            return null;
        }

        @Override
        protected void onPostExecute(final String message) {
            if(message!=null){

                originProducts = jsonparser.doJSONParse(message);
                products = originProducts;
                //products filtering
                products = productsFilteringByTime(products);
                products = productsFilteringByOccasion(products);

                for(int i=0;i<products.size();i++){
                    CustomProductLayout customProductLayout = new CustomProductLayout(getActivity(), products.get(i));
                    containerLayout.addView((View)customProductLayout);
                }
            }else{
                Toast.makeText(getActivity(),"서버 접속에 실패했습니다.",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

}
