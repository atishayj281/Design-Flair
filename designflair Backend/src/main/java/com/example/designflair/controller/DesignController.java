package com.example.designflair.controller;

import com.example.designflair.model.Image;
import com.example.designflair.model.Prompt;
import com.example.designflair.service.HuggingFaceService;
import com.example.designflair.service.ImageUpscalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/design/v1")
public class DesignController {
    @Autowired
    private HuggingFaceService huggingFaceService;
    @Autowired
    private ImageUpscalerService imageUpscalerService;

    @PostMapping(value = "/get")
    public Image getImage(@RequestBody Prompt prompt) {
        System.out.println(prompt);
        return huggingFaceService.getDesign(prompt);
    }

    @PostMapping(value = "/upscale")
    public Image upscale(@RequestBody Image image) {
        return imageUpscalerService.upscaleImage(image);
    }
}
