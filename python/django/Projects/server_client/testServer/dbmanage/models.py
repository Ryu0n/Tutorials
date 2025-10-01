from django.db import models


# Create your models here.
class LSP(models.Model):
    no = models.IntegerField(default=0)
    duplicate = models.IntegerField(default=0)
    div_1 = models.CharField(max_length=1000)
    div_2 = models.CharField(max_length=1000)
    div_3 = models.CharField(max_length=1000)
    div_4 = models.CharField(max_length=1000)
    file_name = models.CharField(max_length=1000)
    file_dir = models.CharField(max_length=1000)
    value = models.CharField(max_length=1000)

    def __str__(self):
        return self.file_name


class Info(models.Model):
    id = models.IntegerField(primary_key=True)
    project_name = models.CharField(max_length=1000)
    total_pipe_cnt = models.CharField(max_length=1000)
    total_pipe_length = models.FloatField()
    not_check_pipe_cnt = models.IntegerField()
    not_check_pipe_length = models.FloatField()
    total_adjoin_cnt = models.IntegerField()

    def __str__(self):
        return self.id