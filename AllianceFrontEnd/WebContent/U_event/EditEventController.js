app.controller('EditEventController', function($scope, $routeParams, $location,
		EventService) {

	console.log('entering edit event controller ');

	var eventid = $routeParams.id;
	console.log('entering edit event controller...for eventid' + eventid);

	$scope.event = EventService.getEvent(eventid).then(function(response) {
		console.log("status...........=" + response.status)
		$scope.event = response.data;
	}, function(response) {
		console.log("error status...........=" + response.status)
	})

	$scope.update = function() {
		console.log('update function in editcontroller')
		EventService.updateevent(eventid, $scope.event).then(
				function(response) {
					console.log(response.status)
					$location.path('/listEvents')
				}, function(response) {
					console.log(response.status)
				})
	}
})