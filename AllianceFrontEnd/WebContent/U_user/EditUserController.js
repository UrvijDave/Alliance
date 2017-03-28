app.controller('EditUserController', function($scope, $routeParams, $location,
		UserService) {

	console.log('entering edit user controller ');

	var userid = $routeParams.id;
	console.log("user id=" + userid);

	$scope.user = UserService.getUser(userid).then(function(response) {
		console.log("status...........=" + response.status)
		$scope.user = response.data;
	}, function(response) {
		console.log("error status...........=" + response.status)
	})

	$scope.update = function() {
		console.log('update function in editcontroller')
		UserService.updateUser(userid, $scope.user).then(function(response) {
			console.log(response.status)
			$location.path('/listUsers')
		}, function(response) {
			console.log(response.status)
		})
	}
})