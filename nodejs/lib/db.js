var mongoose = require('mongoose');
var Schema = mongoose.Schema;
 
var User = new Schema(
	{
		Username: String,
		Password: String,
	}
);
 
var Comment = new Schema(
	{
		Provider: String,
		Receiver: String,
		Message: String,
		Timestamp: Number,
	}
);

var Users = mongoose.model("User", User);
var Comments = mongoose.model("Comment", Comment);
mongoose.connect("mongodb://localhost/Guestbook");

exports.VerifyUser = function(username, password, req, res, callback)
{
	console.log("VerifyUser");
	Users.count(
		{$and:[{"Username": username}, {"Password": password}]}, 
		function(err, count)
		{
			callback(err, count, req, res);
		}
	);
}

exports.AddUser = function(username, password, res, callback)
{
	var userObj = new Users({"Username": username, "Password": password});
	userObj.save(
		function(err)
		{
			callback(err, res);
		}
	);
}

exports.CheckUser = function(username, req, res, callback)
{
	console.log("CheckUser");
	Users.count(
		{"Username": username}, 
		function(err, count)
		{
			callback(err, count, req, res);
		}
	);
}

exports.ReadComments = function(username, res, callback)
{
	console.log("ReadComments");
	Comments.find(
		{"Receiver": username}, 
		function(err, comments)
		{
			callback(err, comments, res);
		}
	);
}

exports.WriteComment = function(req, res, callback)
{
	var commentObj = new Comments({"Provider": req.body.username, 
									"Receiver": req.body.receiver, 
									"Message": req.body.message,
									"Timestamp": new Date().getTime()});
	commentObj.save(
		function(err)
		{
			callback(err, res);
		}
	);
}