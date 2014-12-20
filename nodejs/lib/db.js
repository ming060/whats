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
		Reciever: String,
		Message: String,
		Timestamp: Number,
	}
);

var Users = mongoose.model("User", User);
var Comments = mongoose.model("Comment", Comment);
mongoose.connect("mongodb://localhost/Guestbook");

exports.VerifyUser = function(username, password, res, callback)
{
	console.log("VerifyUser");
	Users.count(
		{$and:[{"Username": username}, {"Password": password}]}, 
		function(err, count)
		{
			callback(err, count, res);
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