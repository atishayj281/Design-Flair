package com.example.designflair.service;

import com.example.designflair.Constants;
import com.example.designflair.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
@Service
public class ImageUpscalerServiceImpl implements ImageUpscalerService{

    @Autowired
    private WebClient.Builder esrganClientBuilder;

    public Image upscaleImage(Image inputImage) {
        try {

            // Save input image to a temporary file
            Path tempInputFile = Files.createTempFile("input", ".jpeg");
            Files.copy(new ByteArrayResource(inputImage.getData()).getInputStream(), tempInputFile, StandardCopyOption.REPLACE_EXISTING);

            // Prepare the request
            MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
            bodyBuilder.part("image", new ByteArrayResource(inputImage.getData()))
                    .filename("input.jpeg")
                    .contentType(MediaType.IMAGE_JPEG);

            // Make the request to the Flask API
            byte[] result = esrganClientBuilder.build().post()
                    .uri(Constants.ESRGAN_UPSCALE_ENDPOINT)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block(); // block() for simplicity; consider using reactive programming instead
            // Delete the temporary input file
            Files.delete(tempInputFile);

            return new Image(result);
        } catch (IOException e) {
            throw new RuntimeException("Error processing image", e);
        }
    }
}
