from django.urls import path

from . import views

# 네임스페이스 설정 - html의 url 템플릿 태그에 다른 앱들과 구분하기 위해 사용된다.
app_name = 'polls'

urlpatterns = [
    path('', views.index, name='index'),
    path('specifics/<int:question_id>/', views.detail, name='detail'),
    path('<int:question_id>/results/', views.results, name='results'),
    path('<int:question_id>/vote/', views.vote, name='vote'),

]