"""
reference about Enum : https://greendreamtrre.tistory.com/358
"""

from enum import Enum
from pydantic_settings import BaseSettings
from pydantic import Field, validator
from dependency_injector import containers, providers


class ApplicationEnvironment(Enum):
    LOCAL = "local"
    DEV = "dev"
    PROD = "prod"
    TEST = "test"


class DatabaseSettings(BaseSettings):
    db_host: str = Field(
        default="localhost",
    )
    db_port: int = Field(
        default=3306,
        env="DATABASE_PORT"
    )


class ApplicationSettings(BaseSettings):
    env: ApplicationEnvironment = Field(
        default="local",
    )
    db: DatabaseSettings = DatabaseSettings()

    @validator("env")
    def validate_env(cls, v: ApplicationEnvironment) -> str:
        if v.value not in ["local", "dev", "prod", "test"]:
            raise ValueError(f"This argument v({v.value}) is not proper value.")
        return v


class ApplicationContainer(containers.DeclarativeContainer):
    config = providers.Configuration()


if __name__ == "__main__":
    container = ApplicationContainer()
    container.config.from_pydantic(
        ApplicationSettings()
    )
    print(container.config.env())
    print(container.config.env().name)
    print(container.config.env().value)
    print(container.config.db())
    print(container.config.db.db_host())
    print(container.config.db.db_port())
