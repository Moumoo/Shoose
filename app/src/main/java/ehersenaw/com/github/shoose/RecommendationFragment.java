package ehersenaw.com.github.shoose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
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

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecommendationFragment extends Fragment{
    String selected_time = "전체";
    String selected_occasion = "전체";
    JSONParser jsonparser = new JSONParser();

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

        final LinearLayout containerLayout = new LinearLayout(this.getActivity());
        LinearLayout inLayout = (LinearLayout) view.findViewById(R.id.inLayout);
        inLayout.addView(containerLayout);

        //추천받기 버튼
        Button recommendation_btn = (Button) view.findViewById(R.id.recommendation_btn);
        recommendation_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<Product> products = jsonparser.doJSONParse("[{'product_SN':1,'type':'부츠','name':'나이키개간지신발1','price':13000,'brand':'나이키','img_url':'https://www.kicksusa.com/media/wysiwyg/brands/nike/Running.jpg','point':'3.0','shop_url':'https://m.sports.naver.com/kbaseball/news/read.nhn?oid=081&aid=0002961772'},{'product_SN':2,'type':'런닝/피트니스화','name':'나이키개간지신발2','price':130000,'brand':'나이키','img_url':'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxO8ny4koS57SX5AMinZmheapT1ayn_OnO7JqYYSVcsTydqVrS','point':'4.0','shop_url':'https://m.sports.naver.com/kbaseball/news/read.nhn?oid=081&aid=0002961772'}]");
                //products filtering
                products = productsFilteringByTime(products);
                products = productsFilteringByOccasion(products);
                containerLayout.setOrientation(LinearLayout.VERTICAL);
                containerLayout.removeAllViewsInLayout();
                for(int i=0;i<products.size();i++){
                    CustomProductLayout customProductLayout = new CustomProductLayout(getActivity(), products.get(i));
                    containerLayout.addView((View)customProductLayout);
                }
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
                    if(products.get(i).type.indexOf("슬리퍼")<0) fillteredItems.add(products.get(i));
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
                    if(products.get(i).type.indexOf("샌들/플리플랍/슬리퍼")<0 && products.get(i).type.indexOf("캔버스/단화")<0 && products.get(i).type.indexOf("농구화")<0 && products.get(i).type.indexOf("런닝/피트니스화")<0) fillteredItems.add(products.get(i));
                }
                return fillteredItems;
            case "캐주얼":
                for(int i=0;i<products.size();i++){
                    if(products.get(i).type.indexOf("구두")<0 && products.get(i).type.indexOf("부츠")<0&& products.get(i).type.indexOf("힐/펌프스")<0 && products.get(i).type.indexOf("로퍼")<0) fillteredItems.add(products.get(i));
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
//                    Toast t = Toast.makeText(getActivity(), shoose.name, Toast.LENGTH_SHORT);
//                    t.show();
                    FragmentManager fm = getFragmentManager();
                    ProductDetailDialogFragment productDetailDialogFragment = new ProductDetailDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("shoose",shoose);
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
}
