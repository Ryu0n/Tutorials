from django.urls import path

from . import views

urlpatterns = [
    path('c/<str:db>/', views.CreateView.as_view()),
    path('r/<str:db>/<int:all>/', views.GetView.as_view()),
    path('u/<str:db>/<int:no>/', views.UpdateView.as_view()),
]