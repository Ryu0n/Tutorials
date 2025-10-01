from django.contrib import admin
from .models import LSP, Info


# Register your models here.
class PostTable(admin.ModelAdmin):
    list_display = (
        'no',
        'duplicate',
        'div_1',
        'div_2',
        'div_3',
        'div_4',
        'file_name',
        'file_dir',
    )

    list_display_links = (
        'no',
        'file_name'
    )

    search_fields = ['duplicate', 'div_1', 'div_2', 'div_3', 'div_4']


admin.site.register(LSP, PostTable)
admin.site.register(Info)
