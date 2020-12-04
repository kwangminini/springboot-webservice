# springboot-webservice

스프링 부트와 AWS로 혼자 구현하는 웹 서비스
## Chapter 1. 인텔리제이로 스프링 부트 시작하기

#### 이클립스에 비해 인텔리제이가 갖는 강점
- 강력한 추천 기능(Smart Completion)
- 훨씬 더 다양한 리팩토링과 디버깅 기능
- 이클립스의 깃(Git)에 비해 훨씬 높은 자유도
- 프로젝트 시작할 때 인덱싱을 하여 파일을 비롯한 자원들에 대한 빠른 검색 속도
- HTML과 CSS, JS, XML에 대한 강력한 기능 지원
- 자바, 스프링 부트 버전업에 맞춘 빠른 업데이트
---------------------------------------
## Chapter 2. 스프링 부트에서 테스트 코드를 작성하자

#### TDD (테스트 주도 개발)
<img src="https://user-images.githubusercontent.com/48472989/100527307-a9891480-3214-11eb-9d2f-c0681ff25b36.png" width="40%" height="40%"></img>
- 항상 실패하는 테스트를 먼저 작성하고 (Red)
- 테스트가 통과하는 프로덕션 코드를 작성하고 (Green)
- 테스트가 통과하면 프로덕션 코드를 리팩토링 (Refactor)

#### 내장 WAS사용 권장
&nbsp;&nbsp;이유 : 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있다

#### @RestController
- 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다
- 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해준다

#### @RequiredArgsConstructor
- 선언된 모든 final 필드가 포함된 생성자를 생성해준다
- final이 없는 필드는 생성자에 포함되지 않는다

#### @RequestParam
- 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
-----------------------------------
## 테스트 코드
#### JUnit4의 @RunWith(SpringRunner.class)
- 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다
- 여기서는 SpringRunner라는 스프링 실행자를 사용
- 즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할

#### @WebMvcTest
- 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
- 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다
- 단, @Service, @Component, @Repository 등은 사용할 수 없다

#### private MockMvc mvc
- 웹 API를 테스트할 때 사용
- 스프링 MVC 테스트의 시작점
- 이 클래스를 통해 HTTP GET, POST 등에 대한 API테스트 가능

#### assertThat
```
assertThat(dto.getName()).isEqualTo(name);
```
- assertj라는 테스트 검증 라이브러리의 검증 메소드
- 검증하고 싶은 대상을 메소드 인자로 받는다

#### param
```
mvc.perform(get("/hello/dto")
                .param("name",name)
                .param("amount",String.valueOf(amount))
                .andExpect(jsonPath("$.name",is(name)));
```
- API테스트할 때 사용될 요청 파라미터를 설정
- 단, 값은 String만 허용
- 그래서 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능
- jsonPath : JSON응답값을 필드별로 검증할 수 있는 메소드 / $를 기준으로 필드명을 명시
------------------------------
## Chapter 3. 스프링 부트에서 JPA로 데이터베이스 다뤄보자

#### Spring Data JPA
- 구현체들을 좀 더 쉽게 사용하고자 추상화 시킨 Spring Data JPA라는 모듈을 이용하여 JPA 기술을 다룬다
- JPA <- Hibernate <- Spring Data JPA
- 구현체 교체의 용이성 : Hibernate외에 다른 구현체로 쉽게 교체하기 위함
- 저장소 교체의 용이성 : 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함

#### Web Layer
- 흔히 사용하는 컨트롤러(@Controller)와 JSP/Freemarker 등의 뷰 템플릿 영역
- 이외에도 필터(@Filter), 인터셉터, 컨트롤러 어드바이스(@ControllerAdvice) 등 외부 요청과 응답에 대한 전반적인 영역

#### Service Layer
- @Service에 사용되는 서비스 영역
- 일반적으로 Controller와 Dao의 중간 영역에서 사용
- @Transaction이 사용되어야 하는 영역

#### Repository Layer
- Database와 같이 데이터 저장소에 접근하는 여역
- 기존의 Dao(Data Access Object) 영역과 비슷

#### Dtos
- Dto(Data Transfer Object)는 계층 간에 데이터 교환을 위한 객체
- 예를 들어 뷰 템플릿 엔진에서 사용될 객체나 Repository Layer에서 결과로 넘겨준 객체 등 

