package ehersenaw.com.github.shoose;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RequestHTTPURLConnection {
    private static final String TAG = "R_HTTP_URL_CONN";

    public JSONObject request(String _url, ContentValues _params) throws CustomizedException{
        // HttpURLConnection 참조 변수
        HttpURLConnection urlConn = null;
        // URL 뒤에 붙여서 보낼 패러미터
        StringBuffer sbParams = new StringBuffer();
        JSONObject j_obj = new JSONObject();
        /**
         * 1. StringBuffer에 패러미터 연결
         */
        // If data needed to send is nothing, make parameter empty.
        if (_params == null)
            sbParams.append("");
        // If data needed to send exist, fill up parameter.
        else {
            // If num of parameter is bigger than or equal to 2, need '&' for parameter connection.
            // So, create variable for switching.
            boolean isAnd = false;
            // Parameter key and value
            String key;
            String value;

//            boolean flag = false;
//            for (Map.Entry<String,Object> parameter : _params.valueSet()){
//                String flag_key = parameter.getKey();
//                if(flag_key.equals("flag")){
//                    flag = !flag;
//                }
//            }


            for (Map.Entry<String,Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                    // If num of parameter is >= 2, put '&' between parameters.
                    if (isAnd)
                        sbParams.append("&");
                    try {
                        if (key.equals("pid")||key.equals("SN")||key.equals("score")){
                            int int_val = Integer.parseInt(value);
                            j_obj.put(key,int_val);
                        }
//                        else if(key.equals("score")){
//                            double double_val =  Double.parseDouble(value);
//                        }
                        else {
                            j_obj.put(key, value);
                        }
                        Log.d(TAG, "request-key : "+key+", -value : "+ value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // If num of parameter is >= 2, make 'isAnd' to 'true' and start adding '&' from next loop
                    if (!isAnd)
                        if (_params.size() >= 2)
                            isAnd = true;
            }
        }
        /**
         * 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
         */
        try{
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            // [2-1]. urlConn settings
            urlConn.setRequestMethod("POST"); // URL request method: POST
            urlConn.setRequestProperty("Cache-Control", "no-cache");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-charset settings
            urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConn.setRequestProperty("Accept", "application/json");

            if (j_obj.has("Cookie")) {
                String _token = j_obj.get("Cookie").toString();
                _token = "user="+_token;
                urlConn.setRequestProperty("Cookie", _token);
                Log.i("user_token", _token);
                j_obj.remove("Cookie");
            }

            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);

            // [2-2]. parameter delivery and read data
            String strParams = j_obj.toString(); // Save j_obj's Parameters as string.
            Log.i("strParams", strParams);

            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8")); // Print to output stream.
            os.flush(); // Flush the output stream

            String response;
            int responseCode = urlConn.getResponseCode();
            Log.i("responseCode", "responseCode : "+String.format("%d",responseCode));

            os.close(); // Shutdown output stream

            // [2-3]. Server connect request check.
            // If failed, return null and quit method.
            if (responseCode != HttpURLConnection.HTTP_OK) {
                if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
                    // During Sign up
                    throw new CustomizedException("CONFLICT");
                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    // During Log in
                    throw new CustomizedException("NOT_FOUND");
                } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    // Server error
                    throw new CustomizedException("INTERNAL_ERROR");
                }
                return null;
            }

            // [2-4]. Return read result
            // Get result of requested URL to BufferedReader
            //BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            InputStream is = urlConn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            byte[] byteData = null;
            int nLength = 0;
            while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, nLength);
            }
            byteData = baos.toByteArray();

            response = new String(byteData);
            JSONObject responseJSON = new JSONObject(response);
            /*
            String message = (String) responseJSON.get("message");
            String result = (String) responseJSON.get("result");

            Log.i(TAG, "DATA Response = " + response);
            */
            return responseJSON;
        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection()
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }

        return null;
    }

    public String requestByGet(String _url, ContentValues _params) throws CustomizedException{
        // HttpURLConnection 참조 변수
        HttpURLConnection urlConn = null;
        // URL 뒤에 붙여서 보낼 패러미터
        StringBuffer sbParams = new StringBuffer();
        JSONObject j_obj = new JSONObject();

        if (_params == null)
            sbParams.append("");
        else {
            String key;
            String value;

            for (Map.Entry<String,Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();
                try {
                    j_obj.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET"); // URL request method: POST
            urlConn.setRequestProperty("Cache-Control", "no-cache");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-charset settings
            urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setDoInput(true);

            if (j_obj.has("Cookie")) {
                String _token = j_obj.get("Cookie").toString();
                Log.d(TAG, "requestByGet: "+_token);
                _token = "user="+_token;
                urlConn.setRequestProperty("Cookie", _token);
                Log.i("user_token", _token);
                j_obj.remove("Cookie");
            }

            String response;
            int responseCode = urlConn.getResponseCode();
            Log.d("responseCode", String.format("%d",responseCode));

            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "requestByGet: "+responseCode);
                if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
                    // During Sign up
                    throw new CustomizedException("CONFLICT");
                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    // During Log in
                    String err_msg = "HTTP_NOT_FOUND";
                    return err_msg;
//                    throw new CustomizedException("NOT_FOUND");
                } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    // Server error
                    throw new CustomizedException("INTERNAL_ERROR");
                }
                return null;
            }
            InputStream is = urlConn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[300*1024];
            byte[] byteData = null;
            int nLength = 0;
            while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, nLength);
            }
            byteData = baos.toByteArray();

            response = new String(byteData);
            Log.d(TAG, "requestByGet: "+response);
            return response;


        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection()
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }

        return null;
    }
}

class CustomizedException extends RuntimeException {
    String msg;
    public CustomizedException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
