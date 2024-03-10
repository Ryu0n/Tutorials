import React, { Component } from 'react'

class Counter extends Component {
    constructor(props) {
      super(props);
      this.state = {
        number: 0
      };
    };

    handleClick() {
    // this.setState(() => {
    //     return {number: this.state.number + 1};
    //     });
      this.setState({number: this.state.number + 1});
    };

    render() {
      return (
        <div>
          <h2>{this.state.number}</h2>
          <button onClick={this.handleClick.bind(this)}>Increase {this.state.number}</button>
        </div>
      );
    };
  }

export default Counter;