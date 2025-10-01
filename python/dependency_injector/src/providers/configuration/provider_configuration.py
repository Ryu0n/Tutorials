from dependency_injector import providers, containers

class ApplicationContainer(containers.DeclarativeContainer): # 아래 컨테이너에서 설명
	config = providers.Configuration()


if __name__ == "__main__":
    container = ApplicationContainer()
    container.config.from_dict(
        {
            "aws": {
                "access_key_id": "KEY",
                "secret_access_key": "SECRET",
            },
        },
    )
    print(container.config.get("aws"))  # {"access_key_id": "KEY", "secret_access_key": "SECRET"}
    
    assert container.config() == {
        "aws": {
            "access_key_id": "KEY",
            "secret_access_key": "SECRET"
        }
    }
    assert container.config.aws() == {
        "access_key_id": "KEY",
        "secret_access_key": "SECRET"
    }
    assert container.config.aws.access_key_id() == "KEY"
    assert container.config.aws.secret_access_key() == "SECRET"