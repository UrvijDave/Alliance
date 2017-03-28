app.controller('JobController', function($scope, $rootScope, $location,
		JobService) {
	$scope.job = {
		jobid : '',
		title : '',
		description : '',
		postdate : '',
		qualification : '',
		salary : '',
		location : '',
		experience : '',
		companyname : '',
		status : '',
		expirydate : ''
	}
	$scope.title = [];

	$scope.myerror = [];

	$scope.saveJob = function() {
		console.log('enteringsave job in job controller')
		// console.log($scope.job.postdate)
		// console.log($scope.job.expirydate)
		$scope.myerror = JobService.saveJob($scope.job).then(
				function(response) {
					if (response.status == 200) {
						console.log("successfully inserted job details");
						alert("Posted job successfully");
						$location.path('/listJobs');
					}

				}, function(response) {
					console.log("failure " + response.status);
					if (response.status == 401) {
						$location.path('/login')
					} else {
						console.log(response.data.message)
						alert("Pls. enter valid data..")
						$location.path('/postJob')
					}
				})
	}

	function getAllJobs() {
		console.log('entering get all jobs')
		JobService.getAllJobs().then(function(response) {
			console.log(response.status);
			$scope.jobs = response.data;
		}, function(response) {
			console.log(response.status)
		})

	}
	getAllJobs();

	function getAllTitle() {
		console.log('entering get all jobs title')
		JobService.getAllTitle().then(function(response) {
			console.log(response.status);
			$scope.title = response.data;
		}, function(response) {
			console.log(response.status)
		})

	}
	getAllTitle();

	$scope.deleteJob = function(id) {
		console.log("entering delete job in controller with id " + id)
		JobService.deletejob(id).then(function(d) {
			console.log('deleted successfully')
			console.log(d)
			getAllJobs();
			$location.path('/listJobs')
		}, function() {
			console.log("unable to delete the record")
		})
	}

})
