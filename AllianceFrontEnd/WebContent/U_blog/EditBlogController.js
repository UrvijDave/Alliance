app.controller('EditBlogController', function($scope, $routeParams, $location,
		BlogService) {

	console.log('entering edit blog controller ');

	var blogid = $routeParams.id;
	console.log('entering edit blog controller...for blogid' + blogid);

	$scope.blog = BlogService.getBlog(blogid).then(function(response) {
		console.log("status...........=" + response.status)
		$scope.blog = response.data;
	}, function(response) {
		console.log("error status...........=" + response.status)
	})

	$scope.update = function() {
		console.log('update function in editcontroller')
		BlogService.updateblog(blogid, $scope.blog).then(function(response) {
			console.log(response.status)
			$location.path('/listBlogs')
		}, function(response) {
			console.log(response.status)
		})
	}
})