package testCommon;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpsLink {

    public static CloseableHttpClient httpClient(){
        CloseableHttpClient httpclient= HttpClients.custom().build();
        return httpclient;
    }

    public static String httpPost(HttpPost httpost){
        CloseableHttpClient client = null;
        CloseableHttpResponse response= null;
        String responsestr=null;
        client=httpClient();
        try {
            response=client.execute(httpost);
            HttpEntity entity=response.getEntity();
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                if(entity!=null){
                    responsestr= EntityUtils.toString(entity,"utf-8");
                    EntityUtils.consume(entity);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  responsestr;

    }

    public static String httpsPOST(String Url, Map<String,String> mapbodys){
        HttpURLConnection con=null;
        HttpPost httppost = new HttpPost(Url);
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();

        if(mapbodys.isEmpty()){
            for(String key : mapbodys.keySet()){
//                meBuilder.addPart(key,new StringBody(mapbodys.get(key), ContentType.create("text/plain","utf-8")));
                meBuilder.addTextBody(key,mapbodys.get(key));
            }

        }
        HttpEntity entity=meBuilder.build();
        httppost.setEntity(entity);
        return httpPost(httppost);
    }

    public static String HttpGet(String url){
        HttpGet httpget=new HttpGet(url);
        CloseableHttpClient client= httpClient();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response=client.execute(httpget);
            HttpEntity entity= response.getEntity();
            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                if (entity!=null){
                    result=EntityUtils.toString(entity,"utf-8");
                    EntityUtils.consume(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] arg){
        String url = "xxx/api/chushou-login.htm";
        String urlget = "xxx/api/timestamp/get.htm";
        Map<String,String> map = new HashMap<String, String>();
        map.put("username","xxx");
        map.put("password","xxxx");
        map.put("_appkey","CSAndroid");
        map.put("_appVersion","6.0.1");
        map.put("_t", HttpGet(url));
        String result = httpsPOST(url,map);
        JSONObject newd =JSONObject.fromObject(result);
        System.out.println(result);
        System.out.println(newd.getString("code"));
        Iterator intnext = newd.keys();
        while ( intnext.hasNext()){
            String keys = String.valueOf(intnext.next());
            System.out.println(keys);
            System.out.println(newd.getString(keys));
        }

    }
}
