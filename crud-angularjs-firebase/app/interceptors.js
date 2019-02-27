(function(){
	ManageJobsApp.factory("authInterceptor", function($q, $location){
		return{
			request: function(config){
				var currentPath = $location.path();
				if(currentPath.localeCompare("/Login") === 0 
					|| currentPath.localeCompare("/manageJobs") === 0){
					validSession();
				}
				
				return config;
			},
			requestError: function(rejection){
				return $q.reject(rejection);
			},
			response: function(response){
				return response;
			},
			responseError: function(rejection){
				return $q.reject(rejection);
			}
		};
	});

	ManageJobsApp.config(function($httpProvider){
		$httpProvider.interceptors.push("authInterceptor");
	});
})();