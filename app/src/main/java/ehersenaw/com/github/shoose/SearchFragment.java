package ehersenaw.com.github.shoose;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.nhn.android.naverlogin.ui.view.OAuthLoginButton.TAG;

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment,container,false);
        //검색창
        LinearLayout search_bar = (LinearLayout) view.findViewById(R.id.search_bar);
        search_bar.setBackgroundColor(Color.GREEN);
        //가격대 Spinner
        Spinner priceRangeSpinner = (Spinner) view.findViewById(R.id.price_range_select_box);
        ArrayAdapter priceRangeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.price_range, android.R.layout.simple_spinner_item);
        priceRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceRangeSpinner.setAdapter(priceRangeAdapter);
        //스타일 Spinner
        Spinner styleSpinner = (Spinner) view.findViewById(R.id.style_select_box);
        ArrayAdapter styleAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.style, android.R.layout.simple_spinner_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);
        //브랜드 Spinner
        Spinner brandSpinner = (Spinner) view.findViewById(R.id.brand_select_box);
        ArrayAdapter brandAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.brand, android.R.layout.simple_spinner_item);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);

        //정렬 Spinner
        Spinner sortSpinner = (Spinner) view.findViewById(R.id.sort_select_box);
        ArrayAdapter sortAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.sort, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        JSONParser jsonparser = new JSONParser();
        ArrayList<Product> products = jsonparser.doJSONParse("[{'product_SN':1,'type':'sneakers','name':'나이키개간지신발1','price':13000,'brand':'nike','img_url':'https://www.kicksusa.com/media/wysiwyg/brands/nike/Running.jpg'},{'product_SN':2,'type':'sneakers','name':'나이키개간지신발2','price':130000,'brand':'nike','img_url':'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxO8ny4koS57SX5AMinZmheapT1ayn_OnO7JqYYSVcsTydqVrS'}]");
        LinearLayout containerLayout = new LinearLayout(this.getActivity());
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i<products.size();i++){
            CustomProductLayout customProductLayout = new CustomProductLayout(this.getActivity(), products.get(i));
            containerLayout.addView((View)customProductLayout);
        }

        LinearLayout inLayout = (LinearLayout) view.findViewById(R.id.inLayout);
        inLayout.addView(containerLayout);


        return view;

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

        private void initView(Product shoose){
            String infService = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
            View v = li.inflate(R.layout.product_layout, this, false);
            addView(v);
            final Product shooseProduct = shoose;

            product_SN = (TextView) findViewById(R.id.product_SN);
            name = (TextView) findViewById(R.id.name);
            price = (TextView) findViewById(R.id.price);
            brand = (TextView) findViewById(R.id.brand);
            type = (TextView) findViewById(R.id.type);
            product_img = (ImageView) findViewById(R.id.product_img);

            product_SN.setText(Integer.toString(shoose.product_SN));
            name.setText(shoose.name);
            price.setText(Integer.toString(shoose.price));
            brand.setText(shoose.brand);
            type.setText(shoose.type);

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
//                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//                        Bitmap bm = BitmapFactory.decodeStream(bis);
//                        bis.close();

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

//        public void getAttrs(AttributeSet attrs, Product shoose){
//            TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.productLayout);
//            setTypeArray(typeArray, shoose);
//        }
//
//        public void getAttrs(AttributeSet attrs, int defStyle, Product shoose){
//            TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.productLayout, defStyle, 0);
//            setTypeArray(typeArray, shoose);
//        }

//        private void setTypeArray(TypedArray typedArray, Product shoose){
//            //http://gun0912.tistory.com/38
////            int symbol_resID = typedArray.getResourceId(R.styleable.LoginButton_symbol, R.drawable.login_naver_symbol);
////            symbol.setImageResource(symbol_resID);
//
////            int product_SN_resID = typedArray.getResourceId(R.styleable.productLayout_product_SN, 0);
//
//            String shoose_product_SN = Integer.toString(shoose.product_SN);
//            product_SN.setText(shoose_product_SN);
//
//            String shoose_name = shoose.name;
//            name.setText(shoose_name);
//
//            String shoose_price = Integer.toString(shoose.price);
//            price.setText(shoose_price);
//
//            String shoose_brand = shoose.brand;
//            brand.setText(shoose_brand);
//
//            String shoose_type = shoose.type;
//            type.setText(shoose_type);
//
//            typedArray.recycle();
//        }

    }

    public class JSONParser{
        JSONArray jarray;

        public JSONParser() {
        }

        public ArrayList<Product> doJSONParse(String data){
            ArrayList<Product> products = new ArrayList<Product>();
            try {
                jarray = new JSONArray(data);
                for(int i=0;i<jarray.length();i++){
                    JSONObject jobject = jarray.getJSONObject(i);
                    Product product = new Product();
                    products.add(putProdcut(jobject, product));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return products;
        };

        public Product putProdcut(JSONObject jobject, Product product) {
            try {
                product.product_SN = jobject.getInt("product_SN");
                product.type = jobject.getString("type");
                product.name = jobject.getString("name");
                product.price = jobject.getInt("price");
                product.brand = jobject.getString("brand");
                product.img_url = jobject.getString("img_url");
//                Log.d(TAG, "putProdcut1 : "+product.name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return product;
        }
    }

    public class Product {
        int product_SN = 0;
        String type = "aaa";
        String name = "bbb";
        int price = 0;
        String brand = "ccc";
        String img_url = "https://stockx.imgix.net/Nike-Air-Max-1-97-Sean-Wotherspoon-NA-Product.jpg?fit=fill&bg=FFFFFF&w=300&h=214&auto=format,compress&trim=color&q=90&dpr=2&updated_at=1538080256";

        public Product() {
        }

        public Product(int product_SN, String type, String name, int price, String brand, String img_url) {
            this.product_SN = product_SN;
            this.type = type;
            this.name = name;
            this.price = price;
            this.brand = brand;
            this.img_url = img_url;
        }
    }
}
