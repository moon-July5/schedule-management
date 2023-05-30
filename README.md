# ⭐ 연차&당직 관리 시스템 
> 직원의 연차 / 당직을 관리하는 프로그램 

## 1. 제작기간 & 참여인원
*  2023.03.20 ~ 2023.04.03
*  Back-End(4인), Front-End(3인)

## 2. 담당 역할
* Back-End 팀장
* 연차/당직 관련 기능 개발(CRUD)
* CI/CD 서버 구축과 배포 

## 3. 사용기술
* Java 17
* Spring Boot 3.0.4
* Gradle 7.6.1
* MySQL 8.0
* Spring Data JPA
* Spring Security
* JWT
* AWS EC2, S3, RDS

## 4. API 설계
<details>
<summary>API 설계</summary>

<br>
  
[API 설계 노션 링크](https://www.notion.so/884b8df02ea0496aa2d8c546fdd9a957?v=a76c8c48624540f3abff1b67cc4b8388) 
  
</details>  

<br>

## 5. ERD 설계
<details>
<summary>ERD 이미지</summary>

![Mini_Project_4](https://github.com/moon-July5/schedule-management/assets/60730405/5e9a6b58-a067-49fa-be26-98f3c88ebebc)
</details>  

<br>

## 6. 핵심 기능
이 프로젝트는 연차/당직을 관리하는 서비스를 제공하고 있기 때문에 핵심 기능은 **연차/당직을 등록, 조회, 수정, 삭제**하는 기능입니다.

<details>
<summary>핵심 기능 설명</summary>

<br>

<details>
<summary>연차/당직 등록 기능</summary>

* `ScheduleController`에서 로그인한 사용자 정보와 연차/당직 등록에 필요한 정보들을 전달받아 Service 계층으로 넘겨 처리합니다.   
⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/controller/ScheduleController.java#L38)
* `ScheduleService`에서 전달받은 정보들 중에 type이 일정, 연차, 당직에 따라서 `ScheduleRepository`에 다르게 저장하도록 합니다.
* 예를 들어, 일정일 경우, 일정 내용을 함께 저장하며, 연차면 먼저 남은 연차 수를 빼주고 저장합니다. 그리고 당직이면 직원이 당직을 설 수 있는 경우에만 저장하도록 로직을 구현했습니다.  
⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/service/ScheduleService.java#L57)
</details> 

<br>

<details>
<summary>연차/당직 조회 기능</summary>

* 연차/당직 조회 기능은 `ScheduleController`에서 사용자에 따라서 조회해야 하기 때문에 `id`를 전달받아 Service 계층으로 넘겨 처리합니다. ⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/controller/ScheduleController.java#L50)
* `ScheduleService`에서 전달받은 사용자의 `id`를 이용하여 사용자 정보를 조회하며, 조회된 사용자에 따라서 연차/당직 관련 정보들을 dto 형태로 반환하여 응답합니다.  
⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/service/ScheduleService.java#L50)
</details> 

<br>

<details>
<summary>연차/당직 수정,삭제 기능</summary>

* 연차/당직 수정, 삭제 기능은 사용자 권한이 `관리자(ADMIN)`인 경우만 처리할 수 있습니다.
* 수정같은 경우에는 `ScheduleController`에서 수정할 연차/당직 일정들을 전달받아 Service 계층으로 넘겨 처리합니다. ⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/controller/ScheduleController.java#L60)
* 삭제같은 경우에는 `ScheduleController`에서 삭제할 연차/당직 번호들을 전달받아 Service 계층으로 넘겨 처리합니다. ⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/controller/ScheduleController.java#L74)
* `ScheduleService`에서 각 정보들을 전달받아 수정/삭제 로직을 처리하며, 수정 로직은 등록 로직과 유사하지만 추가적으로 수정하기 전 남은 연차 일수와 수정한 후 남은 연차 일수를 이용하여 남은 연차 일수를 계산한 후 저장해야 합니다. ⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/service/ScheduleService.java#L140)
* 삭제 로직은 일정들을 삭제하기 전 type이 연차일 경우, 연차를 등록할 때 사용한 연차 일수를 현재 남은 연차 일수에 더하는 과정을 통해 갱신한 후, 삭제를 진행합니다.  
⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/service/ScheduleService.java#L194)
  
</details>  

<br>

<details>
<summary>오늘의 당직</summary>

* 이 기능은 현재 날짜에 당직인 사용자들을 조회하는 기능입니다.
* `ScheduleController`에서 현재 날짜를 `LocalDate` 형태로 전달받아 Service 계층으로 넘겨 처리합니다.  ⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/controller/ScheduleController.java#L85)
* `ScheduleService`에서 전체 일정들을 조회한 후, 전체 일정들 중에서 현재 날짜인 경우와 type이 당직인 경우만 선택하여 사용자들을 List 형태로 모두 저장합니다.  
그 후, 중복된 사용자가 저장된 경우도 있을 수 있기 때문에 Set 형태로 변환하여 중복 제거 후, List 형태로 변환 후, 그 사용자 정보들을 반환하여 응답하도록 합니다. ⭐ [코드 확인](https://github.com/moon-July5/schedule-management/blob/e14c16f468088ec32e692923d2f852dc897aba03/src/main/java/com/group4/miniproject/service/ScheduleService.java#L218)  
  
</details> 
  
</details>  

<br>

## 7. 느낀점
이번 프로젝트는 처음으로 Front-End 분들과 함께 개발한 프로젝트입니다.  
백엔드 팀장으로서 이 프로젝트를 하기 전에 제가 생각했던 목표는 당연히 Front-End 분들과 무사히 프로젝트를 완성시키는 것이였고, 프로젝트를 하다보면 갑작스러운 일로 조원들이 하나 둘 씩 빠지는 경우도 있다고 들어서, 혹시 모를 일을 대비해서 조원들이 각자 맡은 기능들이 어느정도 까지 진행했는지 서로 공유할 필요가 있기 때문에 서로 서로 소통하려고 노력했던 것 같습니다. 그리고 저 포함해서 각 조원들마다 개발하다가 에러 또는 어떤 식으로 개발해야할 지 막막한 부분이 있을 경우에는 다 같이 모여서 의견을 던지며 해결하려고 했습니다. 돌이켜보면 이런 부분에서 저와 조원들이 정말 잘한 것 같다라고 느꼈습니다.

하지만, 이 프로젝트를 하면서 정말 아쉬운 점은 저희 조원들과 서로 소통을 잘한 것은 좋았지만 Front-End 분들과도 소통을 열심히 했어야 한다고 느꼈습니다.  
제가 아직 미숙한 탓에 Front에서 어떤 데이터를 요청으로 보낼 지, 또 저희 Back에서 어떤 데이터를 응답으로 보낼 지 명확하게 정하질 않고 계속 변경사항이 발생하다보니 서로 혼란이 왔었던 것 같습니다. 그러면서 개발 진행이 더뎌지고 서로 서로 좋지 않은 영향이 연쇄적으로 발생했던 것 같습니다. 이런 소통 문제를 해결하기 위해서는 지금 당장이라도 소통하는 방향으로, 저희 쪽, Front-End 분들도 전보다 조금 더 적극적으로 표현하여 어떻게든 개발을 진행했습니다. 프로젝트를 마감하고 나서 생각해보니 다른 파트분들과 소통이 정말 아쉽다고 느꼈습니다.  

여태 개인 프로젝트같은 것을 하면서 자신의 개발 실력이 문제가 됐지만 이번 팀 프로젝트같은 경우는 소통 문제에 직면하게 되어 훨씬 더 어려웠던 것 같습니다.  
그래도 이번 프로젝트를 하면서 다른 파트분들과 협력할 때, 소통이 중요하고, 이 소통으로 개발 진행을 더 효율적으로 할 수 있다는 것을 경험할 수 있어서 정말 좋은 공부를 한 것 같습니다.  

### 📌 실제 화면
![image](https://github.com/moon-July5/schedule-management/assets/60730405/14267947-5370-4ff7-959c-00d75d555006)

### 📌 [AWS 서버 세팅 정리](https://velog.io/@moon-july5/series/AWS)


