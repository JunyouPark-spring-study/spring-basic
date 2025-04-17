## **1. 스프링 핵심 원리 이해 - 예제 만들기**

### **회원 도메인**

- **구조**
    - **`MemberService`**(인터페이스) ← **`MemberServiceImpl`**(구현체)
    - **`MemberRepository`**(인터페이스) ← **`MemoryMemberRepository`**(메모리 저장소)
- **기능**
    - 회원 가입/조회
    - 등급: BASIC, VIP

### **주문 도메인**

- **구조**
    - **`OrderService`** ← **`OrderServiceImpl`**
    - **`DiscountPolicy`**(인터페이스) ← **`FixDiscountPolicy`**(고정 할인)
- **흐름**
    1. 주문 생성 → 회원 조회 → 할인 적용 → 결과 반환

### **개발 포인트**

1. **인터페이스 분리**
    - 저장소: **`MemoryMemberRepository`** → 추후 DB 저장소로 교체 가능
    - 할인 정책: **`FixDiscountPolicy`** → 정률 할인 정책으로 확장 가능
2. **테스트**
    - JUnit으로 회원 가입/주문 생성 테스트
    - 예시: VIP 회원의 고정 할인(1000원) 적용 검증

### **문제점 (리팩토링 필요)**

- **DIP 위반**: **`OrderServiceImpl`**이 **`FixDiscountPolicy`** 구현체에 직접 의존
- **OCP 위반**: 할인 정책 변경 시 클라이언트 코드 수정 필요
    
    → **해결 방안**: AppConfig 도입 (관심사 분리)
    

> 💡 학습 목표: 인터페이스 설계와 다형성을 활용해 확장성 있는 구조를 이해합니다.
> 

---

## **2 . 스프링 핵심 원리 이해 - 객체 지향 설계 원리 적용**

### **🔍 핵심 개념 요약**

### **1. 객체 지향 설계의 중요성**

- **인터페이스 분리**: 구현체 변경이 용이하도록 설계
- **관심사 분리**: 객체 생성/연결 책임과 실행 책임 분리
- **다형성 활용**: 확장성 있는 시스템 구축

### **2. 주요 설계 원칙 적용**

| **원칙** | **적용 포인트** |
| --- | --- |
| **단일 책임(SRP)** | 객체 생성/연결 책임과 실행 책임 분리 |
| **개방-폐쇄(OCP)** | 기능 확장 시 기존 코드 수정 최소화 |
| **의존 역전(DIP)** | 추상화에만 의존하는 구조 구현 |

### **3. 스프링의 핵심 기능**

- **IoC 컨테이너**: 객체 생명주기 관리
- **의존성 주입(DI)**: 런타임 시 의존 관계 자동 연결
- **설정 클래스**: **`@Configuration`**으로 애플리케이션 구성 관리

### **🛠️ 실무 적용 포인트**

1. **변경에 유연한 설계**
    - 인터페이스 중심 개발
    - 구현체 변경이 필요할 때 구성 클래스만 수정
2. **테스트 용이성**
    - 의존성 주입을 통한 모킹(mocking) 용이
    - 단위 테스트 작성 편리
3. **스프링 컨테이너 활용**
    - 객체 생성/관리 자동화
    - 설정을 통한 유연한 환경 구성

### **🎯 학습 효과**

- 객체 지향 설계 원칙의 실제 적용 사례 이해
- 스프링의 핵심 기능이 해결하는 문제 인식
- 유지보수성 높은 코드 작성 능력 향상

---

## **3. 스프링 컨테이너와 스프링 빈**

### **🏗️ 스프링 컨테이너 생성**

- **ApplicationContext**가 스프링 컨테이너의 핵심 인터페이스
- **`new AnnotationConfigApplicationContext(AppConfig.class)`**로 생성
- 구성 정보를 기반으로 빈 등록 및 의존관계 주입 수행

### **🔍 빈 조회 방법**

1. **기본 조회**
    - **`ac.getBean("빈이름", 타입)`**
    - **`ac.getBean(타입)`**
    - ❗ 조회 실패 시 **`NoSuchBeanDefinitionException`** 발생
2. **동일 타입 다중 빈**
    - 동일 타입이 여러 개일 경우 빈 이름 지정 필요
    - **`ac.getBeansOfType()`**로 해당 타입 전체 조회
3. **상속 관계**
    - 부모 타입 조회 시 자식 타입 포함
    - **`Object`** 타입으로 모든 스프링 빈 조회 가능

### **⚙️ 컨테이너 계층 구조**

| **인터페이스** | **설명** |
| --- | --- |
| **BeanFactory** | 빈 관리/조회 기본 기능 (저수준) |
| **ApplicationContext** | BeanFactory + 부가 기능 (실무 표준) |

### **설정 형식 지원**

- **자바 코드**: **`@Configuration`** 클래스 (권장)
- **XML**: **`GenericXmlApplicationContext`** (레거시 시스템용)
- 모든 설정은 내부적으로 **`BeanDefinition`**으로 변환 처리

### **📌 BeanDefinition**

- 빈 생성 방식, 스코프, 생명주기 메서드 등 메타정보 저장
- 스프링 컨테이너의 추상화 핵심 개념

> 💡 실무 Tip:
> 
> - **`ApplicationContext`**를 주로 사용하며, **`BeanDefinition`**은 거의 직접 다루지 않음
> - XML보다 자바 기반 설정이 현대적이고 유지보수 용이
