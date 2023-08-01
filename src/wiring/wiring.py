from typing import Callable
from dependency_injector import containers, providers
from dependency_injector.wiring import Provider, Provide, inject


class StrictService:
    def __init__(self, name: str) -> None:
        self.name = name


class Service:
    def __init__(self, service_id: int, name: str) -> None:
        self.service_id = service_id
        self.name = name


class Container(containers.DeclarativeContainer):
    strict_service = providers.Factory(StrictService, name="Redis")
    service = providers.Factory(Service, name="Redis")


@inject
def wiring_provide(service: StrictService = Provide[Container.strict_service]) -> None:  # here
    redis_service = service
    print(f"Service name : {redis_service.name}")


@inject
def wiring_provider(service_id: int, service: Callable[..., Service] = Provider[Container.service]) -> None:  # here
    redis_service = service(service_id=service_id)
    print(f"Service name : {redis_service.service_id}")
    print(f"Service name : {redis_service.name}")


if __name__ == "__main__":
    container = Container()
    container.wire(modules=[__name__])

    wiring_provide()
    wiring_provider(service_id=3)
