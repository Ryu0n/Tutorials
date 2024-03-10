# React

## React를 사용하는 3가지 방법
- Online Playgrounds  
이미 웹 상에 React 환경이 구현되어 있다.  

- Add React to a Website  
이미 자신의 웹 사이트가 있다면 부분적으로 React를 적용시킬 수 있다.  

- Toolchain
React로 App을 개발할 때, 목표를 달성하기 위해 필요한 도구 및 환경
(e.g : **Create React App**, Next.js, Gatsby, ...)

## npm과 npx
둘 다 패키지를 설치하고 사용하기 위함. 그러나 npx는 **가상으로** 설치하고 실행하기 (이후 삭제) 때문에 메모리가 소모되지 않고, 실행할 때마다 새로 다운로드를 받기 떄문에 최신화가 매번 일어난다.  

```
npm install -g create-react-app
npx create-react-app [프로젝트명] // 바로 프로젝트가 해당 디렉터리에서 생성된다.
```

## React App 생성하기
(npm으로 create-react-app을 설치했다는 가정하에)
원하는 디렉터리에 react-app이라는 디렉터리 생성 후 해당 디렉터리에서

```
create-react-app .
```

## React App 실행하기
기본적으로 **3000번 포트**를 사용하고 있다.

```
npm run start
```

## 디렉터리 구조
최초로 React App을 실행시켰을 때, 보이는 화면은 index.html이다. index.html을 보면 다음과 같은 부분이 있다.

```
<div id="root"></div>
```

해당 태그 안에 컴포넌트(사용자 정의 태그)가 들어간다. 그것을 어떻게 알까? index.js파일을 보면 알 수 있다. 

```javascript
ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);
```

무언가 이상하지 않은가? JavaScript문법 안에 HTML태그가 들어가 있다. 이것은 React의 **JSX**라는 문법이다. (유사 HTML)
root 태그에 App 태그를 렌더한다. 다음으로 App 태그를 살펴보자.

```javascript
import App from './App';
```

'./App'는 App.js를 의미한다. App.js 스크립트의 App 모듈을 살펴보자.

```javascript
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
```

기본적으로 위와 같은 HTML 태그를 리턴하고 있다. (JSX)

## CSS
index.js 의 import './index.css'를 보자.

```css
// body 태그에 적용
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

// code 태그에 적용
code {
  font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New',
    monospace;
}
```

index.css stylesheet을 import하여 index.html의 디자인을 정의하고 있다.
이 밖에 React에서 CSS를 적용하는 방법들은
https://hello-bryan.tistory.com/114 에서 참조하자.

## 배포

## 시맨틱 태그
특별한 의미는 없지만, 해당 태그가 어떤 영역임을 의미있게 표시한다.  
```jsx
<body>
    <header></header>
    <nav></nav>
    <article></article>
</body>
```

## 컴포넌트
사용자 정의 태그라고 생각하면 쉽다. 대문자로 시작하며, Component 클래스를 상속한다. 구조는 다음과 같다.
파일단위로 분리가 가능하여 재사용성을 높일 수 있으며, 가독성 또한 좋아진다.

```javascript
class ComponentName extends Components {
    render() {
        return(
            JSX Syntax..
        );
    }
}
```

## props
컴포넌트에 속성(인자로써) 부여할 수 있다.
Subject라는 컴포넌트가 있다고 가정한다.

```javascript
// 호출부
<Subject title="WEB" sub="world wide web!"></Subject>

// 컴포넌트
class Subject extends Componenet{
    render() {
        return (
            // JSX Syntax, 시맨틱 태그
            <header>
                <h1>{this.props.title}</h1>
                {this.props.sub}
            </header>
        );
    }
}
```

## state  
props는 (함수 매개변수처럼) 컴포넌트에 전달되는 반면 state는 (함수 내에 선언된 변수처럼) **컴포넌트 안에서** 관리된다.  
state를 통해 컴포넌트의 상태를 지정할 수 있다. state는 **constructor**안에서 지정되어야 한다.  
state의 값이 변경되면 해당 컴포넌트는 **새롭게 render**된다.


