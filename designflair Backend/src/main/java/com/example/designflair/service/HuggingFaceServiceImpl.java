package com.example.designflair.service;

import com.example.designflair.Constants;
import com.example.designflair.config.WebClientConfig;
import com.example.designflair.model.Image;
import com.example.designflair.model.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HuggingFaceServiceImpl implements HuggingFaceService{
    @Autowired
    private WebClient.Builder designClientBuilder;
    @Autowired
    private ImageUpscalerService imageUpscalerService;

    @Override
    public Image getDesign(Prompt prompt) {
        byte[] res = designClientBuilder.build().post().uri(Constants.DESIGN_FLAIR_ENDPOINT)
                .bodyValue(prompt)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        return new Image(res);
    }
}
