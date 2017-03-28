app.factory('BlogService',function($http){
	var blogService=this;
	var BASE_URL = "http://localhost:8088/AllianceBackEnd/"
		
	blogService.saveBlog=function(blog){
		return $http.post(BASE_URL + "/creblog" , blog);
	}
	
	blogService.getAllBlogs=function(){
		return $http.get(BASE_URL+"/getAllBlogs");
	}

	blogService.getAllBlogName=function(){
		return $http.get(BASE_URL+"/getAllBlogName");
	}
	
	blogService.getBlog=function(id){
		return $http.get(BASE_URL + "/blog/"+ id)
	};
	
	blogService.updateblog=function(blogid,blog){
		console.log('update blog in service')
		console.log('blog id ' + blogid)
		return $http.put(BASE_URL + "/blog/"+ blogid, blog);
	};
	
	
	blogService.getBlogById=function(blogid){
		console.log('view blog in service')
		console.log('blog id ' + blogid)
		return $http.get(BASE_URL + "/blog/"+ blogid);
	};
	
	blogService.deleteblog=function(id){
		console.log("entering delete blog in service with id " + id);
		return $http.delete(BASE_URL + "/blog/"+id)
		.then(function(response){
			console.log(response.status)
			return response.status;
		},function(response){
			alert("Not authorized to delete blog...")
			console.log(response.status)
			})
	};

	blogService.getBlogsByStatus=function(st){
		console.log("entering approved blog in service");
		console.log(BASE_URL + "/blogsta/"+ st) 
		return $http.get(BASE_URL + "/blogsta/"+ st)
		.then(function(response){
			console.log(response.status)
			return response.status;
		},function(response){
			alert("Not authorized to get blog...")
			console.log(response.status)
			})
	};
	
	
	blogService.updateBlogLikes=function(blogid){
		console.log('update blog likes in service')
		console.log(' id ' + blogid)
		return $http.put(BASE_URL + "/bloglike/"+ blogid);
	};
	
	

	blogService.updateBlogdisLikes=function(blogid){
		console.log('update blog dislikes in service')
		console.log(' id ' + blogid)
		return $http.put(BASE_URL + "/blogdislike/"+ blogid);
	};
	
	
	
	
	return blogService;
})