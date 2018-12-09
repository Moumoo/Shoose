package ehersenaw.com.github.shoose;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
            product.pid = jobject.getInt("pid");
            product.type = jobject.getString("type");
            product.pname = jobject.getString("pname");
            if(jobject.has("price")) product.price = jobject.getInt("price");
            else product.price = 0;
            product.brand = jobject.getString("brand");
            product.img_url = jobject.getString("img_url");
            if(jobject.has("point")) product.point = jobject.getDouble("point");
            else product.point = 0;
            if(jobject.has("link") &&jobject.getString("link") != null) product.link = jobject.getString("link");
            else product.link = "https://m.sports.naver.com/kbaseball/news/read.nhn?oid=081&aid=0002961772";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }
}