#### Domain Model
- 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화시킨것
- 택시 앱이라고 하면 배차,탑승,요금 등이 모두 도메인
- @Entity가 사용된 영역 역시 도메인 모델
- 다만, 무조건 데이터베이스의 테이블과 관계가 있어야만 하는건 아님
- VO처럼 값 객체들도 이 영역에 해당하기 때문

#### 스프링에서 Bean을 주입받는 방식
- @Autowired
- setter
- 생성자
- 위의 3가지 방식 중 가장 권장하는 방식은 생성자로 주입받는 방식이다. (@RequiredArgsConstructor)
- 그럼에도 불구하고 생성자를 직접 안쓰고 @Autowired를 사용하는 이유는 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위함

#### Entity 클래스와 거의 유사함에도 Dto클래스를 추가로 생성
- Entity 클래스는 데이터베이스와 맞닿는 핵심 클래스
- 절대로 Entity 클래스를 Request/Response 클래스로 사용하면 안됨
- Entity 클래스와 Controller에서 쓸 Dto는 분리해서 사용해야 함

#### 모든 Entity의 상위 클래스가 되어 Entity들의 createdDate, modifiedDate를 자동으로 관리하는 클래스
```
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
```
- @MappedSuperclass : JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식하도록 함
- @EntityListeners(AuditingEntityListener.class) : BaseTimeEntity 클래스에 Auditing 기능을 포함
- @CreatedDate : Entity가 생성되어 저장할 때 시간이 자동 저장
- @LastModifiedDate : 조회한 Entity의 값을 변경할 때 시간이 자동 저장

