(function(){
	ManageJobsApp.config(function($routeProvider){
		$routeProvider.when("/",{
			templateUrl: "app/users/login.html",
			controller: "userManageAccountCtrl"
		});
		$routeProvider.when("/createAccount",{
			templateUrl: "app/users/create-account.html",
			controller: "userManageAccountCtrl"
		});
		$routeProvider.when("/Login",{
			templateUrl: "app/users/login.html",
			controller: "userManageAccountCtrl"
		});
		$routeProvider.when("/manageJobs",{
			templateUrl: "app/jobs/manage-jobs.html",
			controller: "jobsManageCtrl"
		});
		$routeProvider.when("/newJob",{
			templateUrl: "app/jobs/new-job.html",
			controller: "jobNewCtrl"
		});
	});
})();