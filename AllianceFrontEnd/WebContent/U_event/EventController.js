app.controller('EventController', function($scope, $rootScope, $location,
		EventService) {
	$scope.event = {
		eventId : '',
		eventName : '',
		eventContent : '',
		eventVenue : '',
		eventDate : '',
		eventTime : ''

	}

	$scope.myerror = [];

	$scope.eventname = [];

	$scope.saveEvent = function() {
		console.log('enteringsave event in event controller')

		$scope.myerror = EventService.saveEvent($scope.event).then(
				function(response) {
					if (response.status == 200) {
						console.log("successfully inserted event details");
						alert("Create Event successfully");
						$location.path('/home');
					}
				}, function(response) {
					console.log("failure " + response.status);
					if (response.status == 401) {
						$location.path('/login')
					} else {
						console.log(response.data.message)
						alert("Pls. enter valid data..")
						$location.path('/postEvent')
					}
				})
	}

	function getAllEvents() {
		console.log('entering get all events')
		EventService.getAllEvents().then(function(response) {
			console.log(response.status);
			$scope.events = response.data;

		}, function(response) {
			console.log(response.status)
		})

	}
	getAllEvents();

	function getAllEventName() {
		console.log('entering get all event name')
		EventService.getAllEventName().then(function(response) {
			console.log(response.status);
			$scope.eventname = response.data;

		}, function(response) {
			console.log(response.status)
		})

	}
	getAllEventName();

	$scope.deleteEvent = function(id) {
		console.log("entering delete event in controller with id " + id)
		EventService.deleteevent(id).then(function(d) {
			console.log('deleted successfully')
			console.log(d)
			getAllEvents();
			$location.path('/listEvents')
		}, function() {
			console.log("unable to delete the record")
		})
	}

})