```javascript
// App.js

constructor(props){
    
    super(props); // React.Component의 생성자를 먼저 실행
    // state의 값이 바뀌면 state를 가지고 있는 컴포넌트의 render() 재호출(화면이 다시 그려진다.)된다.
    this.state = { 
      mode:'read',
      subject: {title: 'WEB', sub:'World Wide Web!'},
      welcome:{title:'Welcome', desc:'Hello, React!'},
      contents: [
        {id: 1, title: 'HTML', desc: 'HTML is HyperText ...'},
        {id: 2, title: 'CSS', desc: 'CSS if for design'},
        {id: 3, title: 'JavaScript', desc: 'JavaScript is for interactive'},
      ]
    }
  }
```

## state의 참조
props를 참조하듯 this(해당 컴포넌트)의 state를 참조하면 된다.  

```javascript
// App.js

render() {
    var _title, _desc = null;
    if (this.state.mode === 'welcome'){
      _title = this.state.welcome.title;
      _desc = this.state.welcome.desc;
    } else if (this.state.mode === 'read'){
      _title = this.state.contents[0].title;
      _desc = this.state.contents[0].desc;
    }
```

## setState
setState()는 state의 값을 변경할 때 사용된다. 직접 state의 값을 변경해봤자 의미가 없다.

```javascript
// App.js

    return (
      <div className="App">
        <header>
          <h1><a href="/" onClick={
            function(e){
              console.log(e);
              e.preventDefault() 
              this.setState({
                mode: 'welcome'
              });
            }.bind(this) // onClick 콜백 메소드에서 this 키워드(해당 컴포넌트)를 참조할 수 있도록 바인딩
          }>{this.state.subject.title}</a></h1>
```  

여기서 리팩토링을 더 해보면..  

```javascript
// App.js

        <Subject 
          title={this.state.subject.title} 
          sub={this.state.subject.sub}
          onChangePage={function(){
            this.setState({mode: 'welcome'});
            }.bind(this)}>
        </Subject>
```

onChangePage라는 이벤트를 Subject컴포넌트에 props로 넘기고..  

```javascript
// Subject.js

import React, { Component } from 'react'

class Subject extends Component {
    render(){
      return (
        <header>
          <h1><a href="/" onClick={function(e){
            e.preventDefault();
            this.props.onChangePage();
          }.bind(this)}>{this.props.title}</a></h1>
          {this.props.sub}    
        </header>
      );
    }
  }

  export default Subject;
```

props의 onChangePage속성을 onClick funtion에 주입해주면 된다.

## key  
App.js의 state에 TOC.js에 표현할 콘텐츠들을 담고 해당 콘텐츠들을 props로 전달하였다. 이때 각 콘텐츠들을 li 태그를 사용하여 표현할 때, 리액트에서 자체적으로 각 항목들을 구분하기 위한 key라는 속성을 요구한다. 

## event & debugger
우리는 TOC 컴포넌트에서 3개의 항목중에서 클릭한 항목에 대한 내용을 Subject 컴포넌트에 띄우는 것이 목적이다. 큰 맥락을 앞서 설명하자면 TOC 컴포넌트를 클릭했을 때 발생한 이벤트 객체로부터 선택한 항목의 ID를 받아와 해당 항목에 대한 내용을 가져오는 것이 목적이다. 우선 코드부터 수정해보자.  

```javascript
// App.js

  this.state = { 
    mode:'read',
    selected_content_id: 2,
    subject: {title: 'WEB', sub:'World Wide Web!'},
    welcome:{title:'Welcome', desc:'Hello, React!'},
    contents: [
      {id: 1, title: 'HTML', desc: 'HTML is HyperText ...'},
      {id: 2, title: 'CSS', desc: 'CSS if for design'},
      {id: 3, title: 'JavaScript', desc: 'JavaScript is for interactive'},
    ]
  }
```  
state에 현재 선택된 항목의 ID를 나타내는 속성인 selected_content_id를 정의하자.  

