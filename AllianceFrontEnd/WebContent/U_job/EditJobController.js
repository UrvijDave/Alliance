app.controller('EditJobController', function($scope, $routeParams, $location,
		JobService) {

	console.log('entering edit job controller ');

	var jobid = $routeParams.id;
	console.log('entering edit job controller...for jobid' + jobid);

	$scope.job = JobService.getJob(jobid).then(function(response) {
		console.log("status...........=" + response.status)
		$scope.job = response.data;
	}, function(response) {
		console.log("error status...........=" + response.status)
	})

	$scope.update = function() {
		console.log('update function in editcontroller')
		JobService.updatejob(jobid, $scope.job).then(function(response) {
			console.log(response.status)
			$location.path('/listJobsAd')
		}, function(response) {
			console.log(response.status)
		})
	}
})