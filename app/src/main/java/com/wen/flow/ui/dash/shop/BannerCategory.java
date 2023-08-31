package com.wen.flow.ui.dash.shop;

import java.util.List;

public class BannerCategory {
    private int type;
    private String typeName;
    private List<Category> categoryList;


    public BannerCategory(int type, String typeName, List<Category> categoryList) {
        this.type = type;
        this.typeName = typeName;
        this.categoryList = categoryList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }



    static class Category {
        private int categoryId;
        private String categoryName;
        private int categoryImg;

        public Category(int categoryId, String categoryName, int categoryImg) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.categoryImg = categoryImg;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(int categoryImg) {
            this.categoryImg = categoryImg;
        }
    }
}
