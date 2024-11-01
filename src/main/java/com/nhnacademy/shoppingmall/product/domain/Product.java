package com.nhnacademy.shoppingmall.product.domain;

import java.util.List;

public class Product {
    private int pId;
    private String pName;
    private int pPrice;
    private String thumbnailImage;
    private String detailImage;
    private List<String> categoryNames;   // 카테고리 ID 목록

    public Product(int pId, String pName, int pPrice, String thumbnailImage, String detailImage) {
        this.pId = pId;
        this.pName = pName;
        this.pPrice = pPrice;
        this.thumbnailImage = thumbnailImage;
        this.detailImage = detailImage;
    }

    public int getpId() {
        return pId;
    }

    public String getpName() {
        return pName;
    }

    public int getpPrice() {
        return pPrice;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getDetailImage() {
        return detailImage;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

}
