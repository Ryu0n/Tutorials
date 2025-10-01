# MockDependencyInjector
## Objectives
- DIP(Dependency Inversion Principle), DI(Dependency Injection)에 대해서 이해하는것
- Python에서 DI를 위한 패키지인 ```dependency-injector```의 사용 예시 및 테스트 케이스 작성

## DIP (Dependency Inversion Principal)
- 객체지향의 5대 원칙 SOLID의 마지막 원칙에 해당한다.
- 구체화된 개념이 추상화된 개념에 의존해야 한다는 설계 원칙을 의미한다.
    - 예를 들어, ```Character```클래스가 ```Knife```클래스에 의존하고 있다면, 
    - ```Sword```라는 클래스가 새로 추가될 때에, ```Character```클래스는 다시 코드가 바뀌어야 한다. (```Character```클래스와 타 클래스 간의 직접 매핑 관계로 인해 Coupling이 높기 때문)
    - 고로, ```Weapon```이라는 인터페이스를 ```Knife```클래스와 ```Sword```클래스가 구현하도록 설계하면, ```Character```클래스는 ```Weapon```클래스에 의존하도록 설계한다.
    - 이 경우, 직접적인 매핑 관계가 아닌 간접적으로 이루어지기 때문에 Coupling이 낮아지게 된다.  

## DI (Dependency Injection)
- DIP를 실현시키는 방법론으로, 특정 객체 내부에서 타 객체를 생성 및 의존하는 것이 아닌, 
- 타 객체를 외부에서 생성하고, 특정 객체로 주입하는 방식을 의미한다.

## Usage ```dependency-injector```
- Install ```dependency-injector``` package
```
# for poetry
poetry add dependency-injector

# for pip
pip install dependency-injector
```

## REFERENCE  
- https://www.humphreyahn.dev/blog/dependency-injector
- https://python-dependency-injector.ets-labs.org/examples/index.html