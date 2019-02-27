(function(){
	ManageJobsApp.controller('jobsManageCtrl', function($scope, $timeout){

		$scope.jobs = [];

		function getAllJobs(){
			$timeout(function(){
				var db = firebase.database().ref();
				var uid = getUID();
				db.child("/users/" + uid + "/jobs/").on("value", function(snap){
					var keys = (snap.val() == null) ? [] :Object.keys(snap.val());
					$scope.keys = keys;
					$scope.jobs = [];
					var data = (snap.val()== null) ? [] : Object.values(angular.copy(snap.val()));

					data.forEach(function(job){
						$scope.jobs.push(job);
						setTimeout(function(){ $scope.$apply(); }, 0);
					});
				});
			});
		}

		getAllJobs();

		$scope.newJob = function(job){
			var db = firebase.database().ref();
			var uid = getUID();
			db.child("/users/" + uid + "/jobs").push().set(job);
		}

		$scope.getNameUser = function(){
			var data = getDataSession();
			return data.name;
		}
		$scope.logout = function(){
			invalidateSession();
			window.location.href = "#!/Login";
		}
		$scope.deleteJob = function(key){
			var db = firebase.database().ref();
			var uid = getUID();
			db.child("/users/" + uid + "/jobs/"+key).set(null, function(error){
				if(error){

				}else{
					alert("Job deleted successfuly!");
				}
			});
		}
	});
})();