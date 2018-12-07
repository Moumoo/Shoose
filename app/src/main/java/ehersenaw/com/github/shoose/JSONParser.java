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
