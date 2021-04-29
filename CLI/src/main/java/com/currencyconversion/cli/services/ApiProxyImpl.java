package com.currencyconversion.cli.services;

import com.currencyconversion.cli.models.Currency;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class ApiProxyImpl implements ApiProxy {

    private final String URI = "http://localhost:8010/currency/";

    /**
     * Send a request to RestAPI to get a list of currency conversion
     * @param file is a {@link String}
     * @param targetCurrency is a {@link String}
     * @return a {@link List<Currency>}
     */
    @Override
    public List<Currency> send(String file, String targetCurrency) {
        Gson gson = new Gson();

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URI);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("currency", targetCurrency, ContentType.TEXT_PLAIN);

            File f = new File(file);
            builder.addBinaryBody(
                    "file",
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );

            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = httpClient.execute(httpPost, responseHandler);

            return gson.fromJson(response, new TypeToken<List<Currency>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
