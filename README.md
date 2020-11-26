# Django tutorial

django 프레임워크는 가상환경에서 사용하는 것을 추천한다. django 버전관리 때문!  

## django 프로젝트 생성  

django 프로젝트를 생성하기 위해서 프로젝트를 생성할 디렉터리에서 다음과 같이 명령어를 입력한다.  

```
django-admin startproject 프로젝트명
```  

생성된 프로젝트의 구조를 살펴보자면

- manage.py : 프로젝트와 상호작용하기 위한 커맨드 라인 유틸리티  

생성된 프로젝트와 같은 이름의 패키지가 생성되어 있다. 해당 패키지 내부를 보면  
- settings.py : 프로젝트의 환경 및 구성
- url.py : URL 패턴 관리 (해당 프로젝트에 생성된 앱에 접근하기 위한 URL 패턴)
- asgi.py : ASGI 호환 웹 서버 진입점
- wsgi.py : WSGI 호환 웹 서버 진입점

## django 프로젝트 내부에 앱 생성  

프로젝트에는 앱을 생성하여 독립적으로 관리할 수 있다. 앱 생성은 manage.py 를 통해 진행한다. (앞으로도 많이 쓸 예정)  

```
python manage.py startapp 앱명
```

## view 작성  

## URLConf 설정  

## DB 설정  

## django 프로젝트에 앱 등록  

## 앱 내부에 모델 설계  

## 관리자 계정 생성  

## 서버 구동  

## 관리자 페이지에 모델 등록  
