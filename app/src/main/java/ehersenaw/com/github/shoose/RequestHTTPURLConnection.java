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
import java.util.Map;

public class RequestHTTPURLConnection {
    private static final String TAG = "R_HTTP_URL_CONN";

    public JSONObject request(String _url, ContentValues _params) {
        Log.i("tag", "nyang 1");
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

            for (Map.Entry<String,Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                // If num of parameter is >= 2, put '&' between parameters.
                if (isAnd)
                    sbParams.append("&");
                try {
                    j_obj.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //sbParams.append(key).append("=").append(value);

                // If num of parameter is >= 2, make 'isAnd' to 'true' and start adding '&' from next loop
                if (!isAnd)
                    if (_params.size() >= 2)
                        isAnd = true;
            }
        }
        Log.i("tag", "nyang 2");
        /**
         * 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
         */
        try{
            Log.i("tag", "nyang 3");
            URL url = new URL(_url);
            Log.i("tag", "nyang 4");
            urlConn = (HttpURLConnection) url.openConnection();
            Log.i("tag", "nyang 5");
            // [2-1]. urlConn settings
            urlConn.setRequestMethod("POST"); // URL request method: POST
            urlConn.setRequestProperty("Cache-Control", "no-cache");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-charset settings
            urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);

            Log.i("tag", "nyang 6");
            // [2-2]. parameter delivery and read data
            String strParams = j_obj.toString(); // Save j_obj's Parameters as string.
            //Log.i("strParams", strParams);
            /*
            JSONObject job = new JSONObject();
            job.put("ID", "asdfNyang1");
            job.put("password", "asdfPass");
            Log.i("JSONObj", job.toString());
            */
            Log.i("tag", "nyang 7");
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8")); // Print to output stream.
            os.flush(); // Flush the output stream

            String response;
            Log.i("tag", "nyang 8");
            int responseCode = urlConn.getResponseCode();
            //Log.i("responseCode", String.format("%d",responseCode));

            os.close(); // Shutdown output stream

            Log.i("tag", "nyang 9");
            // [2-3]. Server connect request check.
            // If failed, return null and quit method.
            if (responseCode != HttpURLConnection.HTTP_OK)
                return null;

            // [2-4]. Return read result
            // Get result of requested URL to BufferedReader
            //BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            Log.i("tag", "nyang 10");
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
            Log.i("tag", "nyang 11");
            JSONObject responseJSON = new JSONObject(response);
            /*
            String message = (String) responseJSON.get("message");
            String result = (String) responseJSON.get("result");

            Log.i(TAG, "DATA Response = " + response);
            */
            Log.i("tag", "nyang 12");
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
}