/**
 * Module dependencies.
 */

var db = require("./lib/db") ;
var express = require("express");
var http = require("http");
var path = require("path");
//var cors = require("cors");

var mongoose = require("mongoose");
var Users = mongoose.model("User");
var Comments = mongoose.model("Comment");

var app = express();

// all environments
app.set("port", process.env.PORT || 3000);
app.use(express.favicon());
app.use(express.logger("dev"));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.configure(SetConfigure);
function SetConfigure()
{
	app.use(express.cookieParser());
	app.use(express.cookieSession(
		{	
			key: "node",
			secret: "HelloExpressSESSION"
		}
	));
	app.use(express.bodyParser());
	//app.use(cors);
}
app.use(app.router);
app.use(express.static(path.join(__dirname, "public")));

// development only
if ("development" == app.get('env'))
{
  app.use(express.errorHandler());
}

app.post("/register", register);
app.post("/login", login);
app.post("/read", read);
app.post("/write", write);

http.createServer(app).listen(app.get("port"), 
	function()
	{
		console.log('Express server listening on port ' + app.get('port'));
	}
);

function Respond(status, msg, isJSON, res)
{
	console.log("status: " + status);
	console.log("message: " + msg);
	res.status(status)
	if (isJSON)
	{
		res.json(msg);
	}
	else
	{
		res.send(msg);
	}
	res.end();
}

function register(req, res)
{
	console.log("register");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
	if (!req.body.username || !req.body.password || !req.body.username.length || !req.body.password.length)
	{
		Respond(403, "empty input", false, res);
		return;
	}
	db.CheckUser(req.body.username, req, res, CheckUserRes);
}

function CheckUserRes(err, count, req, res)
{
	var status;
	var msg;
	if (err || count)
	{
		if (err)
		{
			status = 403;
			msg = "read user WTF";
		}
		else
		{
			status = 403;
			msg = "user is existed";
		}
		Respond(status, msg, false, res);
		return;
	}
	else
	{
		db.AddUser(req.body.username, req.body.password, res, AddUserRes);
	}
}

function AddUserRes(err, res)
{
	var status;
	var msg;
	if (err)
	{
		status = 403;
		msg = "write DB WTF";
	}
	else
	{
		status = 200;
		msg = "ok";
	}
	Respond(status, msg, false, res);
	return;
}

function login(req, res)
{
	console.log("login");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
	if (!req.body.username || !req.body.password || !req.body.username.length || !req.body.password.length)
	{
		Respond(403, "empty input", res);
		return;
	}
	db.VerifyUser(req.body.username, req.body.password, req, res, VerifyRes);
}

function VerifyRes(err, count, req, res)
{
	var status;
	var msg;
	if (err)
	{
		status = 403;
		msg = "WTF";
	}
	else if (!count)
	{
		status = 403;
		msg = "invalid input";
	}
	else
	{
		status = 200;
		msg = "ok";
	}
	Respond(status, msg, false, res);
}

function read(req, res)
{
	console.log("read");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
	if (!req.body.username || !req.body.password || !req.body.username.length || !req.body.password.length)
	{
		Respond(403, "empty input", false, res);
		return;
	}
	
	db.VerifyUser(req.body.username, req.body.password, req, res, VerifyUserForRead);
}

function VerifyUserForRead(err, count, req, res)
{
	var status;
	var msg;
	if (err || !count)
	{
		if (err)
		{
			status = 403;
			msg = "read user WTF";
		}
		else
		{
			status = 403;
			msg = "user is not existed";
		}
		Respond(status, msg, false, res);
		return;
	}
	else
	{
		db.ReadComments(req.body.username, res, ReadCommentsRes);
	}
}

function ReadCommentsRes(err, comments, res)
{
	var status;
	var msg;
	if (err)
	{
		status = 403;
		msg = "WTF";
	}
	else
	{
		status = 200;
		msg = comments;
	}
	Respond(status, msg, true, res);
}

function write(req, res)
{
	console.log("write");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
	console.log("Receiver: " + req.body.receiver);
	console.log("Message: " + req.body.message);
	if (!req.body.username || !req.body.password || 
		!req.body.username.length || !req.body.password.length ||
		!req.body.receiver || !req.body.receiver.length ||
		!req.body.message || !req.body.message.length)
	{
		Respond(403, "empty input", false, res);
		return;
	}
	
	db.VerifyUser(req.body.username, req.body.password, req, res, VerifyUserForWrite);
}

function VerifyUserForWrite(err, count, req, res)
{
	if (err || !count)
	{
		if (err)
		{
			status = 403;
			msg = "read user WTF";
		}
		else
		{
			status = 403;
			msg = "user is not existed";
		}
		Respond(status, msg, false, res);
		return;
	}
	else
	{
		db.CheckUser(req.body.receiver, req, res, CheckUserForWrite);
	}
}

function CheckUserForWrite(err, count, req, res)
{
	if (err || !count)
	{
		if (err)
		{
			status = 403;
			msg = "read receiver WTF";
		}
		else
		{
			status = 403;
			msg = "user is not existed";
		}
		Respond(status, msg, false, res);
		return;
	}
	else
	{
		db.WriteComment(req, res, WriteCommentRes);
	}
}

function WriteCommentRes(err, res)
{
	var status;
	var msg;
	if (err)
	{
		status = 403;
		msg = "read user WTF";
	}
	else
	{
		status = 200;
		msg = "ok";
	}
	Respond(status, msg, false, res);
	return;
}
