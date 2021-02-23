import React, { Component } from 'react'

import Subject from './components/Subject'
import TOC from './components/TOC';
import ReadContent from './components/ReadContent';
import CreateContent from './components/CreateContent';
import Control from './components/Control'
// import Counter from './components/example';

import './App.css';

// class-type
class App extends Component {
  constructor(props){
    super(props); // React.Component의 생성자를 먼저 실행
    
    this.max_content_id = 3;
    // state의 값이 바뀌면 state를 가지고 있는 컴포넌트의 render() 재호출(화면이 다시 그려진다.)된다.
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
  }

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
      _article = 
      <CreateContent onSubmit={function(_title, _desc){
        console.log(_title, _desc);  // onSubmit으로부터 값을 가져오는데 성공!!
        this.max_content_id += 1;
        var new_content = {id: this.max_content_id, title: _title, desc: _desc};
        // this.state.contents.push(new_content);
        // this.setState({contents: this.state.contents})
        var _contents = this.state.contents.concat(new_content);
        this.setState({contents: _contents});
      }.bind(this)}>
      </CreateContent>
    }

    return (
      <div className="App">

        {/* <header>
          <h1><a href="/" onClick={
            function(e){
              // onclick 속성의 callback이 호출되면 이벤트의 기본적인 동작(href(/)로 리다이렉트)을 수행한다.
              console.log(e);
              // debugger; // 크롬 브라우저가 이 키워드를 만나면 멈추고 기다린다.
              e.preventDefault() // 이벤트 객체의 기본적인 동작(리다이렉트)을 막는다. 즉, 페이지를 reload하지 않고 역동적인 동작을 수행할 수 있는 것이다.
              // this.state.mode = 'welcome'; // 바인딩을 하지 않으면 this가 자기 자신(Component)를 가르키지 않는다. 하지만 바인딩을 해도 리액트에서는 이 뜻을 못알아 먹기 때문에..
              // setState() 메소드를 사용해야 state를 변경할 수 있다.
              this.setState({
                'mode': 'welcome'
              });
            }.bind(this) // onClick 콜백 메소드에서 this 키워드를 참조할 수 있도록 바인딩
          }>{this.state.subject.title}</a></h1>
          {this.state.subject.sub}    
        </header> */}

        {/* <Counter></Counter> */}

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
        {_article}  {/* 해당 위치는 가변적으로 Content 컴포넌트를 받을 것이기 때문에 var _article 변수 처리 */}
      </div>
    );
  }
}

export default App;



// // binding
// var obj = {name: 'ryuon'}
// function bindTest() {
//   console.log(this.name);
// }
// // bind() 리턴값 : 인자로 넣은 객체가 this로 매핑된 메소드
// bindTest2 = bindTest().bind(obj);
