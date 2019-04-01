package com.ocean.fastdfs.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class HttpUtil {
	private static Logger log = Logger.getLogger(HttpUtil.class);

	/**
	 * post��ʽ�ύ����
	 * 
	 * @return String
	 * @author xkqu
	 */
	public static String PostData(String url, String params, String enc) {
		String ret = "";
		try {
			URL posturl = new URL(url);
			URLConnection connection = posturl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			OutputStreamWriter out = null;
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			String enp = encode(params, enc);
			log.info("jjjjjjjjjjjjj"+ enp);
			out.write(enp);
			out.flush();
			out.close();
			String line;
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = in.readLine()) != null) {
				ret += line;
			}
			in.close();
			log.info("post data -->" + params + " encode:" + enc);
		} catch (Exception e) {
			log.error("post data " + params + " fail!\n\r" + e);
		}
		return ret;
	}

	/**
	 * get��ʽ�ύ����
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static String GetData(String url, String params) {
		String ret = "";
		try {
			System.out.println("url +========+params====:::" + url + "?" + params);
			URL posturl = new URL(url + "?" + params);
			URLConnection connection = posturl.openConnection();
			connection.setDoInput(true);
			String line;
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = in.readLine()) != null) {
				ret += line;
			}
			in.close();
		} catch (Exception e) {
			log.error("get data " + params + " fail!\n\r" + e);

		}
		return ret;
	}
	
	public static byte[] getByte(String url, String params) throws Exception{
		
		CloseableHttpClient closeableHttpClient=HttpClients.createDefault(); //1、创建实例
        HttpGet httpGet=new HttpGet(url); //2、创建请求
         
        CloseableHttpResponse closeableHttpResponse=closeableHttpClient.execute(httpGet); //3、执行
        HttpEntity httpEntity=closeableHttpResponse.getEntity(); //4、获取实体
         
        if(httpEntity!=null){
            System.out.println("ContentType:"+httpEntity.getContentType().getValue());
            InputStream inputStream=httpEntity.getContent();
            byte[] b = toByteArray(inputStream);
            closeableHttpResponse.close();
            closeableHttpClient.close();
            return b;
        }else{
            closeableHttpResponse.close();
            closeableHttpClient.close();
        	return null;
        }
             
	}
	
	public static byte[] toByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}


	
	/**
	 * ������� xkqu
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String encode(String params, String encode) throws UnsupportedEncodingException {
		if (params == null || "".equals(params))
			return "";
		if (encode == null)
			return params;
		StringTokenizer stk = new StringTokenizer(params, "&");
		StringBuffer bfurl = new StringBuffer();
		for (; stk.hasMoreTokens();) {
			String pd = stk.nextToken();
			if (pd != null && !pd.equals(""))
				if (pd.indexOf("=") > -1) {
					int pos = pd.indexOf("=");
					bfurl.append("&" + pd.substring(0, pos + 1));
					bfurl.append(URLEncoder.encode(pd.substring(pos + 1), encode));
				} else {
					bfurl.append("&" + pd);
				}
		}
		return bfurl.toString().substring(1);
	}
	
	public  static String getIpAddr(HttpServletRequest request)  {
		if(null == request) return null;
        String ip  =  request.getHeader( "X-Real-IP" );
        if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( "x-forwarded-for" );
        }
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( "Proxy-Client-IP" );
        }
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( "WL-Proxy-Client-IP" );
        }
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
           ip  =  request.getRemoteAddr();
       }
        return  ip;
   }
	
	/**  
     * POST请求，Map形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     * @param charset 编码方式  
     */  
    @SuppressWarnings("deprecation")
	public static String sendPost(String url, Map<String, String> param,  
            String charset) {  
  
        StringBuffer buffer = new StringBuffer();  
        if (param != null && !param.isEmpty()) {  
            for (Map.Entry<String, String> entry : param.entrySet()) {  
                buffer.append(entry.getKey()).append("=")  
                        .append(URLEncoder.encode(entry.getValue()))  
                        .append("&");  
  
            }  
        }  
        buffer.deleteCharAt(buffer.length() - 1);  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(buffer);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 
}
