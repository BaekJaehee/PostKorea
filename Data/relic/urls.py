from django.urls import path
from . import views


urlpatterns = [
    path('list/', views.save_list_data),
    path('detail/', views.save_detail_data),
    path('address/', views.save_regions),
]
