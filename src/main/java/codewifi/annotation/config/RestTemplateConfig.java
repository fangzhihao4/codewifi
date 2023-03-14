package codewifi.annotation.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;

@Configuration
@Component
public class RestTemplateConfig {

    private static final int connectTimeOut = 10;

    private static final int readTimeOut = 10;

    @RequestScope
    @Bean("restTemplateNoSsl")
    @Primary
    public RestTemplate restTemplateNoSsl(RestTemplateBuilder restTemplateBuilder,
                                     HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        RestTemplate restTemplate = null;

        restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(connectTimeOut))
                .setReadTimeout(Duration.ofSeconds(readTimeOut)).errorHandler(new FacePlusThrowErrorHandler()).build();
        restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory);
        restTemplate.setErrorHandler(new FacePlusThrowErrorHandler());
        return restTemplate;
    }


    public class FacePlusThrowErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {

        }

    }

    @Bean
    @Primary
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustAllCertificates = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null; // Not relevant.
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // TODO Auto-generated method stub
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // TODO Auto-generated method stub
            }
        }};

        SSLParameters sslParams = new SSLParameters();
        sslParams.setEndpointIdentificationAlgorithm("");
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            assert sc != null;
            sc.init(null, trustAllCertificates, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = HttpClients.custom().setSSLContext(sc).setSSLHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String requestedHost, SSLSession remoteServerSession) {
                return requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost());
            }
        }).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(connectTimeOut * 1000);
        requestFactory.setReadTimeout(readTimeOut * 1000);
        return requestFactory;
    }

}
