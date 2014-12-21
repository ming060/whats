
var globalVar;
var ShortMessage = React.createClass({
	updatePopUpDiv: function(popupDiv, fromwho, message, senttime){
		while (popupDiv.firstChild) {
		    popupDiv.removeChild(popupDiv.firstChild);
		}

		var elementToAdd = document.createElement("a");
		var textToShow = document.createTextNode("[x]Close"); 
		elementToAdd.appendChild(textToShow);
		elementToAdd.href="#";
		elementToAdd.onclick=function() {
			popup('msgPopUpDiv');
			popup('fire');
			setTimeout(function(){popup('fire');}, 2000);
		};
		popupDiv.appendChild(elementToAdd);
		elementToAdd = document.createElement("h5");
		textToShow = document.createTextNode(fromwho); 
		elementToAdd.appendChild(textToShow);
		popupDiv.appendChild(elementToAdd);
		elementToAdd = document.createElement("DIV");
		textToShow = document.createTextNode(message); 
		elementToAdd.appendChild(textToShow);
		popupDiv.appendChild(elementToAdd);
		elementToAdd = document.createElement("DIV");
		textToShow = document.createTextNode(senttime); 
		elementToAdd.appendChild(textToShow);
		popupDiv.appendChild(elementToAdd);


	},

	transformMsg: function (element){
		//
		console.log("timeout " + element);
      		
		this.togglePopup();	
		this.fade(element);
		// window.clearTimeout(globalVar);
	},


	togglePopup: function () {
	    popup('msgPopUpDiv');
		
	},

	fade: function (element) {
	    var op = 1;  // initial opacity
	    var timer = setInterval(function () {
	        if (op <= 0.1){
	            clearInterval(timer);
	            element.style.display = 'none';
	            // popup('fire');
	        }
	        element.style.opacity = op;
	        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
	        op -= op * 0.05;
	    }, 20);
	},

	handleClick: function(fromwho, message, senttime, elementId){
		var popupDiv = document.getElementById('msgPopUpDiv');
		console.log("elementId = " + elementId);

		var messageList = document.getElementById("msglist");
		if (!messageList || !messageList.firstChild) {
			return;
		}

		var messageElement = document.getElementById(elementId);
		
		this.updatePopUpDiv(popupDiv, fromwho, message, senttime);
      	this.togglePopup();
      	this.fade(messageElement);
      	// globalVar = setTimeout(
      	// 	this.transformMsg(messageElement), 2000);
 	},

	render: function(){
		console.log("prop " + this.props);
		return (
			<div className="shortmessage" id={this.props.id} 
			onClick={this.handleClick.bind(this, this.props.fromwho, this.props.children, this.props.senttime, this.props.id)}>
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
	handleClick: function(){
		console.log("list click");
	},
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

				<ShortMessage fromwho={message.Provider} senttime={date.toLocaleString()} id={message.Provider+message.Timestamp}>
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