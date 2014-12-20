/**
 * Module dependencies.
 */

require("./lib/db") ;
var express = require("express");
var http = require("http");
var path = require("path");

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
app.post('/write, write);

http.createServer(app).listen(app.get("port"), 
	function()
	{
		console.log('Express server listening on port ' + app.get('port'));
	}
);

function register(req, res)
{
	console.log("register");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
}

function login(req, res)
{
	console.log("login");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
}

function read(req, res)
{
	console.log("read");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
}

function write(req, res)
{
	console.log("write");
	console.log("Username: " + req.body.username);
	console.log("Password: " + req.body.password);
	console.log("Receiver" + req.body.receiver);
	console.log("Message" + req.body.message);
}