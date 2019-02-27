(function(){
	ManageJobsApp.controller('userManageAccountCtrl', function($scope){

		$scope.createAccount = function(user){
			FirebaseAuth.createUser(user)
		    .then(function(userData){
		      var db = firebase.database().ref();
		      var uid = userData.user.uid;
		      delete user.password;
		      db.child("/users/" + uid).set(user).then(function(){
		      	document.getElementById("userAccountForm").reset();
		      	alert("User account created successfuly!");
		      });
		      
		    })
		    .catch(function(error){
		      var errorCode = error.code;
		      var errorMessage = error.message;
		      alert("Error message: "+errorMessage);
		    });
		};

		$scope.signIn = function(user){
			FirebaseAuth.signIn(user)
		    .then(function(userData){
		      var db = firebase.database().ref();
		      var uid = userData.user.uid;
		      setUID(uid);
		      db.child("/users/" + uid).on("value", function(data){
		      	setDataSession(data);
		      	document.getElementById("userLoginForm").reset();
		      	alert("Logged!");
		      	window.location.href = "#!/manageJobs";
		      });
		    })
		    .catch(function(error){
		      var errorCode = error.code;
		      var errorMessage = error.message;
		      alert("Error message: "+errorMessage);
		    });
		}
	});
})();