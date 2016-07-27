package cn.kykys.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuangye on 2016/7/27.
 */
public class HttpTest {

    public void a() {

        try {
            // (1) 创建HttpGet实例
//            HttpGet get = new HttpGet("http://www.126.com");

            // (1)创建查询参数
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("name", "ahopedog"));
            params.add(new BasicNameValuePair("work", "程序员"));
            String queryString = URLEncodedUtils.format(params, "utf-8");

            // (2) 创建Get实例
            URI uri = URIUtils.createURI("http", "localhost", 8080, "/jsx/servlet", queryString, null);

            HttpGet get = new HttpGet(uri);

            // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse
            HttpClient http = new DefaultHttpClient();
            HttpResponse response = http.execute(get);

            // (3) 读取返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                InputStream in = entity.getContent();
                readResponse(in);
            }

        } catch (IOException e) {

        } catch (URISyntaxException e) {

        }
    }

    public void b() {
        try {

            // (1) 创建HttpGet实例
            HttpPost post = new HttpPost("http://www.126.com");

            // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse
            HttpClient http = new DefaultHttpClient();
            HttpResponse response = http.execute(post);

// (3) 读取返回结果
            if (response.getStatusLine().getStatusCode() == 200)

            {
                HttpEntity entity = response.getEntity();

                InputStream in = entity.getContent();
                readResponse(in);
            }
        } catch (IOException e) {
        }
    }

    public static void readResponse(InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {

        }
    }
}
