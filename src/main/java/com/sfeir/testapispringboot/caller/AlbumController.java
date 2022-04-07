package com.sfeir.testapispringboot.caller;

import java.util.HashSet;
import java.util.Set;

import com.sfeir.testapispringboot.called.Album;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@RestController
public class AlbumController {

    @Value("${test.api.go.url}")
    private String apiGoUrl;

    @GetMapping(value = "/albums")
    private Set<Album> getAll() {
        String url = apiGoUrl + "/albums";
        RestTemplate restTemplate = new RestTemplate();

        AlbumProxy[] albumsProxy = restTemplate.getForObject(url, AlbumProxy[].class);

        Set<Album> albums = new HashSet<>();
        for (AlbumProxy alp : albumsProxy) {
            albums.add(new Album(alp.id, alp.title, alp.artist, alp.price));
        }

        return albums;
    }

    @GetMapping("/albums/{id}")
    private ResponseEntity<?> getById(@PathVariable int id) {
        String url = apiGoUrl + "/albums/{id}";
        RestTemplate restTemplate = new RestTemplate();
        
        try {
            AlbumProxy alp = restTemplate.getForObject(url, AlbumProxy.class, id);
            return new ResponseEntity<>(new Album(alp.id, alp.title, alp.artist, alp.price), HttpStatus.OK);
        } catch (NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/albums")
    private Album add(@RequestBody Album al) {
        String url = apiGoUrl + "/albums";
        RestTemplate restTemplate = new RestTemplate();

        AlbumProxy newAlp = restTemplate.postForObject(url, new AlbumProxy(al.id, al.title, al.artist, al.price),
                AlbumProxy.class); // return the new album

        return new Album(newAlp.id, newAlp.title, newAlp.artist, newAlp.price);
    }

    @DeleteMapping("/albums/{id}")
    private ResponseEntity<?> deleteById(@PathVariable int id) {
        String url = apiGoUrl + "/albums/{id}";
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(url, id);
        } catch (NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }
}
