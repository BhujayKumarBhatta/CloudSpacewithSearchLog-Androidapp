package activities.android.theopentutorials.com.cloudspace;

import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Bhujay K Bhatta on 22-05-2016.
 */
public class HTTPwithBasicAuth {
    //public InputStream HTTPtoServer(String url,String...params)throws IOException{
    public String HTTPtoServer(String url,String...params)throws IOException {
        //String url = "http://14.142.104.138/api/v1/cabinet";
        //String webPage = "https://www.google.com/";
        String name = "dcim";
        String password = "dcim";
        String authStr = "dcim"+":"+"dcim";
        System.out.println("Original String is " + authStr);
        byte[] b = authStr.getBytes();
        //encode data on your side using BASE64
        //byte[] bytesEncoded = Base64.encodeBase64(authStr.getBytes());
        byte[] bytesEncoded = Base64.encode(b,Base64.DEFAULT);
        String authEncoded = new String(bytesEncoded);
        URL urlObj=new URL(url);
        StringBuilder sbParamsObj;
        String charset="UTF-8";
        sbParamsObj = new StringBuilder();
        String paramString;
        int len = 200000;
        if (params.length>1)
            for (int i=0;i<params.length;i++){
                try {
                    String key = params[i];
                    String value = params[i + 1];
                    sbParamsObj.append(key).append("=").append(URLEncoder.encode(value, charset));
                }catch (UnsupportedEncodingException e){e.printStackTrace();}
            }

        HttpURLConnection conn = (HttpURLConnection)urlObj.openConnection();
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Accept-Charset", charset);
        conn.setRequestProperty("Accept-Charset", charset);
        conn.setRequestProperty("Authorization", "Basic "+authEncoded);
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.connect();
        InputStream is = conn.getInputStream();
        //Reader reader = new InputStreamReader(is, "UTF-8");
        Reader reader = new InputStreamReader(is, charset);
        char[] buffer = new char[len];
        reader.read(buffer);
        String s= new String(buffer);
        return s;

        //DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        //if (params.length>1)
        //paramString=sbParamsObj.toString();
        //wr.writeBytes(sbParamsObj.toString());//why it is not taking paramString ???
        //wr.flush();
        //wr.close();
        //return conn.getInputStream();
        //InputStream inputstreamObj = new BufferedInputStream(conn.getInputStream());
        //return inputstreamObj;
    }
}
