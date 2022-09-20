package com.aircraft.codelab.pioneer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 2022-03-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class HttpClientUtil {
    /**
     * 从连接池中获取一个连接的时间
     */
    public static final int CONNECTION_REQUEST_TIMEOUT = 5 * 1000;
    /**
     * 与远程服务器建立连接的时间
     */
    public static final int CONNECT_TIMEOUT = 5 * 1000;
    /**
     * 远程服务器返回数据的时间
     */
    public static final int SOCKET_TIMEOUT = 5 * 1000;

    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        // 创建连接池管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(60 * 1000, TimeUnit.MILLISECONDS);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(100);

        SocketConfig socketConfig = SocketConfig.custom()
                .setSoKeepAlive(true)
                // 是否立即发送数据 提高实时性
                .setTcpNoDelay(true)
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();

        /*SSLConnectionSocketFactory socketFactory = null;
        try {
            socketFactory = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                    new String[]{"TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        HTTP_CLIENT = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
//                .setSSLSocketFactory(socketFactory)
                .setDefaultSocketConfig(socketConfig)
                .setDefaultRequestConfig(requestConfig)
                // 302重定向
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
    }

    public static void get() throws IOException {
        HttpGet httpGet = new HttpGet("");

        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet)) {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            }
        }
    }

    public static void post() throws Exception {
        HttpPost httpPost = new HttpPost("");

        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpPost.abort();
                throw new RuntimeException("post request exception,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);
        }
    }
}
