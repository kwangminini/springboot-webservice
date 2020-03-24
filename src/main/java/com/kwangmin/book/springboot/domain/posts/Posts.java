package com.kwangmin.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor //기본생성자 자동 추가
@Entity //테이블과 링크될 클래스임을 나타냄 ex) SalesManager.java -> sales_manager table함 , Entity 클래스에서는 절대 Setter메소드 안만든다
public class Posts { //실제 DB의 테이블과 매칭될 클래스이며 보통 Entity클래스라고 한다.
    @Id //해당 테이블의 PK필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성 규칙을 나타낸다. GenerationType.IDENTITY : auto_increment
    private Long id;

    @Column(length = 500,nullable = false) //테이블 컬럼을 나타내며 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용 , varchar(255)의 사이즈를 500으로 늘림
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //타입을 text로 변
    private String content;

    private String author;

    @Builder //해당 클래스의 빌더 패턴 클래스를 생성 , 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포
    public Posts(String title, String content, String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
