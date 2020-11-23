from django.shortcuts import render
from django.http import HttpResponse

from .models import Question

# Create your views here.
def index(request):
    lastest_question_list = Question.objects.order_by('-pub_date')[:5]
    output = ', '.join([q.question_text for q in lastest_question_list])
    # return HttpResponse("Hello, world. You're at the polls index.")
    return HttpResponse(output)

def detail(request, question_id):
    response = "You're looking at question {0}."
    return HttpResponse(response.format(question_id))


def results(request, question_id):
    response = "You're looking at the results of questions {0}."
    return HttpResponse(response.format(question_id))


def vote(request, question_id):
    response = "You're voting on question {0}."
    return HttpResponse(response.format(question_id))
