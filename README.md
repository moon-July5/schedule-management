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
