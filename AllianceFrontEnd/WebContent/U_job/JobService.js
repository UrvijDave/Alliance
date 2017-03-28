app.factory('JobService',function($http){
	var jobService=this;
	var BASE_URL= "http://localhost:8088/AllianceBackEnd/"
		
	jobService.saveJob=function(job){
		return $http.post(BASE_URL + "/postJob" , job);
	}
	
	jobService.getAllJobs=function(){
		return $http.get(BASE_URL+"/getAllJobs");
	}
	
	jobService.getAllTitle=function(){
		return $http.get(BASE_URL+"/getAllTitle");
	}

	

	jobService.getJob=function(id){
		return $http.get(BASE_URL + "/job/"+ id)
	};
	
	
	/*
	 * jobService.getDistinctJobStatus=function(id){ return $http.get(BASE_URL +
	 * "/getDistinctJobStatus") };
	 */
	
	jobService.updatejob=function(jobid,job){
		console.log('update job in service')
		console.log('job id ' + jobid)
		return $http.put(BASE_URL + "/job/"+ jobid, job);
	};
	
	jobService.deletejob=function(id){
		console.log("entering delete job in service with id " + id);
		return $http.delete(BASE_URL + "/job/"+id)
		.then(function(response){
			console.log(response.status)
			return response.status;
		},function(response){
			alert("Not authorized to delete job...")
			console.log(response.status)
			})
	};

	/*
	 * jobService.getJobsByStatus=function(st){ console.log("entering approved
	 * job in service"); console.log(BASE_URL + "/jobsta/"+ st) return
	 * $http.get(BASE_URL + "/jobsta/"+ st) .then(function(response){
	 * console.log(response.status) return response.status;
	 * },function(response){ alert("Not authorized to get job...")
	 * console.log(response.status) }) };
	 */
	
	return jobService;
})