from dependency_injector import containers, providers


class UserSerivce:
    def get_config(self):
        return {"key": "hi"}


class Container(containers.DeclarativeContainer):
    user_service_provider = providers.Singleton(UserSerivce)
    cfg = user_service_provider.provided.get_config.call()


if __name__ == "__main__":
    container = Container()

    user_service = container.user_service_provider()
    print(container.cfg())
