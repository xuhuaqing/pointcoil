package com.pointcoil.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author:WuShuang
 * @date:2019/6/10
 * @ver:1.0
 **/
public class PayUtil {
    public static String createSign(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set entries = parameters.entrySet();
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry entry =  (Map.Entry)iterator.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if(null!=v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                sb.append(k+"="+v+"&");
            }
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }
}
