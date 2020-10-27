package com.pointcoil.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by WuShuang on 2019/11/18.
 */
@Slf4j
public class MapUtils {


    public static net.sf.json.JSONObject getCity(String longitude,String latitude) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL getUrl = new URL("http://api.map.baidu.com/geocoder?output=json&location="+latitude+",%20"+longitude+"&key=vsxPM6UOP11eZgHiAkI1Rb3qR9kRo0dt");
            connection= (HttpURLConnection) getUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line =reader.readLine()) !=null) {
                builder.append(line);
            }
            if (log.isDebugEnabled())
                log.debug(builder.toString());
            //net.sf.json.JSONObject jsonObj =net.sf.json.JSONObject.fromObject(builder.toString());
            net.sf.json.JSONArray newArray =net.sf.json.JSONArray.fromObject("["+builder.toString()+"]");
            net.sf.json.JSONObject obj = (net.sf.json.JSONObject)newArray.get(0);
            net.sf.json.JSONObject result = (net.sf.json.JSONObject)obj.get("result");
            net.sf.json.JSONObject addressComponent =(net.sf.json.JSONObject)result.get("addressComponent");
            //String city =(String)addressComponent.get("city");
            return addressComponent;

        }catch(Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }finally{
            try {
                reader.close();
            }catch(IOException e) {
                e.printStackTrace();
            }finally{
                connection.disconnect();
            }
        }
        return null;
    }

}
