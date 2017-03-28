app.controller('EditNewsController', function($scope, $routeParams, $location,
		NewsService) {

	console.log('entering edit news controller ');

	var bulletinid = $routeParams.id;
	console.log('entering edit news controller...for bulletinid' + bulletinid);

	$scope.news = NewsService.getNews(bulletinid).then(function(response) {
		console.log("status...........=" + response.status)
		$scope.news = response.data;
	}, function(response) {
		console.log("error status...........=" + response.status)
	})

	$scope.update = function() {
		console.log('update function in editcontroller')
		NewsService.updatenews(bulletinid, $scope.news).then(
				function(response) {
					console.log(response.status)
					$location.path('/listNews')
				}, function(response) {
					console.log(response.status)
				})
	}
})