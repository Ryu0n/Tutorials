import React, { Component } from 'react'

class Subject extends Component {
    render(){
      return (
        <header>
          {/* onclick 은 javascript, onClick은 JSX */}
          <h1><a href="/" onClick={function(){}}>{this.props.title}</a></h1>
          {this.props.sub}    
        </header>
      );
    }
  }

  export default Subject;
