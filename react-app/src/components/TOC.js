import React, { Component } from 'react'

// Table Of Content
class TOC extends Component {
    // interface ComponentLifeCycle
    // shouldComponentUpdate?(nextProps: Readonly<P>, nextState: Readonly<S>, nextContext: any): boolean;
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
      console.log('Im rendered');
      var lists = [];
      var data = this.props.data;
      var i = 0;
      while(i < data.length){
        lists.push(<li key={data[i].id}>
          <a href={"/content/"+data[i].id} 
          // data-id={data[i].id} // data는 접두사로써 실제로는 id 속성으로 접근한다. data-id2일 경우 id2로 접근
          onClick={
            // function(e){
            function(id, e){
              e.preventDefault();
              // this.props.onChangePage(e.target.dataset.id); // 이곳에 클릭된 항목의 ID를 인자로 넣어주자.
              this.props.onChangePage(id); // 이곳에 클릭된 항목의 ID를 인자로 넣어주자.
            }.bind(this, data[i].id)}>
            {data[i].title}
          </a>
        </li>);
        i = i + 1;
      }
      return (
        <nav>
            <ul>
              {lists}
            </ul>
        </nav>
      );
    }
  }

  export default TOC;
