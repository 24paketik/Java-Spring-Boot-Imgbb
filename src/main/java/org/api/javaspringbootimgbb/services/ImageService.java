package org.api.javaspringbootimgbb.services;

import org.api.javaspringbootimgbb.models.Image;
import org.api.javaspringbootimgbb.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private static final String IMGBB_API_URL = "https://api.imgbb.com/1/upload";
    private static final String IMGBB_API_KEY = "YOUR_IMGBB_API_KEY";

    public String uploadImageToImgbb(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        byte[] fileContent = file.getBytes();
        String encodedString = java.util.Base64.getEncoder().encodeToString(fileContent);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("key", IMGBB_API_KEY);
        body.add("image", encodedString);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(IMGBB_API_URL, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            String imageUrl = jsonNode.path("data").path("url").asText();

            Image image = new Image();
            image.setUrl(imageUrl);
            image.setCreated_at(LocalDateTime.now());
            imageRepository.save(image);

            return imageUrl;
        } else {
            throw new IOException("Failed to upload image to imgbb");
        }
    }
}
