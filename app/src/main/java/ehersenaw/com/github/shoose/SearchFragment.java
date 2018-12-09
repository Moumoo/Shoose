package ehersenaw.com.github.shoose;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import static com.nhn.android.naverlogin.ui.view.OAuthLoginButton.TAG;

public class SearchFragment extends Fragment {
    String selected_price_range = "전체";
    String keyword = "";
    String selected_type = "전체";
    String selected_brand = "전체";
    String selected_sort = "인기순";
    String Token="";
    int SN=0;
    JSONParser jsonparser = new JSONParser();
    ArrayList<Product> originProducts = jsonparser.doJSONParse("[]");
    ArrayList<Product> products = originProducts;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() !=null && getActivity() instanceof TabActivity){
            Token = ((TabActivity)getActivity()).getToken();
            SN = ((TabActivity)getActivity()).getSN();
        }
        SearchTask searchTask = new SearchTask(Token);
        searchTask.execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_fragment,container,false);

        //가격대 Spinner
        final Spinner priceRangeSpinner = (Spinner) view.findViewById(R.id.price_range_select_box);
        ArrayAdapter priceRangeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.price_range, android.R.layout.simple_spinner_item);
        priceRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceRangeSpinner.setAdapter(priceRangeAdapter);

        priceRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) priceRangeSpinner.getSelectedItem();
                selected_price_range = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_price_range = "전체";
            }
        });
        //스타일 Spinner
        final Spinner styleSpinner = (Spinner) view.findViewById(R.id.style_select_box);
        ArrayAdapter styleAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.style, android.R.layout.simple_spinner_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);
        styleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) styleSpinner.getSelectedItem();
                selected_type = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_type = "전체";
            }
        });
        //브랜드 Spinner
        final Spinner brandSpinner = (Spinner) view.findViewById(R.id.brand_select_box);
        ArrayAdapter brandAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.brand, android.R.layout.simple_spinner_item);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) brandSpinner.getSelectedItem();
                selected_brand = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_brand = "전체";
            }
        });
        //정렬 Spinner
        final Spinner sortSpinner = (Spinner) view.findViewById(R.id.sort_select_box);
        ArrayAdapter sortAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.sort, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) sortSpinner.getSelectedItem();
                selected_sort = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_sort = "전체";
            }
        });

        //filtering
        products = productsFiltering(products);

        //sorting
        products = productsSorting(products);

        final LinearLayout containerLayout = new LinearLayout(this.getActivity());
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i<products.size();i++){
            CustomProductLayout customProductLayout = new CustomProductLayout(this.getActivity(), products.get(i));
            containerLayout.addView((View)customProductLayout);
        }

        LinearLayout inLayout = (LinearLayout) view.findViewById(R.id.inLayout);
        inLayout.addView(containerLayout);

        //검색창
        LinearLayout search_bar = (LinearLayout) view.findViewById(R.id.search_bar);
        search_bar.setBackgroundColor(Color.GREEN);
        final EditText searchEdit = (EditText)view.findViewById(R.id.search);

        //검색 버튼
        ImageButton search_btn = (ImageButton) view.findViewById(R.id.search_button);
        search_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                keyword = searchEdit.getText().toString();
                products = originProducts;
                //filtering
                products = productsFiltering(originProducts);
                //sorting
                products = productsSorting(products);
                Log.d(TAG, "onClick: "+products.size());
                containerLayout.removeAllViewsInLayout();
                for(int i=0;i<products.size();i++){
                    CustomProductLayout customProductLayout = new CustomProductLayout(getActivity(), products.get(i));
                    containerLayout.addView((View)customProductLayout);
                }
            }
        });

        return view;
    }

    public ArrayList<Product> productsSorting(ArrayList<Product> products){
//        ArrayList<Product> sortedProducts = products;
        switch (selected_sort){
            case "인기순":
                Collections.sort(products, new Comparator<Product>(){
                    @Override
                    public int compare(Product o1, Product o2) {
                        if(o1.point<o2.point) return 1;
                        else if(o1.point>o2.point) return -1;
                        return 0;
                    }
                });
                return products;
            case "낮은 가격순":
                Collections.sort(products, new Comparator<Product>(){
                    @Override
                    public int compare(Product o1, Product o2) {
                        if(o1.price<o2.price) return -1;
                        else if(o1.price>o2.price) return 1;
                        return 0;
                    }
                });
                return products;
            case "높은 가격순":
                Collections.sort(products, new Comparator<Product>(){
                    @Override
                    public int compare(Product o1, Product o2) {
                        if(o1.price<o2.price) return 1;
                        else if(o1.price>o2.price) return -1;
                        return 0;
                    }
                });
                return products;
            case "평가순":
                return products;
            default: return products;
        }
    }

    public ArrayList<Product> productsFiltering(ArrayList<Product> products){
        ArrayList<Product> filteredProducts = products;

        //검색값으로 필터링
        filteredProducts = productsFilteringByKeyword(filteredProducts);
        //가격대로 필터링
        filteredProducts = productsFilteringByPrice(filteredProducts);
        //스타일(type)로 필터링
        filteredProducts = productsFilteringByType(filteredProducts);
        //brand로 필터링
        filteredProducts = productsFilteringByBrand(filteredProducts);
        return filteredProducts;
    };

    public ArrayList<Product> productsFilteringByBrand(ArrayList<Product> products) {
        ArrayList<Product> filteredItems = new ArrayList<Product>();
        if(selected_brand.equals("전체")) return products;
        for(int i=0;i<products.size();i++){
            if (products.get(i).brand.indexOf(selected_brand)>=0){
                filteredItems.add(products.get(i));
            }
        }
        return filteredItems;
    }

    public ArrayList<Product> productsFilteringByType(ArrayList<Product> products) {
        ArrayList<Product> filteredItems = new ArrayList<Product>();
//        Log.d(TAG, "productsFilteringByType1: "+selected_type);
        if(selected_type.equals("전체")) return products;
        for(int i=0;i<products.size();i++){
//            Log.d(TAG, "productsFilteringByType2: "+products.get(i).type);
            if (products.get(i).type.indexOf(selected_type)>=0){
                filteredItems.add(products.get(i));
            }
        }
        return filteredItems;
    }

    public ArrayList<Product> productsFilteringByKeyword(ArrayList<Product> products){
        ArrayList<Product> filteredItems = new ArrayList<Product>();
        if(keyword=="") return products;
        for(int i=0;i<products.size();i++){
            if (products.get(i).pname.indexOf(keyword)>=0){
                filteredItems.add(products.get(i));
            }
        }
        return filteredItems;
    };

    public ArrayList<Product> subProductsFilteringByPrice(ArrayList<Product> products, int row, int high){
        ArrayList<Product> filteredItems = new ArrayList<Product>();
        for(int i=0;i<products.size();i++){
            if (products.get(i).price>=row&&products.get(i).price<=high){
                filteredItems.add(products.get(i));
            }
        }
        return filteredItems;
    };

    public ArrayList<Product> productsFilteringByPrice(ArrayList<Product> products){
        //가격대로 필터링
        switch (selected_price_range){
            case "전체": return products;
            case "50,000원 이하":
                return subProductsFilteringByPrice(products,0,50000);
            case "50,000 ~ 100,000":
                return subProductsFilteringByPrice(products,50000,100000);
            case "100,000 ~ 150,000":
                return subProductsFilteringByPrice(products,100000,150000);
            case "150,000 ~ 200,000":
                return subProductsFilteringByPrice(products,150000,200000);
            case "200,000원 이상":
                return subProductsFilteringByPrice(products,200000,Integer.MAX_VALUE);
            default: return products;
        }
    };

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

//        @Override
//        public void bringToFront() {
//            super.bringToFront();
//        }
//

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
                    FragmentManager fm = getFragmentManager();
                    ProductDetailDialogFragment productDetailDialogFragment = new ProductDetailDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("shoose",shoose);
                    productDetailDialogFragment.setArguments(bundle);
                    productDetailDialogFragment.show(fm,shoose.pname);
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
    }

    public class SearchTask extends AsyncTask<Void, Void, String> {
        private ContentValues values;
        private String token;
        SearchTask(String token) {
            this.token = token;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url= "http://13.125.41.85:3000/api/search/";
            url =url+SN;

            values = new ContentValues();
            values.clear();
            values.put("Cookie",token);

            String message;
            RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();
            JSONObject jsonObj_response = requestHTTPURLConnection.requestByGet(url, values);;
            try {
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
//                Log.d(TAG, "onPostExecute: kdy0962 : "+message);
//                Log.d(TAG, "onPostExecute: size : "+products.size());
            }else{
//                Log.d(TAG, "onPostExecute: kdy0962 : null");
                Toast.makeText(getContext(),"서버 접속에 실패했습니다.",Toast.LENGTH_SHORT);
                return;
            }
        }
    }
}
