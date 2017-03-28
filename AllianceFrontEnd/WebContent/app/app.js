var app = angular.module("myApp", [ 'ngRoute', 'ngCookies',
		'myApp.controllers', 'myApp.services' ]);
angular.module("myApp.controllers", []);
angular.module("myApp.services", []);

app.config(function($routeProvider) {
	console.log('entering configuration');

	$routeProvider.when('/login', {
		controller : 'UserController',
		templateUrl : 'U_user/login.html'

	}).when('/home', {
		controller : 'UserController',
		templateUrl : 'U_home/home.html'

	}).when('/register', {
		controller : 'UserController',
		templateUrl : 'U_user/register.html'

	}).when('/listUsers', {
		controller : 'UserController',
		templateUrl : 'U_user/listUsers.html'

	}).when('/edit', {
		controller : 'EditUserController',
		templateUrl : 'U_user/editUsers.html'

	}).when('/singleUser', {
		controller : 'UserController',
		templateUrl : 'U_user/viewProfile.html'

	}).when('/createblog', {
		controller : 'BlogController',
		templateUrl : 'U_blog/createBlog.html'
	}).when('/listBlogs', {
		controller : 'BlogController',
		templateUrl : 'U_blog/listBlog.html'
	}).when('/editBlog', {
		controller : 'EditBlogController',
		templateUrl : 'U_blog/editBlog.html'

	}).when('/viewBlog', {
		controller : 'BlogController',
		templateUrl : 'U_blog/viewBlog.html'

	}).when('/postJob', {
		controller : 'JobController',
		templateUrl : 'U_job/createJob.html'

	}).when('/listJobs', {
		controller : 'JobController',
		templateUrl : 'U_job/listJobs.html'

	}).when('/listJobsAd', {
		controller : 'JobController',
		templateUrl : 'U_job/listJobsBySta.html'

	}).when('/editJob', {
		controller : 'EditJobController',
		templateUrl : 'U_job/editJob.html'

	}).when('/uploadPicture', {
		templateUrl : 'U_user/uploadPicture.html'

	}).when('/createnews', {
		controller : 'NewsController',
		templateUrl : 'U_news/createNews.html'
	}).when('/listNews', {
		controller : 'NewsController',
		templateUrl : 'U_news/listNews.html'

	}).when('/editNews', {
		controller : 'EditNewsController',
		templateUrl : 'U_news/editNews.html'

	}).when('/postEvent', {
		controller : 'EventController',
		templateUrl : 'U_event/createEvent.html'
	}).when('/listEvents', {
		controller : 'EventController',
		templateUrl : 'U_event/listEvent.html'

	}).when('/editEvent', {
		controller : 'EditEventController',
		templateUrl : 'U_event/editEvent.html'

	}).when('/friendsList', {
		controller : 'FriendController',
		templateUrl : 'U_friend/listofFriends.html'

	}).when('/pendingRequest', {
		controller : 'FriendController',
		templateUrl : 'U_friend/pendingRequest.html'

	}).when('/getAllUsers', {
		controller : 'UserController',
		templateUrl : 'U_user/listOfUsers.html'

	}).when('/chat', {
		controller : 'ChatCtrl',
		templateUrl : 'chat/chat.html'

	})
})

app.run(function($cookieStore, $rootScope, $location, UserService) { // entry
	// point

	console.log($cookieStore.get('currentUser'))
	if ($rootScope.currentUser == undefined)
		$rootScope.currentUser = $cookieStore.get('currentUser')

	$rootScope.logout = function() {
		console.log('logout function')
		delete $rootScope.currentUser;
		$cookieStore.remove('currentUser')
		UserService.logout().then(function(response) {
			console.log("logged out successfully..");
			$rootScope.message = "Logged out Successfully";
			$location.path('/login')
		}, function(response) {
			console.log(response.status);
		})

	}
})
