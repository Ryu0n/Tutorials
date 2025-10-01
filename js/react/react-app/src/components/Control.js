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
              this.props.onChangeMode('update');  // onChangeMode 핸들러 실행
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
