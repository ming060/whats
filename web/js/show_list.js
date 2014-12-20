var data = [
  {fromwho: "Peter Hunt", content: "Hi, Im Peter", senttime: "2014/12/21"},
  {fromwho: "Jordan Walke", content: "Hi, Im Jordan", senttime: "2014/12/21"}
];

var Message = React.createClass({
	render: function(){
		return (
			<div className="message">
				<h5>{this.props.fromwho}</h5>
				<div>{this.props.children}</div>
				<div>{this.props.senttime}</div>
			</div>
		);
	}
});

var MessageList = React.createClass({
	render: function() {
		var messages = this.props.msglist.map(function(message){
			return (
				<Message fromwho={message.fromwho} senttime={message.senttime}>{message.content}</Message>
			);
		});
		return (
			<div className="messageList">
				<h3>List</h3>
				{messages}
			</div>
		);
	}
});

React.render(
	<MessageList msglist={data}/>, 
	document.getElementById('content')
);