import csv
import os
import django
import sys

os.chdir(".")
print("Current dir=", end=""), print(os.getcwd())

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
print("BASE_DIR=", end=""), print(BASE_DIR)

sys.path.append(BASE_DIR)

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "testServer.settings")  # 1. 여기서 프로젝트명.settings입력
django.setup()

# 위의 과정까지가 python manage.py shell을 키는 것과 비슷한 효과

from dbmanage.models import *  # 2. App이름.models

CSV_PATH = 'CSV/LSP.csv'  # 3. csv 파일 경로

with open(CSV_PATH, newline='', encoding='utf-8') as csvfile:  # 4. newline =''
    data_reader = csv.DictReader(csvfile)

    for i, row in enumerate(data_reader):
        print(i, row)
        LSP.objects.create(  # 5. class명.objects.create
            no=int(row['NO']),
            # duplicate= (row['중복']=0 if not row['중복'] else int(row['중복'])),
            div_1=row['구분1'],
            div_2=row['구분2'],
            div_3=row['구분3'],
            div_4=row['구분4'],
            file_name=row['파일명'],
            file_dir=row['경로'],
            value=row['화면표시'],
        )