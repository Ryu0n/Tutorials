import React, { Component } from 'react'

import Subject from './components/Subject'
import TOC from './components/TOC';
import Content from './components/Content';

import './App.css';

// class-type
class App extends Component {
  constructor(props){
    super(props);
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

  render() {
    var _title, _desc = null;
    if (this.state.mode === 'welcome'){
      _title = this.state.welcome.title;
      _desc = this.state.welcome.desc;
    } else if (this.state.mode === 'read'){
      _title = this.state.contents[0].title;
      _desc = this.state.contents[0].desc;
    }

    return (
      <div className="App">
        {/* <Subject 
          title={this.state.subject.title} 
          sub={this.state.subject.sub}>
        </Subject> */}
        <header>
          {/* onclick 은 javascript, onClick은 JSX */}
          <h1><a href="/" onClick={function(e){
            // onclick 속성의 callback이 호출되면 이벤트의 기본적인 동작(href(/)로 리다이렉트)을 수행한다.
            console.log(e);
            // debugger; // 크롬 브라우저가 이 키워드를 만나면 멈추고 기다린다.
            e.preventDefault() // 이벤트 객체의 기본적인 동작(리다이렉트)을 막는다. 즉, 페이지를 reload하지 않고 역동적인 동작을 수행할 수 있는 것이다.
            }}>{this.state.subject.title}</a></h1>
          {this.state.subject.sub}    
        </header>
        <TOC data={this.state.contents}></TOC>
        <Content title={_title} desc={_desc}></Content>
      </div>
    );
  }
}

export default App;
