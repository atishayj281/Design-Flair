package com.example.designflair.service;

import com.example.designflair.model.Image;
import com.example.designflair.model.Prompt;

public interface HuggingFaceService {
    public Image getDesign(Prompt prompt);
}
