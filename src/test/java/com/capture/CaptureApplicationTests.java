package com.capture;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaptureApplicationTests {

    @Test
    public void contextLoads() {
    }

    //15个ua随机用，减少503的机率
    public static String[] ua = {
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; Intel Mac OS X 10.6; rv:7.0.1) Gecko/20100101 Firefox/7.0.1",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 OPR/18.0.1284.68",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:7.0.1) Gecko/20100101 Firefox/7.0.1",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.9.1) Presto/2.12.388 Version/12.16",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 OPR/18.0.1284.68",
            "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) CriOS/30.0.1599.12 Mobile/11A465 Safari/8536.25",
            "Mozilla/5.0 (iPad; CPU OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4",
            "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53"

    };

    @Test
    public void name() {
        int i = 1;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
       /* ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (i <= 10) {
            Future<String> stringFuture = executorService.submit(() -> {
                try {
                    return httpclientCapture();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            });
            Thread.currentThread().setName("capture" + i);
            i++;
            System.out.println(Thread.currentThread().getName());
            String string = null;
            try {
                string = stringFuture.get();
                StreamUtils.copy(string.getBytes(), new FileOutputStream(new File("a.html")));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();*/
        try {
            httpclientCapture();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private String httpclientCapture() throws IOException {
        InputStream content = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpUriRequest httpUriRequest = new HttpGet("http://www.amazon.cn/s/ref=nb_sb_noss?__mk_zh_CN=%E4%BA%9A%E9%A9%AC%E9%80%8A%E7%BD%91%E7%AB%99&field-keywords=" + "手机");
        String u = ua[Math.abs(new Random().nextInt() % 15)];
        //随机调用ua
        httpUriRequest.addHeader(new BasicHeader("User-Agent", u));
        httpUriRequest.addHeader(new BasicHeader("Host", "www.amazon.cn"));
        httpUriRequest.addHeader(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
        httpUriRequest.addHeader(new BasicHeader("Connection", "keep-alive"));
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpUriRequest);
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        content = httpEntity.getContent();
        return StreamUtils.copyToString(content, Charset.defaultCharset());
    }

    @Test
    public void jdkCapture() {
    }


}
