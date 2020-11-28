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
- assertj라는 테스트 검증 라이브러리의 검증 메소드
- 검증하고 싶은 대상을 메소드 인자로 받는다
