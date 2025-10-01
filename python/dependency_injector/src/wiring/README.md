# Wiring
- wiring은 continer provider(의존성)를 함수에 주입시키는 방법을 의미한다.
- wiring을 위해 필요한 것들
    - ```@inject``` : 주입 대상이 되는 함수에 명시해야 한다. (dependency를 주입시킨다는 의미)
        - ```@inject``` 데코레이터는 주입 대상 함수의 가장 맨 처음으로 적는 것이 성능 이슈가 없다.
        ```python
        from dependency_injector.wiring import inject, Provide

        @decorator_etc
        @decorator_2
        @decorator_1
        @inject
        def foo(bar: Bar = Provide[Container.bar]):
        ```
    - ```Provide[Container.bar]``` : (Markers) - wiring marker는 어떤 의존성이 주입되는지를 명시한다. 이는 container가 injection을 찾을 수 있게 한다. (특정 container의 provider를 명시한다.)
    - ```container.wire()``` : 주입시킬 container를 주입 대상이 되는 함수와 연결한다.

```python
from dependency_injector import containers, providers
from dependency_injector.wiring import Provide, inject


class Service:
    ...


class Container(containers.DeclarativeContainer):

    service = providers.Factory(Service)


@inject
def main(service: Service = Provide[Container.service]) -> None:
    ...


if __name__ == "__main__":
    container = Container()
    container.wire(modules=[__name__])

    main()
```

# FastAPI example
```python
app = FastAPI()

@app.api_route("/")
@inject
async def index(service: Service = Depends(Provide[Container.service])):
    value = await service.process()
    return {"result": value}
```

# Decorator pattern example
```python
def decorator1(func):
    @functools.wraps(func)
    @inject
    def wrapper(value1: int = Provide[Container.config.value1]):
        result = func()
        return result + value1
    return wrapper


def decorator2(func):
    @functools.wraps(func)
    @inject
    def wrapper(value2: int = Provide[Container.config.value2]):
        result = func()
        return result + value2
    return wrapper

@decorator1
@decorator2
def sample():
    ...
```

# Markers
- 주입할 dependency의 종류에 따라 marker가 달라질 수 있다.
```python
from dependency_injector import containers, providers
from dependency_injector.wiring import Provide, inject


class Service:
    def __init__(self, name) -> None:
        self.name = name


class Container(containers.DeclarativeContainer):
    service = providers.Factory(Service, name="Redis")


@inject
def main(service: Service = Provide[Container.service]) -> None:  # here
    print(f"Service name : {service.name}")


if __name__ == "__main__":
    container = Container()
    container.wire(modules=[__name__])

    main()
```
- ```Container.service``` factory provider로 생성된 객체를 주입하려는 경우 marker를 ```Provide```로 명시해야 한다.

```python
from typing import Callable
from dependency_injector import containers, providers
from dependency_injector.wiring import Provider, inject


class Service:
    def __init__(self, name) -> None:
        self.name = name


class Container(containers.DeclarativeContainer):
    service = providers.Factory(Service, name="Redis")


@inject
def main(service: Callable[..., Service] = Provider[Container.service]) -> None:  # here
    redis_service = service()
    print(f"Service name : {redis_service.name}")


if __name__ == "__main__":
    container = Container()
    container.wire(modules=[__name__])

    main()
```
- 반면, provider 함수 자체를 넘길 때에는 ```Provider```로 명시해야 한다.
- 정리하자면, ```Provide```를 사용하는 경우, 이미 생성된 객체를 주입한다. (인스턴스 필드를 따로 지정할 수 없다.) 반면, ```Provider```를 사용할 경우, 해당 객체를 생성하기 위한 factory provider method를 제공하기 때문에 동적으로 필드를 할당할 수 있다.

# FastAPI example
```python
app = FastAPI()

@app.api_route("/")
@inject
async def index(service: Service = Depends(Provide[Container.service])):
    value = await service.process()
    return {"result": value}
```
- ```FastAPI```의 경우 ```Depends```를 통하여 의존성을 주입한다.

# REFERENCE
- https://python-dependency-injector.ets-labs.org/wiring.html