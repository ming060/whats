var LoginForm = React.createClass({
	handleSubmit: function(e) {
    e.preventDefault();
    var username = this.names.username.getDOMNode().value.trim();
    var password = this.names.password.getDOMNode().value.trim();
    if (!password || !username) {
      return;
    }
    // TODO: send request to the server
    this.names.username.getDOMNode().value = '';
    this.names.password.getDOMNode().value = '';
    return;
  },
  render: function() {
    return (
      <form className="loginForm" method="post">
        User Name: <input type="text" name="username" placeholder="User name" /><br/>
        psassword: <input type="password" name="password" placeholder="Password" /><br/>

         <input type="submit" value="login" />
      </form>
    );
  }
});


React.render(
	<LoginForm />, document.getElementById('content')
);