package org.api.javaspringbootimgbb.controllers;

import lombok.AllArgsConstructor;
import org.api.javaspringbootimgbb.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/create")
    public String uploadImage(@RequestParam("img") MultipartFile file) throws IOException {
        return imageService.uploadImageToImgbb(file);
    }

}
