var LoginForm = React.createClass({
  login: function(username, password) {
    console.log('username: ' + username);
    console.log('password: ' + password);
    $.ajax({
      type: "POST",
      url: "http://192.168.1.69:3000/login",
      data: {'username': username, 'password': password},
      error(jqXHR, textStatus, errorThrown){
        console.log('err');
        alert("Login Fail");
        console.log(jqXHR);
      },
      success(data, textStatus, jqXHR) {
        console.log('data'+ data);
      }
    })
    .done(function(msg) {
      console.log('msg: '+ msg);
      window.location.assign("show_messages.html");
    });
  },
	handleSubmit: function(e) {
    e.preventDefault();
    var username = this.refs.username.getDOMNode().value.trim();
    var password = this.refs.password.getDOMNode().value.trim();

    if (!password || !username) {
      alert("User Name & password should not be empty");
      return;
    }
    // TODO: send request to the server
    this.login(username, password);
    return;
  },
  render: function() {
    return (

      <div className="center-div" >
      <form className="loginForm" method="post" onSubmit={this.handleSubmit}>
        User Name: <input type="text" ref="username" placeholder="User name" /><br/>
        psassword: <input type="password" ref="password" placeholder="Password" /><br/>
        <input type="submit" value="login" />
      </form>
      </div>
    );
  }
});


React.render(
	<LoginForm />, document.getElementById('content')
);