package com.scaler.blogapi.articles;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity(name = "articles")
public class ArticleEntity extends BaseEntity {

    @Column(nullable = false, length = 200)
    String title;
    @Column(unique = true, nullable = false, length = 150)
    String slug;
    @Column(nullable = false, length = 8000)
    String body;
    String subtitle;

    @ManyToOne
    UserEntity author;

    @ManyToMany
    List<UserEntity> likedBy;

}