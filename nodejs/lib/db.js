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

mongoose.model("User", User);
mongoose.model("Comment", Comment);
mongoose.connect("mongodb://localhost/Guestbook");