```javascript
// App.js

render() {
  var _title, _desc = null;
  if (this.state.mode === 'welcome'){
    _title = this.state.welcome.title;
    _desc = this.state.welcome.desc;
  } else if (this.state.mode === 'read'){
    var i = 0;
    while (i < this.state.contents.length){
      var data = this.state.contents[i];
      if (data.id === this.state.selected_content_id){
        _title = data.title;
        _desc = data.desc;
        break;
      }  
      i += 1;
    }
  }

  ...
  
  <TOC 
    data={this.state.contents} 
    onChangePage={function(){
      this.setState({mode: 'read'});
    }.bind(this)}>
  </TOC>
  
  ...
```  
그리고 TOC의 항목을 클릭하게 되면 state의 mode속성이 read로 변하는 것을 알 수 있으므로 위와 같이 코드를 변경할 수 있다. 그러나 지금은 selected_content_id가 2번으로 고정되어 있기 때문에 어떠한 항목을 선택해도 2번 ID의 CSS의 내용만 나올것이다. 고로 우리는 3가지 항목중 클릭 이벤트로부터 해당 항목에 대한 ID를 얻어내어 selected_content_id를 변경해주어야 한다.  

```javascript
// App.js

  <TOC 
    data={this.state.contents} 
    onChangePage={function(){
      this.setState(
        {mode: 'read',
        this.state.selected_content_id: // 해당 항목에 대한 ID가 들어갈 자리}
        );
    }.bind(this)}>
  </TOC>
```  

```javascript
// TOC.js
      ...
      while(i < data.length){
        lists.push(<li key={data[i].id}>
          <a href={"/content/"+data[i].id} 
          data-id={data[i].id}
          onClick={
            function(e){
              e.preventDefault();
              this.props.onChangePage();
            }.bind(this)}>
            {data[i].title}
          </a>
        </li>);
        i = i + 1;
      }
      ...
```  
우선 각 a태그(각 항목)에 data-id 속성을 부여하자.  

