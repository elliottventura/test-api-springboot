package com.sfeir.testapispringboot.caller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumProxy {

    public String id;
    public String title;
    public String artist;
    public float price;

    public AlbumProxy(){
        
    }

    public AlbumProxy(String id, String title, String artist, float price) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.price = price;
    }

}
