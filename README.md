# Musinsa Backend Engineer 과제

---

## 구현 범위

### API 1: 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회

- end point: `/api/categories/min-prices`
- 반환 값:
```json
{
  "items": [
    { "category": "TOP", "brand": "C", "price": 10000 },
    { "category": "OUTER", "brand": "E", "price": 5000 },
    ...
  ],
  "totalPrice": 15000
}
```

### API 2: 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회

- end point: `/api/categories/min-prices/brand`
- 반환 값:
```json
{
  "brand": "D",
  "categories": [
    { "category": "TOP", "price": 10100 },
    { "category": "OUTER", "price": 5100 },
    ...
  ],
  "totalPrice": 36100
}
```

### API 3: 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회

- end point: `/api/categories/{category}/min-max`
- 반환 값 :
```json
{
  "category":"TOP",
  "minPrice": {
    "brand": "C",
    "price": 10000
  },
  "maxPrice":{
    "brand":"I",
    "price":11400
  }
}
```

---

## 빌드 & 테스트 방법

### 파일 구성
```bash
./gradlew clean build
```

### 테스트 실행
```bash
./gradlew test
```

---
