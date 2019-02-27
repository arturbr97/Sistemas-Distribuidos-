(function(){
	ManageJobsApp.controller('jobNewCtrl', function($scope){
		$scope.newJob = function(job){
			var db = firebase.database().ref();
			var uid = getUID();
			db.child("/users/" + uid + "/jobs").push().set(job, function(error){
				if(error){

				}else{
					document.getElementById("userJobForm").reset();
					alert("Job created successfuly!");
				}
			});
		}

		$scope.getNameUser = function(){
			var data = getDataSession();
			return data.name;
		}
		$scope.logout = function(){
			invalidateSession();
			window.location.href = "#!/Login";
		}
	});
})();