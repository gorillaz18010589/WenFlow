package com.wen.flow.network.response;

import java.io.Serializable;
import java.util.List;

public class Mongodb implements Serializable {

    /**
     * error : 0
     * total : 80
     * page : 1
     * books : [{"title":"MongoDB in Action, 2nd Edition","subtitle":"Covers MongoDB version 3.0","isbn13":"9781617291609","price":"$19.99","image":"https://itbook.store/img/books/9781617291609.png","url":"https://itbook.store/books/9781617291609"},{"title":"MongoDB and Python","subtitle":"Patterns and processes for the popular document-oriented database","isbn13":"9781449310370","price":"$6.88","image":"https://itbook.store/img/books/9781449310370.png","url":"https://itbook.store/books/9781449310370"},{"title":"Building Node Applications with MongoDB and Backbone","subtitle":"Rapid Prototyping and Scalable Deployment","isbn13":"9781449337391","price":"$4.85","image":"https://itbook.store/img/books/9781449337391.png","url":"https://itbook.store/books/9781449337391"},{"title":"Practical MongoDB","subtitle":"Architecting, Developing, and Administering MongoDB","isbn13":"9781484206485","price":"$41.65","image":"https://itbook.store/img/books/9781484206485.png","url":"https://itbook.store/books/9781484206485"},{"title":"The Definitive Guide to MongoDB, 3rd Edition","subtitle":"A complete guide to dealing with Big Data using MongoDB","isbn13":"9781484211830","price":"$49.99","image":"https://itbook.store/img/books/9781484211830.png","url":"https://itbook.store/books/9781484211830"},{"title":"MongoDB Performance Tuning","subtitle":"Optimizing MongoDB Databases and their Applications","isbn13":"9781484268780","price":"$34.74","image":"https://itbook.store/img/books/9781484268780.png","url":"https://itbook.store/books/9781484268780"},{"title":"Pentaho Analytics for MongoDB","subtitle":"Combine Pentaho Analytics and MongoDB to create powerful analysis and reporting solutions","isbn13":"9781782168355","price":"$16.99","image":"https://itbook.store/img/books/9781782168355.png","url":"https://itbook.store/books/9781782168355"},{"title":"Pentaho Analytics for MongoDB Cookbook","subtitle":"Over 50 recipes to learn how to use Pentaho Analytics and MongoDB to create powerful analysis and reporting solutions","isbn13":"9781783553273","price":"$44.99","image":"https://itbook.store/img/books/9781783553273.png","url":"https://itbook.store/books/9781783553273"},{"title":"Web Development with MongoDB and NodeJS, 2nd Edition","subtitle":"Build an interactive and full-featured web application from scratch using Node.js and MongoDB","isbn13":"9781785287527","price":"$39.99","image":"https://itbook.store/img/books/9781785287527.png","url":"https://itbook.store/books/9781785287527"},{"title":"MongoDB Cookbook, 2nd Edition","subtitle":"Harness the latest features of MongoDB 3 with this collection of 80 recipes - from managing cloud platforms to app development, this book is a vital resource","isbn13":"9781785289989","price":"$44.99","image":"https://itbook.store/img/books/9781785289989.png","url":"https://itbook.store/books/9781785289989"}]
     */

    private String error;
    private String total;
    private String page;
    private List<BooksBean> books;

    public Mongodb() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean implements Serializable {
        /**
         * title : MongoDB in Action, 2nd Edition
         * subtitle : Covers MongoDB version 3.0
         * isbn13 : 9781617291609
         * price : $19.99
         * image : https://itbook.store/img/books/9781617291609.png
         * url : https://itbook.store/books/9781617291609
         */

        private String title;
        private String subtitle;
        private String isbn13;
        private String price;
        private String image;
        private String url;

        public BooksBean() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getIsbn13() {
            return isbn13;
        }

        public void setIsbn13(String isbn13) {
            this.isbn13 = isbn13;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