![image](https://user-images.githubusercontent.com/32003817/107636346-d5faeb80-6caf-11eb-898c-dfc84cd6b804.png)
이제 이벤트를 발생시켜보자. 항목을 클릭한 후 크롬의 검사탭에서 esc를 누르면 콘솔창이 나오는데 debugger;가 써있는 곳에서 breakpoint로 걸린다. 콘솔창에 이벤트 객체 변수명인 e를 쳐보면 다음과 같이 나온다. 이벤트 객체의 속성중에 가장 중요한 target이 보일것이다. 여기에는 클릭된 태그가 나온다. (여기서는 a태그가 나온다.)  

![image](https://user-images.githubusercontent.com/32003817/107636686-56b9e780-6cb0-11eb-817b-9f23eec46c08.png)
이어서 해당 target의 속성을 보면 dataset속성에 id속성이 있을것이다. 아까 우리가 data를 접두사로 붙인 data-id속성이다.  

```javascript
// TOC.js 
      ...
      while(i < data.length){
        lists.push(<li key={data[i].id}>
          <a href={"/content/"+data[i].id} 
          data-id={data[i].id} // data는 접두사로써 실제로는 id 속성으로 접근한다. data-id2일 경우 id2로 접근
          onClick={
            function(e){
              e.preventDefault();
              this.props.onChangePage(e.target.dataset.id); // 이곳에 클릭된 항목의 ID를 인자로 넣어주자.
            }.bind(this)}>
            {data[i].title}
          </a>
        </li>);
        ...
```
this.props.onChangePage 콜백 함수의 인자에 클릭된 항목의 ID를 인자로 넣어주면 된다. 왜냐하면 onChangePage속성은 App.js에서 state를 세팅하는 함수를 props로써 넘긴 것이기 때문이다. 그럼 이제 넘겨주었으니 받아서 selected_content_id를 세팅해보자.  

```javascript
// App.js

        <TOC 
          data={this.state.contents} 
          onChangePage={function(id){
            debugger;
            this.setState(
              {mode: 'read',
              selected_content_id: id}
              );
          }.bind(this)}>
        </TOC>
```  
![image](https://user-images.githubusercontent.com/32003817/107638243-a4375400-6cb2-11eb-84b5-c9672e97e9e5.png)
디버거를 찍어봤는데 정상적으로 인자부분에 ID가 넘어오는 것은 확인이 되지만 내용이 변하지 않는 것을 알 수 있다. 그것은 넘어오는 인자값이 **문자**이기 때문이다. 그래서 Number()를 사용하여 타입을 숫자로 캐스팅해야한다.  

```javascript
// App.js

        <TOC 
          data={this.state.contents} 
          onChangePage={function(id){
            this.setState(
              {mode: 'read',
              selected_content_id: Number(id)}
              );
          }.bind(this)}>
        </TOC>
```  

* 꿀팁  
추가로 설명을 덧붙이자면, TOC.js에서 data-id 속성을 통해 각 항목의 ID를 얻어왔다. 하지만 이렇게 하지 않는 방법이 있다. 그것은 bind()를 이용하는 방법이다.  
```javascript
// Toc.js

          <a href={"/content/"+data[i].id} 
          onClick={
            function(id, num, e){
              e.preventDefault();
              this.props.onChangePage(e.target.dataset.id); // 이곳에 클릭된 항목의 ID를 인자로 넣어주자.
            }.bind(this, data[i].id, 10)}>
```  
bind의 this인자 이후로 다른 값들을 파라미터로 넘기게 되면 바인딩되고 있는 함수의 첫번째 인자부터 차곡차곡 들어온다. 마지막 인자는 반드시 이벤트 객체(e)이다.  
(data[i].id -> id, 10 -> num)  

## 베이스 캠프
여태까지 한 내용들을 간단하게 정리하자.  
우선 props와 state의 차이부터 알아보면..  
props는 read-only 성질이 있기 때문에 넘겨주는 쪽에서 수정해서 보내지 않으면 props를 받는 component 내부에서는 props를 수정할 수 없다. 반면 state는 setState()를 통해 수정할 수 있다. 그렇기에 보통 외부로부터 어떤 component의 state에 영향을 주고, DOM에 영향이 가서 react jsx문법이 아닌 실제 HTML문법에 영향을 주게 된다.  

- 한줄 요약  
상위 컴포넌트에 하위 컴포넌트에 영향을 줄 때에는 **props**를 통해서 하위 컴포넌트의 state에 영향을 준다. 반면, 하위 컴포넌트가 상위 컴포넌트에 영향을 주고 싶을 때에는 **이벤트**를 발생시킨다.  

## Create 구현 - 1
우리는 create, update, delete의 state를 변경하기 위한 버튼들을 만들어야 한다.  
```javascript
// App.js

        <ul>
          <li><a href="/create">create</a></li>
          <li><a href="/update">update</a></li>
          <li><input type="button" value="delete"></input></li>
        </ul>
```  
다만, delete같은 경우에는 링크를 통해 접근하는 방식을 지양해야 한다. 왜나하면, 해당 링크가 노출될 경우 사용자가 삭제하려는 데이터 이외의 다른 서버의 데이터를 마음대로 삭제할 수 있기 때문이다. 그래서 반드시 다른 데이터에는 접근하지 못하도록 input 태그를 사용하는 것이 적합하다. 그리고 위의 내용을 Control.js 컴포넌트로 분리시켜준다.  

```javascript
// Control.js

import React, { Component } from 'react'

class Control extends Component {
    render(){
      return (
        <ul>
          <li><a href="/create" onClick={
            function(e){
              e.preventDefault();
              this.props.onChangeMode('create');  // onChangeMode 핸들러 실행
            }.bind(this)
          }>create</a></li>
          <li><a href="/update" onClick={
            function(e){
              e.preventDefault();
              this.props.onChangeMode('update ');  // onChangeMode 핸들러 실행
            }.bind(this)
          }>update</a></li>
          <li><input onClick={
            function(e){
              e.preventDefault();
              this.props.onChangeMode('delete');  // onChangeMode 핸들러 실행
            }.bind(this)
          } type="button" value="delete" ></input></li>
        </ul>
      );
    }
  }

  export default Control;

```  

```javascript
// App.js

        <Control onChangeMode={function(mode){  // Control.js의 onClick속성에서 핸들러에 넘긴 mode인자 'create', 'update', 'delete'
          this.setState(
            {mode: mode}
          )
        }.bind(this)}></Control>
```  


## Create 구현 - 2
우선 state가 welcome일 때와 read상태일 때는 기존에 사용하던 Content 컴포넌트가 나오고, 나머지 상태 (create, update, delete) 상태에서는 각 상태에 적합한 Content 컴포넌트를 출력하기를 원한다. 그래서 기존의 Content 컴포넌트를 ReadContent로 바꾼다. 그리고, Content가 들어가는 부분을 _article이라는 변수로 처리한다.  

```javascript
// App.js

  render() {
    var _title, _desc, _article = null;  // aritcle 변수 선언
    if (this.state.mode === 'welcome'){  // welcome state
      _title = this.state.welcome.title;
      _desc = this.state.welcome.desc;
      _article = <ReadContent title={_title} desc={_desc}></ReadContent>

    } else if (this.state.mode === 'read'){  // read state
      var i = 0;
      while (i < this.state.contents.length){
        var data = this.state.contents[i];
        if (data.id === this.state.selected_content_id){
          _title = data.title;
          _desc = data.desc;
          break;
        }  
        i += 1;
      }
      _article = <ReadContent title={_title} desc={_desc}></ReadContent>

    } else if (this.state.mode === 'create'){  // create state
      _article = <CreateContent></CreateContent>
    }

    return (
      ...
        {_article}  {/* 해당 위치는 가변적으로 Content 컴포넌트를 받을 것이기 때문에 var _article 변수를 통해 동적으로 처리 */}
      ...
```

## Create 구현 - 3
이번 시간에는 CreateContent.js의 form 태그를 구현해볼 것이다.  
```javascript
// CreateContent.js

...

            <form action="create_process" method="post" onSubmit={function(e){
              e.preventDefault();
              alert("Submit!");
            }.bind(this)}>
              <p>
                <input type="text" name="title" placeholder="title"></input>
              </p>
              <p>
                <textarea name="desc" placeholder="description"></textarea>
              </p>
              <p>
                <input type="submit"></input>
              </p>
            </form>

...            
```
간단히 설명하자면 이 코드는 React의 기능을 사용한 것이 아닌 HTML의 native한 기능들로 구현한 것이다. form 태그의 action 속성은 form안의 내용들을 전송할 위치를 의미한다. method 속성은 어떠한 HTML method (GET, POST, PUT, PATCH, DELETE ...)를 사용할 것인지 정하고 onSubmit 속성은 form 태그 내부에서 submit 타입의 input이 발생했을 때 나타나는 이벤트이다. e.preventDefault() 를 통해 action 의 속성값인 create_process 위치로 이동하는 것을 막았다. 

## Create 구현 - 4
이번 시간에는 Create 한 내용을 Content에 추가시키는 작업을 할 것이다. 우선 submit을 하였을 때, 이벤트 객체로부터 title과 description을 얻어와야 한다.  
![image](https://user-images.githubusercontent.com/32003817/108626504-d9525c00-7493-11eb-9e92-a3cfd52c544e.png)
![image](https://user-images.githubusercontent.com/32003817/108626687-cf7d2880-7494-11eb-9ab1-e24a86499890.png)
e.target[0 ~ 2]를 통해 form 태그 안의 각 태그들을을 참조하는 방법  

![image](https://user-images.githubusercontent.com/32003817/108626587-32ba8b00-7494-11eb-99e0-89392d71c15a.png)
![image](https://user-images.githubusercontent.com/32003817/108648521-cf197780-74fe-11eb-9d9a-a8458eceeb6c.png)
e.target.title / e.target.desc를 통해 참조하는 방법  

```javascript
// CreateContent.js
...

            <form action="create_process" method="post" onSubmit={function(e){
              e.preventDefault();
              this.props.onSubmit(
                e.target.title.value,
                e.target.desc.value
              );
              alert("Submit!");
            }.bind(this)}>

...
```  

form 태그에서 제출을 할경우 onSubmit 속성의 이벤트 핸들러가 동작한다. 이때 App.js에서 props로 넘긴 this.props.onSubmit()이 실행된다. App.js를 살펴보자.  

```javascript
// App.js
...

      <CreateContent onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        // setState를 통해 새로운 Content를 추가시키면 된다.
        // this.setState(
        //   {}
        // );
      }.bind(this)}>
      </CreateContent>

...
```
e.target.title.value와 e.target.desc.value값이 CreateContent 컴포넌트의 onSubmit 이벤트 핸들러의 인자(_title, _desc)로 전달되어 출력이 되는 것을 확인할 수 있다.

## Create 구현 - 5
이번 시간에 우리는 제출했을 때 발생한 onSubmit 이벤트로부터 _title, _desc 값을 state에 추가시켜 반영할 것이다.  

```javascript
// App.js

class App extends Component {
  constructor(props){
    super(props); // React.Component의 생성자를 먼저 실행
    
    this.max_content_id = 3;
    ...
```
새로 추가할 콘텐츠의 id를 미리 정의한다.  

```javascript
// App.js

...
      <CreateContent onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        this.max_content_id += 1;
        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        this.state.contents.push(new_content);
        this.setState({contents: this.state.contents})
      }.bind(this)}>
      </CreateContent>
...      
```
새로 추가할 콘텐츠의 id를 증가시킨 후 새로운 콘텐츠의 내용을 push한 상태를 setState해주어야 최종적으로 반영된다. 하지만 push를 사용한 방법은 원본을 바꾸기 때문에 concat으로 대체할 것이다.  

![image](https://user-images.githubusercontent.com/32003817/108791136-b4f49d80-75c1-11eb-86db-c6aa52717b25.png)
push는 위와 같이 arr배열을 직접 값을 추가하는 반면 concat은 값이 추가된 새로운 배열을 반환하기 때문에 원본에 영향을 주지않는다.  

```javascript
// App.js 

        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        var _contents = this.state.contents.concat(new_content);
        this.setState({contents: _contents});
```
이를 적용하면 새로운 콘텐츠가 추가된 또다른 배열인 _contents를 setState() 해주면 된다. 이렇게 할 경우 원본을 직접 영향을 주지 않기 때문에 (내 추측이지만.. undo가 가능할 것이라..) 퍼포먼스가 높아진다고 한다.

## Create 구현 - 6
state의 상태가 바뀌면 하위 컴포넌트들이 전부 다시 렌더링 되는 것은 알고있을 것이다. 그러나, TOC같은 경우에는 표시할 데이터가 줄거나 늘지 않는 이상 다시 렌더링 될 필요가 없다. (다시 렌더링을 할 경우 규모가 큰 프로젝트에서는 퍼포먼스가 떨어짐) 그래서 이를 해결하기위해 **shouldComponentUpdate**에 대해서 알아볼 것이다.  

shouldComponentUpdate() 메소드는 ComponentLifeCycle 인터페이스로부터 구현되야 하는 멤버이다. 그리고, nextProps, nextState, nextContent를 인자로 받고 boolean타입을 반환한다. 이중 우리가 다룰 내용은 nextProps와 nextState인데 이것들이 의미하는 바는 상태가 변한 이후의 props와 state를 의미한다. 그리고 True를 반환할 경우 render() 메소드를 호출하지만 False를 반환한 경우 render() 메소드를 호출하지 않는다. 즉, 우리는 이전상태와 최신상태의 내용을 비교하여 렌더링을 할지 말지 결정할 수 있다.  

정리하자면 shouldComponentUpdate는 다음과 같은 특징을 지닌다.  
* render() 메소드 이전에 호출된다.
* 새로운 props와 state를 얻을 수 있다.
* render() 메소드 호출 여부를 결정할 수 있다.  

```javascript
// TOC.js

class TOC extends Component {
    shouldComponentUpdate(nextProps, nextState, nextContext) {
      var prevContents = this.props.data;
      var nextContents = nextProps.data;
    }

    render() { ... }
    ...
```
this.props.data를 통해 이전 상태의 콘텐츠들과 nextProps.data를 통해 새로운 상태의 콘텐츠들을 들고왔다. 이들을 비교하여 렌더링 여부를 결정하면 된다. 그렇다면 이전 상태의 내용과 현재 상태의 내용을 비교하여 다른 경우에만 true를 리턴한다면 render() 메소드를 호출할 것이다.  

```javascript
// TOC.js

class TOC extends Component {
    shouldComponentUpdate(nextProps, nextState, nextContext) {
      console.log('shouldComponentUpdate');
      var prevContents = this.props.data;
      var nextContents = nextProps.data;
      console.log(prevContents, nextContents)
      if(prevContents === nextContents){
        return false;
      }
      return true;
    }
    render() {
      console.log('Im rendered')
...
```
![image](https://user-images.githubusercontent.com/32003817/109404571-c4e9f400-79aa-11eb-93ea-b8411ca7d974.png)  
최초 로딩할 때 render() 함수를 호출한다. shouldComponentUpdate는 호출되지 않는다.  

![image](https://user-images.githubusercontent.com/32003817/109404617-2d38d580-79ab-11eb-93da-634befc62865.png)  
데이터를 추가하지 않고 목록만 선택한 경우에는 shouldComponentUpdate() 메소드가 호출되지만 변경된 props의 데이터가 없으므로 render() 함수는 호출되지 않는다.  

![image](https://user-images.githubusercontent.com/32003817/109404656-75f08e80-79ab-11eb-9c3a-0d64e2efc85e.png)  
반면 새로운 항목을 추가하자 props의 데이터에 이전 상태와 다르므로 render() 메소드가 호출되는 모습을 보여준다. 그러나 여기서 유의할 점이 있다.  

```javascript
      <CreateContent onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        this.max_content_id += 1;
        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        this.state.contents.push(new_content);
        this.setState({contents: this.state.contents})
      }.bind(this)}>
      </CreateContent>
```
다음과 같이 push를 하게 되면 원본을 수정하게 되므로 이전 props가 변하게 되어 newProps와 비교를 해도 차이를 인지할 수 없다.  
![image](https://user-images.githubusercontent.com/32003817/109404729-2f4f6400-79ac-11eb-9c4a-c64a9690812d.png)
데이터를 새로 추가했음에도 이전 상태가 새로운 상태와 동일한 상태이다.  

## Create 구현 - 7
immutable 은 불변의 속성을 의미한다. 
```javascript
var a = [1, 2];
var b = Array.from(a);
console.log(a, b, a == b);
// VM403:1 (2) [1, 2] (2) [1, 2] false
```
Array.from() 메소드를 사용하면 인자로 넘긴 배열과 동일한 내용의 다른 배열(객체)를 반환한다. 

```javascript
var a = {name: 'egoing'};
var b = Object.assign({}, a);
console.log(a, b, a==b);
// VM1298:1 {name: "egoing"} {name: "egoing"} false
```
Object.assign() 메소드의 첫 번째 인자는 target, 두 번째 인자는 source 이다.  

javascript의 아쉬운 점이 있다면, push(), concat() 메소드처럼 원본 수정여부를 기억해야 하는 일관성이 떨어지는 모습을 볼 수 있다. 그래서 우리는 immutable.js 모듈을 사용하면 모든 객체를 immutable (원본을 수정하지 않고 새로운 객체를 반환하는 형태로 만들 수 있다.  
```javascript
import Immutable from require('immutable');
var map1: Immutable.Map<string, number>;
map1 = Immutable.Map({ a: 1, b: 2, c: 3 }); // Immutable 객체
var map2 = map1.set('b', 50);
map1.get('b'); // 2
map2.get('b'); // 50
```
https://immutable-js.github.io/immutable-js/

## Update 구현
```javascript
// App.js
else if (this.state.mode === 'update'){  // create state
      _article = 
      <UpdateContent onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        this.max_content_id += 1;
        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        var _contents = this.state.contents.concat(new_content);
        this.setState({contents: _contents});
      }.bind(this)}>
      </UpdateContent>
    }
```
update state 에서 업데이트 form을 위한 코드를 작성했다. 그러나 render() 메소드의 크기가 너무 커져서 다음과 같이 함수로 분리했다.  

```javascript
// App.js
  getContent() {
    var _title, _desc, _article = null;  // aritcle 변수 선언
    if (this.state.mode === 'welcome'){  // welcome state
      _title = this.state.welcome.title;
      _desc = this.state.welcome.desc;
      _article = <ReadContent title={_title} desc={_desc}></ReadContent>

    } else if (this.state.mode === 'read'){  // read state
      var i = 0;
      while (i < this.state.contents.length){
        var data = this.state.contents[i];
        if (data.id === this.state.selected_content_id){
          _title = data.title;
          _desc = data.desc;
          break;
        }  
        i += 1;
      }
      _article = <ReadContent title={_title} desc={_desc}></ReadContent>

    } else if (this.state.mode === 'create'){  // create state
      _article = 
      <CreateContent onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        this.max_content_id += 1;
        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        var _contents = this.state.contents.concat(new_content);
        this.setState({contents: _contents});
      }.bind(this)}>
      </CreateContent>

    } else if (this.state.mode === 'update'){  // update state
      _article = 
      <UpdateContent onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        this.max_content_id += 1;
        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        var _contents = this.state.contents.concat(new_content);
        this.setState({contents: _contents});
      }.bind(this)}>
      </UpdateContent>
    }

    return _article;
  }
```
그리고 render() 메소드에서는 다음과 같이 해당 함수를 호출한다.
```javascript
// App.js
  render() {
    return (
      <div className="App">
        <Subject 
          title={this.state.subject.title} 
          sub={this.state.subject.sub}
          onChangePage={function(){
            this.setState({mode: 'welcome'});
          }.bind(this)}>
        </Subject>
        <TOC 
          data={this.state.contents} 
          onChangePage={function(id){
            this.setState(
              {mode: 'read',
              selected_content_id: Number(id)}
              );
          }.bind(this)}>
        </TOC>
        <Control onChangeMode={function(mode){
          this.setState(
            {mode: mode}
          )
        }.bind(this)}></Control>
        {this.getContent()}
      </div>
    );
  }
```
그다음으로 해야할 작업은 어떤 내용을 수정할지 알아야하므로 selected_content_id를 UpdateContent.js 컴포넌트로 넘겨야한다.  
우선 read state 일때의 로직을 메소드로 분리하자. 왜나하면 선택된 콘텐츠의 내용을 update에도 전달해줄때 재사용하기 때문이다.
```javascript
// App.js
  getReadContent() {
    var i = 0;
    while (i < this.state.contents.length){
      var data = this.state.contents[i];
      if (data.id === this.state.selected_content_id){
        return data;
      }  
      i += 1;
    }
  }
```
그리고 read state에서는 다음과 같이 사용한다.
```javascript
// App.js
    } else if (this.state.mode === 'read'){  // read state
      var _content = this.getReadContent();
      _article = <ReadContent title={_content.title} desc={_content.desc}></ReadContent>

    } 
```
update state에서는 다음과 같이 props로 주입한다.
```javascript
// App.js
else if (this.state.mode === 'update'){  // update state
      _content = this.getReadContent();
      _article = 
      <UpdateContent data={_content} onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        this.max_content_id += 1;
        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        var _contents = this.state.contents.concat(new_content);
        this.setState({contents: _contents});
      }.bind(this)}>
      </UpdateContent>
    }
```