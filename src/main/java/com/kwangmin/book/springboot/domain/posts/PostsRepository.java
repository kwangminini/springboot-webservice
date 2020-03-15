package com.kwangmin.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts,Long> { //JpaRepository 상속 시 자동으로 CRUD 생성
}
