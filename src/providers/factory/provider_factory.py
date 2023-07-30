"""
https://python-dependency-injector.ets-labs.org/providers/factory.html
"""

from dependency_injector import containers, providers


class Photo:
    ...


class Email:
    ...


class User:
    def __init__(self, uid: int, main_photo: Photo) -> None:
        self.uid = uid
        self.main_photo = main_photo
        self.email = None


class Container(containers.DeclarativeContainer):
    # first argument : class(provided_type)
    # after first argument : dependencies (it can be provider)
    photo_factory = providers.Factory(Photo)
    email_factory = providers.Factory(Email)
    user_factory = providers.Factory(
        User,
        main_photo=photo_factory
    )
    user_factory.add_attributes(email=email_factory)


if __name__ == "__main__":
    container = Container()
    
    user1 = container.user_factory(1)
    user2 = container.user_factory(2)
    another_photo = Photo()
    user3 = container.user_factory(
        uid=3,
        main_photo=another_photo
    )
    another_photo = container.photo_factory()
    user4 = container.user_factory(
        uid=4,
        main_photo=another_photo
    )
    print(
        user1, user2, user3, user4,
        sep='\n'
    )
    assert isinstance(user4.email, Email)