#### JPA Auditing 어노테이션 활성화 (@EnableJpaAuditing)
```
@EnableJpaAuditing  //JPA Auditing 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## Chapter 4. 머스테치로 화면 구성하기
**머스테치 (http://mustache.github.io/)는 수많은 언어를 지원하는 가장 심플한 템플릿 엔진**

#### 머스테치의 장점
- 문법이 다른 템플릿 엔진(Thymeleaf 등)보다 심플
- 로직코드를 사용할 수 없어 View의 역할과 서버의 역할을 명확하게 분리
- Mustache.js와 Mustache.java 2가지가 다 있어, 하나의 문법으로 클라이언트/서버 템플릿을 모두 사용 가능

#### @Query
```
public interface PostsRepository extends JpaRepository<Posts,Long>{
  @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
  List<Posts> findAllDesc();
}
```
- SpringDataJpa에서 제공하지 않는 메소드는 위처럼 @Query 어노테이션을 이용해서 쿼리로 작성해도 된다
- @Query가 가독성이 좋으니 선택해서 사용

#### 참고사항!!
- 규모가 있는 프로젝트에서는 데이터 조회는 FK의 조인, 복잡한 조건 등으로 인해 이런 Entity 클래스만으로 처리하기 어려워 조회용 프레임워크를 추가로 사용한다. 대표적인 예로 querydsl, jooq, MyBatis 등이 있다.
  조회는 위 3가지 프레임워크 중 하나를 통해 조회되고, 등록/수정/삭제 등은 SpringDataJap를 통해 진행된다.
  가장 추천되는 프레임워크 : Querydsl
- Querydsl 추천 이유
  - 타입 안정성이 보장 (단순한 문자열로 쿼리를 생성하는 것이 아니라, 메소드를 기반으로 쿼리를 생성하기 때문에 오타나 존재하지 않는 컬럼명을 명시할 경우 IDE에서 검출)
  - 국내 많은 회사에서 사용 중 (쿠팡, 배민 등 JPA를 적극적으로 사용하는 회사에서는 Querydsl을 적극적으로 사용 중)
  - 많은 레퍼런스 (많은 회사와 개발자들이 사용하다보니 그많큼 국내 자료 다양)
  
------------------------------------------
## Chapter 5. 스프링 시큐리티와 OAuth 2.0으로 로그인 기능 구현하기

#### 구글 로그인 연동
- 구글 서비스에 신규 서비스를 생성
- 구글 플랫폼 (https://console.cloud.google.com)에 새 프로젝트 생성
- 사용자 인증 정보 만들기 (Oauth 클라이언트 ID) 
- 승인된 리다이렉션 URI
  - 서비스에서 파라미터로 인증 정보를 주었을 때 인증이 성공하면 구글에서 리다이렉트할 URL
  - 스프링 부트 2버전의 시큐리티에서는 기본적으로 {도메인}/login/oauth2/code/{소셜서비스코드}로 리다이렉트 URL을 지원
  - 사용자가 별도로 리다이렉트 URL을 지원하는 Controller를 만들 필요가 없다 (시큐리티에서 이미 구현해 놓은 상태)
  - 로컬 개발 단계에서는 http://localhost:8080/login/oauth2/code/google 로 등록
  - AWS 서버에 배포하게 되면 localhost 외에 추가로 주소를 추가해야 한다
- 클라이언트 ID와 클라이언트 보안 비밀 설정
  - application-oauth.properties 생성
    ```
    spring.security.oauth2.client.registration.google.client-id=아이디
    spring.security.oauth2.client.registration.google.client-secret=보안키
    spring.security.oauth2.client.registration.google.scope=profile,email
    ```
- application.properties
```
spring.profiles.include=oauth
```
- 보안을 위해 .gitignore 설정

#### 스프링 시큐리티 설정
```
compile('org.springframework.boot:spring-boot-starter-oauth2-client')
```
- 소셜 로그인 등 클라이언트 입장에서 소셜 기능 구현 시 필요한 의존성

### @EnableWebSecurity
- Spring Security 설정들을 활성화

```
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }
```
#### authorizeRequests
- URL별 권한 관리를 설정하는 옵션의 시작점
- authorizeRequests가 선언 되어야만 antMatchers 옵션 사용 가능

#### antMatchers
- 권한 관리 대상을 지정하는 옵션
- URL, HTTP 메소드별로 관리가 가능
- "/" 등 지정된 URL들은 permitAll()옵션을 통해 전체 열람 권한을 줌
- POST메소드이면서 "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능

#### anyRequest
- 설정된 값들 이외 나머지 URL
- 여기서는 authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자(로그인)들에게만 허용

#### logout().logoutSuccessUrl("/")
- 로그아웃 기능에 대한 여러 설정의 진입점
- 로그아웃 성공 시 / 주소로 이동

#### oauth2Login
- OAuth 2 로그인 기능에 대한 여러 설정의 진입점

#### oauth2Login().userInfoEndpoint()
- OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당

#### oauth2Login().userInfoEndpoint().userService(customOAuth2UserService)
- 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록

#### 2대 이상의 서버에서 톰캣마다 세션 동기화
- 톰캣 세션을 사용
  - 일반적으로 별다른 설정을 하지 않을 때 기본적으로 선택되는 방식
  - 이렇게 될 경우 톰캣(WAS)에 세션이 저장되기 때문에 2대 이상의 WAS가 구동되는 환경에서는 톰캣들 간의 세션 공유를 위한 추가 설정이 필요
- MySQL과 같은 데이터베이스를 세션 저장소로 사용
  - 여러 WAS 간의 공용 세션을 사용할 수 있는 가장 쉬운 방법
  - 많은 설정이 필요 없지만, 결국 로그인 요청마다 DB IO가 발생하여 성능상 이슈가 발생
  - 보통 로그인 요청이 많이 없는 백오피스, 사내 시스템 용도로 사용
- Redis, Memchached와 같은 메모리 DB를 세션 저장소로 사용
  - B2C 서비스에서 가장 많이 사용하는 방식
  - 실제 서비스로 사용하기 위해서는 Embedded Redis와 같은 방식이 아닌 외부 메모리 서버가 필요

## Chapter 6. AWS 서버 환경을 만들어보자 - AWS EC2

#### 서버 24시간 작동시키는 방법
- 집에 PC를 24시간 구동
- 호스팅 서비스(Cafe 24, 코리아 호스팅 등)를 이용
- 클라우드 서비스(AWS, AZURE, GCP 등)를 이용
- 일반적으로 비용은 호스팅 서비스나 집PC를 이용하는 것이 저렴
- 그러나, 특정 시간에만 트래픽이 몰린다면 유동적으로 사양을 늘릴 수 있는 클라우드가 유리

####

