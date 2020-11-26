from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse
from django.http import Http404
from django.template import loader

from .models import Question


# Create your views here.
def index(request):
    latest_question_list = Question.objects.order_by('-pub_date')[:5]

    # Inject logic
    # output = ', '.join([q.question_text for q in lastest_question_list])
    # # return HttpResponse("Hello, world. You're at the polls index.")
    # return HttpResponse(output)

    # Load template
    # template = loader.get_template('polls/index.html')
    # context = {
    #     'latest_question_list': latest_question_list,
    # }
    # return HttpResponse(template.render(context, request))

    # Shortcut
    context = {'latest_question_list': latest_question_list}
    return render(request, 'polls/index.html', context)


def detail(request, question_id):
    # response = "You're looking at question {0}."
    # return HttpResponse(response.format(question_id))

    # Raise 404(Not Found) Error if object isn't exists.
    # try:
    #     question = Question.objects.get(pk=question_id)
    # except Question.DoesNotExist:
    #     raise Http404('Question does not exist')
    # return render(request, 'polls/detail.html', {'question': question})

    # Shortcut
    question = get_object_or_404(Question, pk=question_id)
    return render(request, 'polls/detail.html', {'question': question})


def results(request, question_id):
    response = "You're looking at the results of questions {0}."
    return HttpResponse(response.format(question_id))


def vote(request, question_id):
    response = "You're voting on question {0}."
    return HttpResponse(response.format(question_id))
