from django.shortcuts import render
from django.core.serializers import serialize
from django.http import HttpResponse, JsonResponse
from django.views.generic import View
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

from .models import *

import json


# Create your views here.
@method_decorator(csrf_exempt, name='dispatch')
class CreateView(View):
    def post(self, request, db):
        if db == 'LSP':
            print(request.META['CONTENT_TYPE'], request.body)

            if request.META['CONTENT_TYPE'] == "application/json":
                request = json.loads(request.body)

                o = LSP(
                    no=request['no'],
                    duplicate=request['duplicate'],
                    div_1=request['div_1'],
                    div_2=request['div_2'],
                    div_3=request['div_3'],
                    div_4=request['div_4'],
                    file_name=request['file_name'],
                    file_dir=request['file_dir'],
                    value=request['value'],
                )

            o.save()
            return HttpResponse(status=200)


class GetView(View):
    def get(self, request, db, all):
        if db == 'LSP':
            files = LSP.objects.all()
        _files = json.loads(serialize('json', files))

        # Find all
        if all == 1:
            return JsonResponse({'files': _files})
        # Find something
        elif all == 0:
            id = int(request.GET.get('id'))
            return JsonResponse(_files[id - 1])


class UpdateView(View):
    def get(self, request, db, no):
        if db == 'LSP':
            o = LSP.objects.get(no=no)

            div_1 = request.GET.get('div_1')
            div_2 = request.GET.get('div_2')
            div_3 = request.GET.get('div_3')
            div_4 = request.GET.get('div_4')

            if div_1 is not None:
                o.div_1 = div_1
            if div_2 is not None:
                o.div_2 = div_2
            if div_3 is not None:
                o.div_3 = div_3
            if div_4 is not None:
                o.div_4 = div_4

            o.save()

        return HttpResponse(o)
