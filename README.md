#### ๐ ์๋๋ธ๋ก: ๋ชจ๋ฅด๋ ์ฌ์ด์ ์กฐ๊ธ์ฉ ์กฐ๊ธ์ฉ
##### ์ผ์ ๊ธฐ๋ก ์ดํ๋ฆฌ์ผ์ด์
![์ผ๋ฌ์คํธ](https://user-images.githubusercontent.com/22411296/200245318-764285c4-d825-494c-b833-6b93bc3621e5.jpg)

#### ๐ฟ ๊ตฌ๊ธ ํ๋ ์ด ์คํ ์ด
[๊ตฌ๊ธ ํ๋ ์ด ์คํ ์ด๋ก ์ด๋ํ๊ธฐ!](https://play.google.com/store/apps/details?id=com.haman.dearme)

<img width="350" alt="แแณแแณแแตแซแแฃแบ 2022-11-07 แแฉแแฎ 4 05 06" src="https://user-images.githubusercontent.com/22411296/200245884-b2cc569c-ae98-48cf-a35c-5403341ac9a0.png">

#### ๐  ์ฌ์ฉ๊ธฐ์ 
|๋ด์ฉ|๊ธฐ์ |
|----------|----------------------|
|UI|Jetpack Compose|
|๋น๋๊ธฐ|Coroutine, Flow|
|DB|Room|
|DI|Hilt|

#### ๐ฅ ํ๋ก์ ํธ ๊ตฌ์กฐ
#### 1. ui
|๋ฒํธ|ํจํค์ง๋ช|์ค๋ช|
|-----|------------------|----------------|
|1-1|component|๊ฐ ํ๋ฉด์ ์ฌ์ฉ๋๋ Compose ๋ถํ|
|1-2|screen|๊ฐ ํ๋ฉด Compose|
|1-3|route|Compose Navigation ๊ด๋ฆฌ|
|1-4|model|presentation์์ ์ฌ์ฉ๋๋ data class|
#### 2. domain
|๋ฒํธ|ํจํค์ง๋ช|์ค๋ช|
|-----|------------------|----------------|
|2-1|repository|Repository Implement|
|2-2|usecase|๊ฐ usecase ๊ด๋ฆฌ|
#### 3. data
|๋ฒํธ|ํจํค์ง๋ช|์ค๋ช|
|-----|------------------|----------------|
|3-1|db|database ๊ด๋ จ dao, entity, database class ๊ด๋ฆฌ|
|3-2|repository|2-1์ ๊ตฌํ class|
|3-3|source|db์ ์ ๊ทผํ๊ธฐ ์ํ data source|
#### 4. di: hilt module
