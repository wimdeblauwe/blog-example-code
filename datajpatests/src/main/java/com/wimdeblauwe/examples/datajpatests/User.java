package com.wimdeblauwe.examples.datajpatests;

import javax.persistence.*;

@Entity
@Table(name = "my_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Song favoriteSong;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book favoriteBook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Song getFavoriteSong() {
        return favoriteSong;
    }

    public void setFavoriteSong(Song favoriteSong) {
        this.favoriteSong = favoriteSong;
    }

    public Book getFavoriteBook() {
        return favoriteBook;
    }

    public void setFavoriteBook(Book favoriteBook) {
        this.favoriteBook = favoriteBook;
    }
}
