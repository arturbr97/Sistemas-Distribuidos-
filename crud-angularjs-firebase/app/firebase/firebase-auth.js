var FirebaseAuth = (function() {
	var _auth = firebase.auth();
	var _publicMethods = {
		createUser: function(user){
			return _auth.createUserWithEmailAndPassword(user.email, user.password);
		},
		signIn: function(user){
			return _auth.signInWithEmailAndPassword(user.email, user.password);
		},
		stateChanged: function(handler){
			_auth.onAuthStateChanged(function(user){
				handler(user);
			});
		},
		signOut: function(){
			_auth.signOut();
		}
	};
	return _publicMethods;
})(FirebaseAuth);