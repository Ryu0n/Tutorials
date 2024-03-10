# Generated by Django 3.1.3 on 2020-11-20 07:45

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('dbmanage', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Info',
            fields=[
                ('id', models.IntegerField(primary_key=True, serialize=False)),
                ('project_name', models.CharField(max_length=1000)),
                ('total_pipe_cnt', models.CharField(max_length=1000)),
                ('total_pipe_length', models.FloatField()),
                ('not_check_pipe_cnt', models.IntegerField()),
                ('not_check_pipe_length', models.FloatField()),
                ('total_adjoin_cnt', models.IntegerField()),
            ],
        ),
    ]