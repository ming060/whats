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

app.get("/register", register);
app.get("/login", login);
app.get("/read/:id", read);
app.post('/write/:id', write);

http.createServer(app).listen(app.get("port"), 
	function()
	{
		console.log('Express server listening on port ' + app.get('port'));
	}
);

function register(req, res)
{
	console.log("register");
	console.log("Username: " + req.query.username);
	console.log("Password: " + req.query.password);
}

function login(req, res)
{
	console.log("login");
	console.log("Username: " + req.query.username);
	console.log("Password: " + req.query.password);
}

function read(req, res)
{
	console.log("read" + req.params.id);
}

function write(req, res)
{
	console.log("write" + req.params.id);
	console.log("Producer" + req.body.producer);
	console.log("Receiver" + req.body.receiver);
}