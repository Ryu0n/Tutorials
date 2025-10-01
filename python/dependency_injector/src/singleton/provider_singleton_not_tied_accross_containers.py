from dependency_injector import containers, providers


class UserSerivce:
    def get_config(self):
        return {"key": "hi"}


class Container(containers.DeclarativeContainer):
    user_service_provider = providers.Singleton(UserSerivce)
    cfg = user_service_provider.provided.get_config.call()


if __name__ == "__main__":
    container1 = Container()
    user_service1 = container1.user_service_provider()

    container2 = Container()
    user_service2 = container2.user_service_provider()

    # Singleton provider scope is tied to the container.
    assert user_service1 is not user_service2

    # Singleton provider can be reset memory.
    container2.user_service_provider.reset()

    # Context manager return provider with aliasing.
    with container1.user_service_provider.reset() as user_service1:
        print(user_service1)
        print(user_service1())
