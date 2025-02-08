package com.yuhao.acp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhao.acp.DTO.CallServiceDTO;
import com.yuhao.acp.model.Key;
import com.yuhao.acp.repository.KeyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KeyRepo keyRepo;

    @GetMapping("/uuid")
    public ModelAndView getUUID() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uuid.html");
        return modelAndView;
    }

    @PostMapping("/valuemanager")
    public ResponseEntity<String> postKeyValueWithRequestParams(@RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value) {
        if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Key> keyData = keyRepo.findById(key);
        if (keyData.isPresent()) {
            keyData.get().setMyValue(value);
            keyRepo.save(keyData.get());
        } else {
            keyRepo.save(new Key(key, value));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/valuemanager/{key}/{value}")
    public ResponseEntity<String> postKeyValueWithPathVariables(@PathVariable(required = false) String key, @PathVariable(required = false) String value) {
        if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Key> keyData = keyRepo.findById(key);
        if (keyData.isPresent()) {
            keyData.get().setMyValue(value);
            keyRepo.save(keyData.get());
        } else {
            keyRepo.save(new Key(key, value));
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/valuemanager/{key}")
    public ResponseEntity<String> deleteKeyWithRequestParams(@PathVariable(required = false) String key) {

        if (key == null || key.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Key> keyData = keyRepo.findById(key);
        if (keyData.isPresent()) {
            keyRepo.delete(keyData.get());
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = {"/valuemanager", "/valuemanager/", "/valuemanager/{key}"})
    public ResponseEntity<String> getKeyWithRequestParams(@PathVariable(required = false) String key) throws JsonProcessingException {

        if (key == null || key.isEmpty()) {
            List<Key> keyData = keyRepo.findAll();
            Map<String, String> response = new LinkedHashMap<>();
            for (Key k : keyData) {
                response.put(k.getMyKey(), k.getMyValue());
            }
            String json = new ObjectMapper().writeValueAsString(response);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
        }


        Optional<Key> keyData = keyRepo.findById(key);
        if (keyData.isPresent()) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(keyData.get().getMyValue());
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping(value = "/callservice", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCallService(@RequestBody(required = false) CallServiceDTO requestBody) {

        if (requestBody == null || requestBody.getExternalBaseUrl().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            URI uri = UriComponentsBuilder.fromUriString(requestBody.getExternalBaseUrl())
                    .path(requestBody.getParameters())
                    .build()
                    .toUri();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(response.getHeaders().getContentType());
            return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
