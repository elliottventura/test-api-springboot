package com.sfeir.testapispringboot.caller;

import java.util.HashSet;
import java.util.Set;

import com.sfeir.testapispringboot.called.Album;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    private Album getById(@PathVariable String id){
        String url = apiGoUrl + "/albums/{id}";
        RestTemplate restTemplate = new RestTemplate();
        AlbumProxy alp = restTemplate.getForObject(url, AlbumProxy.class, id);

        return new Album(alp.id, alp.title, alp.artist, alp.price);
    }

    @PostMapping("/albums")
    private Album add(@RequestBody Album al) {
        String url = apiGoUrl + "/albums";
        RestTemplate restTemplate = new RestTemplate();

        AlbumProxy newAlp = restTemplate.postForObject(url, new AlbumProxy(al.id, al.title, al.artist, al.price), AlbumProxy.class); // return the new album

        return new Album(newAlp.id, newAlp.title, newAlp.artist, newAlp.price);
    }
}
