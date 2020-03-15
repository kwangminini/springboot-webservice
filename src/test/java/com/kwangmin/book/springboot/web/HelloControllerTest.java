package com.kwangmin.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)    //스프링 부트 테스트와 JUnit 사이에 연결자 역할을 함
@WebMvcTest         //Web(Spring MVC)에 집중
public class HelloControllerTest {
    @Autowired // 스프링이 관리하는 Bean을 주입
    private MockMvc mvc;    //이 클래스를 통해 HTTP GET, POST 등에 대한 API테스트를 할 수 있음

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";
        mvc.perform(get("/hello"))      //MockMvc를 통해 /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk())         //HTTP Header의 Status를 검증 , 여기선 Ok 즉, 200인지 검증
                .andExpect(content().string(hello));    //응답 본문의 내용을 검증, Controller에서 "hello"를 리턴하기 때문에 맞는지 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name",name)   //param : API테스트할 때 사용될 요청파라미터 설정, String만 허용
                .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name))) //JSON 응답값을 필드별로 검증 할 수 있는 메소드
                .andExpect(jsonPath("$.amount",is(amount)));
    }
}
