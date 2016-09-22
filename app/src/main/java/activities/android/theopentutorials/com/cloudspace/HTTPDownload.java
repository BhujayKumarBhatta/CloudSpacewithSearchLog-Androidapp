package activities.android.theopentutorials.com.cloudspace;

import android.net.http.HttpResponseCache;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by acer on 02-06-2016.
 */
public class HTTPDownload  {
    //public InputStream HTTPtoServer(String url,String...params)throws IOException{



    public String HTTPtoServer(String url,String...params)throws IOException {
        //String url = "http://14.142.104.138/api/v1/cabinet";
        //String webPage = "https://www.google.com/";
       //encode data on your side using BASE64
        //byte[] bytesEncoded = Base64.encodeBase64(authStr.getBytes());
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
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
       conn.setUseCaches(true);
        //conn.addRequestProperty("Cache-Control", "no-cache");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if(HttpResponseCache.getInstalled() != null) //enableHttpResponseCache();
            try {
                String cachedResponse = fetchFromHTTPUrlConnectionCache(conn, new URI(url));
                if (cachedResponse != null) {
                    //fetchedFromCache = true;
                    return cachedResponse;
                }
            }catch (Exception e){}

        }

        conn.connect();
        InputStream is = conn.getInputStream();
        //Reader reader = new InputStreamReader(is, "UTF-8");
        Reader reader = new InputStreamReader(is, charset);
        char[] buffer = new char[len];
        reader.read(buffer);
        String s= new String(buffer);
        conn.disconnect();
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
    private String fetchFromHTTPUrlConnectionCache(HttpURLConnection connection, URI uri) {

        try {
            HttpResponseCache responseCache = HttpResponseCache.getInstalled();
            if(responseCache != null){
                CacheResponse cacheResponse = responseCache.get(uri, "GET", connection.getRequestProperties());
                Scanner scanner = new Scanner(cacheResponse.getBody(), "UTF-8");
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNextLine()){
                    sb.append(scanner.nextLine());
                }

                return sb.toString();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
