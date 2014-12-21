
var ShortMessage = React.createClass({
	handleClick: function(){
      popup('msgPopUpDiv');
 	},
	render: function(){
		return (
			<div className="shortmessage"
			onClick={this.handleClick}>
				<h5 >{this.props.fromwho}</h5>
				<div >{this.props.senttime}</div>				
				<br />
			</div>
		);
	}
});

var Message = React.createClass({
	render: function(){
		return (
			<div className="message" >
				<h5 >{this.props.fromwho}</h5>
				<div >{this.props.children}</div>
				<div >{this.props.senttime}</div>
				<br />
			</div>
		);
	}
});

var MessageList = React.createClass({
	loadMessagesFromServer: function() {
		var username = Session.get('username');
	    var password = Session.get('password');
	    console.log(username);
	    console.log(password);
		$.ajax({
			url: "http://192.168.1.69:3000/read",
			type: 'POST',
			data: {'username': username, 'password': password},
			async: false,
			success: function(data){
				this.setState({msglist:data});
				this.render();
			}.bind(this),
			error: function(xhr, status, err){
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	getInitialState: function(){
		return {msglist: []};
	},
	componentDidMount: function() {
    	this.loadMessagesFromServer();
	},

  	render: function() {
  		console.log("list: " + this.state.msglist);
		var messages = this.state.msglist.map(function(message){
			var date = new Date(message.Timestamp);
			console.log("time: " + message.Timestamp);
			return (

				<ShortMessage fromwho={message.Provider} senttime={date.toLocaleString()} >
				{message.Message}</ShortMessage>
			);
		});
		return (
			<div className="messageList" id="msglist">
				<h3>List</h3>
				{messages}
			</div>
		);
	}
});

React.render(
	<MessageList />, 
	document.getElementById('content')
);