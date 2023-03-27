# compare-mvc-and-webflux
코드 실행을 위해 필요한 라이브러리를 결정하기 위한 비교 분석 자료입니다.

## 필요한 도구

- JDK 17
- k6

## 설명

- MVC + Async + Polling 방식의 구조와 Webflux의 구조를 비교하기 위해 비슷한 환경을 구성했습니다.
- k6를 활용해 성능을 테스트할 수 있습니다. 서버 영역 모니터링은 구현하지 않았습니다.


## MVC + Async + Polling 방식 빌드

```shell
cd delivery-request-mvc && ./gradlew build
```

## MVC + Async + Polling 방식 테스트

### 로드 테스트

```shell
k6 run mvc-k6-test/load.js
```

### 스트레스 테스트

```shell
k6 run mvc-k6-test/stress.js
```

## Webflux 방식 빌드

```shell
cd delivery-request-mvc && ./gradlew build
```

## Webflux 방식 테스트

### 로드 테스트

```shell
k6 run webflux-k6-test/load.js
```

### 스트레스 테스트

```shell
k6 run webflux-k6-test/stress.js
```
