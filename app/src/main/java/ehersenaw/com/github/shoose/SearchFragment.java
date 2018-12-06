package ehersenaw.com.github.shoose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import static com.nhn.android.naverlogin.ui.view.OAuthLoginButton.TAG;

public class SearchFragment extends Fragment {
    String selected_price_range = "전체";
    String keyword = "";
    String selected_type = "전체";
    String selected_brand = "전체";
    String selected_sort = "인기순";
    JSONParser jsonparser = new JSONParser();
    ArrayList<Product> originProducts = jsonparser.doJSONParse("[{'product_SN':1,'type':'부츠','name':'나이키개간지신발1','price':13000,'brand':'나이키','img_url':'https://www.kicksusa.com/media/wysiwyg/brands/nike/Running.jpg','point':'3.0','shop_url':'https://m.sports.naver.com/kbaseball/news/read.nhn?oid=081&aid=0002961772'},{'product_SN':2,'type':'런닝/피트니스화','name':'나이키개간지신발2','price':130000,'brand':'나이키','img_url':'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxO8ny4koS57SX5AMinZmheapT1ayn_OnO7JqYYSVcsTydqVrS','point':'4.0','shop_url':'https://m.sports.naver.com/kbaseball/news/read.nhn?oid=081&aid=0002961772'}]");
    ArrayList<Product> products = originProducts;


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
            if (products.get(i).name.indexOf(keyword)>=0){
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
//                    Toast t = Toast.makeText(getActivity(), shoose.name, Toast.LENGTH_SHORT);
//                    t.show();
                    FragmentManager fm = getFragmentManager();
                    ProductDetailDialogFragment productDetailDialogFragment = new ProductDetailDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("shoose",shoose);
//                    bundle.putSerializable("shoose",shoose);
                    productDetailDialogFragment.setArguments(bundle);
                    productDetailDialogFragment.show(fm,shoose.name);
                }
            });

            product_SN = (TextView) findViewById(R.id.product_SN);
            name = (TextView) findViewById(R.id.name);
            price = (TextView) findViewById(R.id.price);
            brand = (TextView) findViewById(R.id.brand);
            type = (TextView) findViewById(R.id.type);
            product_img = (ImageView) findViewById(R.id.product_img);

            product_SN.setText("품번 : "+Integer.toString(shoose.product_SN));
            name.setText("품명 : "+shoose.name);
            price.setText("가격 : "+Integer.toString(shoose.price)+" 원");
            brand.setText("상표 : "+shoose.brand);
            type.setText("분류 : "+shoose.type+"/5.0");

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
                product.point = jobject.getDouble("point");
                product.shop_url = jobject.getString("shop_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return product;
        }
    }

    public class Product implements Parcelable{
        int product_SN = 0;
        String type = "aaa";
        String name = "bbb";
        int price = 0;
        String brand = "ccc";
        String img_url = "https://stockx.imgix.net/Nike-Air-Max-1-97-Sean-Wotherspoon-NA-Product.jpg?fit=fill&bg=FFFFFF&w=300&h=214&auto=format,compress&trim=color&q=90&dpr=2&updated_at=1538080256";
        double point;
        String shop_url = "http://m.coupang.com/vm/products/17090984?vendorItemId=3111328892";

        public Product() {
        }

        public Product(int product_SN, String type, String name, int price, String brand, String img_url,double point, String shop_url) {
            this.product_SN = product_SN;
            this.type = type;
            this.name = name;
            this.price = price;
            this.brand = brand;
            this.img_url = img_url;
            this.point = point;
            this.shop_url = shop_url;
        }

        protected Product(Parcel in) {
            product_SN = in.readInt();
            type = in.readString();
            name = in.readString();
            price = in.readInt();
            brand = in.readString();
            img_url = in.readString();
            point = in.readDouble();
            shop_url = in.readString();
        }

        public final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(product_SN);
            dest.writeString(type);
            dest.writeString(name);
            dest.writeInt(price);
            dest.writeString(brand);
            dest.writeString(img_url);
            dest.writeDouble(point);
            dest.writeString(shop_url);
        }
    }
}
