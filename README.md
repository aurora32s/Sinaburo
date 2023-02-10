#### 📌 시나브로: 모르는 사이에 조금씩 조금씩
##### 일상 기록 어플리케이션
![일러스트](https://user-images.githubusercontent.com/22411296/200245318-764285c4-d825-494c-b833-6b93bc3621e5.jpg)

#### 🍿 구글 플레이 스토어
[구글 플레이 스토어로 이동하기!](https://play.google.com/store/apps/details?id=com.haman.dearme)

<img width="350" alt="스크린샷 2022-11-07 오후 4 05 06" src="https://user-images.githubusercontent.com/22411296/200245884-b2cc569c-ae98-48cf-a35c-5403341ac9a0.png">

#### 🛠 사용기술
|내용|기술|
|----------|----------------------|
|UI|Jetpack Compose|
|비동기|Coroutine, Flow|
|DB|Room|
|DI|Hilt|

#### 🔥 프로젝트 구조
#### 1. ui
|번호|패키지명|설명|
|-----|------------------|----------------|
|1-1|component|각 화면에 사용되는 Compose 부품|
|1-2|screen|각 화면 Compose|
|1-3|route|Compose Navigation 관리|
|1-4|model|presentation에서 사용되는 data class|
#### 2. domain
|번호|패키지명|설명|
|-----|------------------|----------------|
|2-1|repository|Repository interface|
|2-2|usecase|각 usecase 관리|
#### 3. data
|번호|패키지명|설명|
|-----|------------------|----------------|
|3-1|db|database 관련 dao, entity, database class 관리|
|3-2|repository|2-1의 구현 class|
|3-3|source|db에 접근하기 위한 data source|
#### 4. di: hilt module
