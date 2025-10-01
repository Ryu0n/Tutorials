from fastapi import FastAPI, Depends
from dependency_injector import containers, providers
from dependency_injector.wiring import Provide, inject


app = FastAPI()


class Sample:
    name: str = "test"


class SampleContainer(containers.DeclarativeContainer):
    sample = providers.Factory(Sample)


@inject
def _depends_test_endpoint(
    sample: Sample = Provide[SampleContainer.sample]
):
    print(__name__)
    print(sample)


@app.get("/test_depends")
@inject
async def depends_test_endpoint(
    sample: Sample = Depends(Provide[SampleContainer.sample])
):
    print(__name__)
    print(sample, sample.name)


container = SampleContainer()
container.wire(modules=[__name__])

_depends_test_endpoint()
