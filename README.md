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

```
ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);
```

무언가 이상하지 않은가? JavaScript문법 안에 HTML태그가 들어가 있다. 이것은 React의 **JSX**라는 문법이다. (유사 HTML)
root 태그에 App 태그를 렌더한다. 다음으로 App 태그를 살펴보자.

```
import App from './App';
```

'./App'는 App.js를 의미한다. App.js 스크립트의 App 모듈을 살펴보자.

```
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

```
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
```
<body>
    <header></header>
    <nav></nav>
    <article></article>
</body>
```

## 컴포넌트
사용자 정의 태그라고 생각하면 쉽다. 대문자로 시작하며, Component 클래스를 상속한다. 구조는 다음과 같다.
파일단위로 분리가 가능하여 재사용성을 높일 수 있으며, 가독성 또한 좋아진다.

```
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

```
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


```
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

```
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

```
    return (
      <div className="App">
        <header>
          <h1><a href="/" onClick={
            function(e){
              console.log(e);
              e.preventDefault() 
              this.setState({
                'mode': 'welcome'
              });
            }.bind(this) // onClick 콜백 메소드에서 this 키워드(해당 컴포넌트)를 참조할 수 있도록 바인딩
          }>{this.state.subject.title}</a></h1>
```  

여기서 리팩토링을 더 해보면..  

```
        <Subject 
          title={this.state.subject.title} 
          sub={this.state.subject.sub}
          onChangePage={function(){
            this.setState({'mode': 'welcome'});
            }.bind(this)}>
        </Subject>
```

onChangePage라는 이벤트를 Subject컴포넌트에 props로 넘기고..  

```
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
App.js의 state에 TOC.js에 표현할 콘텐츠들을 담고 해당 콘텐츠들을 props로 전달하였다. 이때 각 콘텐츠들을 <li>태그를 사용하여 표현할 때, 리액트에서 자체적으로 각 항목들을 구분하기 위한 key라는 속성을 요구한다. 
## event & debugger
