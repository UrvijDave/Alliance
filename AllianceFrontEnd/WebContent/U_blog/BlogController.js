app.controller('BlogController', function($scope, $rootScope, $location,
		BlogService) {
	$scope.viewblog1;
	$scope.blog = {
		blogid : '',
		blogname : '',
		username : '',
		blogdesc : '',
		status : '',
		createddate : '',
		blogcomments : '',
		bloglikes : '',
		blogdislikes : ''
	}
	$scope.blogsbysta = [];

	$scope.myerror = [];

	$scope.blognames = [];

					/*-------------------- SAVE BLOG --------------------*/
	
	$scope.saveBlog = function() {
		console.log('enteringsave blog in blog controller')
		BlogService.saveBlog($scope.blog).then(function(response) {
			if (response.status == 200) {
				console.log("successfully inserted blog details");
				alert("Create Blog successfully");
				$location.path('/home');
			}
		}, function(response) {
			console.log("failure " + response.status);

			if (response.status == 401) {
				$location.path('/login')
			} else {
				console.log(response.data.message)
				alert("Pls. enter valid data..")
				$location.path('/createblog')
			}
		})
	}

	
	/*-------------------- GET ALL BLOGS --------------------*/
	
	function getAllBlogs() {
		console.log('entering get all blogs')
		BlogService.getAllBlogs().then(function(response) {
			console.log(response.status);
			$scope.blogs = response.data;

		}, function(response) {
			console.log(response.status)
		})
	}
	getAllBlogs();

	$scope.getBlogById = function(id) {
		console.log("entering get blog in controller with id " + id)
		BlogService.getBlogById(id).then(function(response) {
			console.log(response.status);
			$scope.viewblog1 = response.data;
			$rootScope.viewblog = $scope.viewblog1;
			$location.path('/viewBlog')
		}, function() {
			console.log("unable to get the record")
		})
	}
	
	/*-------------------- GET BLOGS BY STATUS --------------------*/
	
	$scope.getBlogsByStatus = function(id) {
		console.log('entering get all Blogs by status')
		BlogService.getBlogsByStatus("P").then(function(response) {

			$scope.blogsbysta = response.data;
			console.log("response.status");
		}, function(response) {
			console.log(response.status)
		})
	}

	
	/*-------------------- DELETE BLOG --------------------*/
	
	$scope.deleteBlog = function(id) {
		console.log("entering delete blog in controller with id " + id)
		BlogService.deleteblog(id).then(function(d) {
			console.log('deleted successfully')
			console.log(d)
			getAllBlogs();
			$location.path('/listBlogs')
		}, function() {
			console.log("unable to delete the record")
		})
	}

	
	/*-------------------- UPDATE BLOG LIKES --------------------*/
	
	$scope.updateBlogLikes = function(id) {
		console.log('update likes function in editcontroller')
		BlogService.updateBlogLikes(id).then(function(response) {
			console.log(response.status)
			getAllBlogs();
			$location.path('/listBlogs')
		}, function(response) {
			console.log(response.status)
		})
	}

	
	/*-------------------- UPDATE BLOG DISLIKES --------------------*/
	
	$scope.updateBlogdisLikes = function(id) {
		console.log('update dislikes function in editcontroller')
		BlogService.updateBlogdisLikes(id).then(function(response) {
			console.log(response.status)
			getAllBlogs();
			$location.path('/listBlogs')
		}, function(response) {
			console.log(response.status)
		})
	}

})