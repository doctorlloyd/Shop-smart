package com.example.admin.shopsmart.pojos;

import java.io.Serializable;

public class Image implements Serializable {
    String imageUrl;

    public Image() {
    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
