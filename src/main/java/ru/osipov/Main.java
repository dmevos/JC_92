package ru.osipov;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static final String NASA_URI = "https://api.nasa.gov/planetary/apod?api_key=m8u98Q5vpv6J6aEJW5nS41X4lgljiwbWk8ECgA23";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(NASA_URI);
        CloseableHttpResponse response = httpClient.execute(request);

        var viewsInfo = mapper.readValue(response.getEntity().getContent(), new TypeReference<NasaClass>() {
        });

        System.out.println(viewsInfo.toString());
        response.close();
        httpClient.close();

        if (viewsInfo.getMediaType().equals("image")) {
            CloseableHttpClient httpClientImage = HttpClients.createDefault();
            HttpGet requestImage = new HttpGet(viewsInfo.getUrl());
            CloseableHttpResponse responseImage = httpClientImage.execute(requestImage);

            var imageEntity = responseImage.getEntity();
            if (imageEntity != null) {
                InputStream imageStream = imageEntity.getContent();
                String imagePath = "NASA_IMAGES\\" + viewsInfo.getTitle().replace(" ", "_") + ".jpg";
                try (FileOutputStream imageFOS = new FileOutputStream(new File(imagePath))) {
                    byte[] inByte = imageStream.readAllBytes();
                    imageFOS.write(inByte, 0, inByte.length);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                String textPath = "NASA_IMAGES\\" + viewsInfo.getTitle().replace(" ", "_") + ".txt";
                try (FileOutputStream textFOS = new FileOutputStream(new File(textPath))) {
                    byte[] buffer = viewsInfo.getExplanation().getBytes();
                    textFOS.write(buffer, 0, buffer.length);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            httpClientImage.close();
            responseImage.close();
            System.out.println("Картинка " + viewsInfo.getTitle().replace(" ", "_") + " и ее описание получены и находятся в 'NASA_IMAGES\\'");
        }
    }
}