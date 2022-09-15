package com.ttm.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttm.demo.dao.service.ProductService;
import com.ttm.demo.domaine.Product;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = service.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = service.getProduct(id);
        return ResponseEntity.ok(product);
    }

    // read and write json into/from file
    @GetMapping("writeToJsonFile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> getTranslation(){
        Product product = service.getProduct(1L);
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("product.json"))){
            // Serialize Java object info JSON file.
            //mapper.writeValue(new File("product.json"), product);
            writer.write(mapper.writeValueAsString(product));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Deserialize JSON file into Java object.
            Product productFromJson = mapper.readValue(new File("product.json"), Product.class);
            System.out.println("Id = " + productFromJson.getId());
            System.out.println("Label = " + productFromJson.getLabel());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(product);
    }

    // sending back an File containing a DTO
    @GetMapping("translation__")
    public ResponseEntity<InputStreamResource> downloadTranslationFile(@RequestParam(name = "lang") String lang){
        Translation translation;
        if ("en".equals(lang)) {
            translation = Translation.builder()
                    .label("my label from backend").name("my name from backend").count(2)
                    .build();
        }else{
            translation = Translation.builder()
                    .label("mon label a partir du backend").name("mon nom a partir du backend").count(2)
                    .build();
        }
        ObjectMapper mapper = new ObjectMapper();
        byte[] buf;
        try {
            buf = mapper.writeValueAsBytes(translation);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+lang+".json")
                .contentLength(buf.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new ByteArrayInputStream(buf)));
    }

    // sending back an DTO --
    @GetMapping("translation")
    public ResponseEntity<Translation> downloadTranslationFile2(@RequestParam(name = "lang") String lang){
        Translation translation;
        if ("en".equals(lang)) {
            translation = Translation.builder()
                    .label("my label from backend").name("my name from backend").count(2)
                    .build();
        }else{
            translation = Translation.builder()
                    .label("mon label a partir du backend").name("mon nom a partir du backend").count(2)
                    .build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(translation);
    }
}

@Builder
@Data
class Translation {
    private String label;
    private String name;
    private Integer count;
}
