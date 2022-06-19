# ['리멤버'](https://rememberandpass.herokuapp.com) 기술 노트

## 기술스택
#### 백엔드
- SpringBoot 
  - Spring web (MVC + tomcat)
  - Spring security
  - Spring oauth2 client (소셜로그인)
  - Spring data jpa
  - Spring data rest
- lombok
- jdbc mariadb, flyway(DB 형상관리), querydsl

#### 프론트
- thymeleaf
- jquery
- bootstrap, bootstrap-select (폼 select)
- alertify (알람 라이브러리)


## 1. 스프링부트로 개발시작
리멤버 개발을 위해 기존에 공부했던 Django 프레임워크를 사용할까 잠깐 고민이 있었다. 
하지만 많은 기업에서 백엔드 프레임워크로 스프링을 사용하고 있음이 느껴져서, 스프링을 공부해보면 좋을것 같다는 판단이 섰다.

스프링부트는 기존 스프링에 톰캣서버를 내장하고 있고 의존성을 어느정도 자동으로 관리해주는 기능을 갖추고 있어서 
스프링에 입문하는데 큰도움이 되었다.

```shell
plugins {
  id 'org.springframework.boot' version '2.6.7'
  id 'io.spring.dependency-management' version '1.0.11.RELEASE'
  id 'java'
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-data-rest'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```
위와 같이 특정버전의 'org.springframework.boot' 플러그인과 'io.spring.dependency-management' 플러그인을
설치하면 스프링부트 버전의 spring-boot-dependencies bom을 받아온다. 'org.springframework.boot'
에서 관리하는 프레임워크에 한해서는 버전의 명시를 따로 해줄필요가 없어진다.

스프링을 공부하면서 놀랐던것은 django의 경우 거의 모든 기능이 하나의 프레임워크에 포함되어있었지만, 
Spring은 서로 다른 프레임워크로 기능들이 쪼개져있었다. 따라서 필요한 기능을 제공하는 스프링의 프레임워크들을 찾아서
gradle 의존성에 추가해주는 작업이 필요했다.

그래서 Spring web (MVC + tomcat), Spring 시큐리티(보안), Spring oauth2 client(소셜로그인), 
Spring data jpa(편리하게 리파지토리 생성), Spring data rest(restful한 response object 생성)등의
프레임워크들을 추가해주었고, 편리하게 생성자를 생성해주는 롬복, JPQL 쿼리를 도와주는 Querydsl을 의존성에 추가해주었다.

