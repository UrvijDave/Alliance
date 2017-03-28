app.controller('NewsController', function($scope, $rootScope, $location,
		NewsService) {
	$scope.allnews;
	$scope.news = {
		bulletinId : '',
		bulletinHead : '',
		bulletinBody : '',
		bulletinDate : ''
	}

	$scope.head = [];

	$scope.myerror = [];

	$scope.saveNews = function() {
		console.log('enteringsave news in news controller')
		$scope.myerror = NewsService.saveNews($scope.news).then(
				function(response) {

					if (response.status == 200) {
						console.log("successfully inserted news details");
						alert("Create News successfully");
						$location.path('/listNews');
					}
				}, function(response) {
					console.log("failure " + response.status);
					if (response.status == 401) {
						$location.path('/login')
					} else {
						console.log(response.data.message)
						alert("Pls. enter valid data..")
						$location.path('/createnews')
					}
				})

	}

	function getAllNews() {
		console.log('entering get all news')
		NewsService.getAllNews().then(function(response) {
			console.log(response.status);
			$scope.allnews = response.data;
		}, function(response) {
			console.log(response.status)
		})

	}
	getAllNews();

	function getAllHead() {
		console.log('entering get all news')
		NewsService.getAllHead().then(function(response) {
			console.log(response.status);
			$scope.head = response.data;
		}, function(response) {
			console.log(response.status)
		})

	}
	getAllHead();

	$scope.deleteNews = function(id) {
		console.log("entering delete news in controller with id " + id)
		NewsService.deletenews(id).then(function(d) {
			console.log('deleted successfully')
			console.log(d)
			getAllNews();
			$location.path('/listNews')
		}, function() {
			console.log("unable to delete the record")
		})
	}
})
