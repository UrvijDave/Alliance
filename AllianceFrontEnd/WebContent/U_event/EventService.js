app.factory('EventService',function($http){
	var eventService=this;
	var BASE_URL="http://localhost:8088/AllianceBackEnd/"
		
	eventService.saveEvent=function(event){
		return $http.post(BASE_URL + "/postEvent" , event);
	}
	
	eventService.getAllEvents=function(){
		return $http.get(BASE_URL+"/getAllEvents");
	}

	eventService.getAllEventName=function(){
		return $http.get(BASE_URL+"/getAllEventName");
	}

	
	eventService.getEvent=function(id){
		return $http.get(BASE_URL + "/event/"+ id)
	};
	
	eventService.updateevent=function(eventid,event){
		console.log('update event in service')
		console.log('event id ' + eventid)
		return $http.put(BASE_URL + "/event/"+ eventid, event);
	};
	
	eventService.deleteevent=function(id){
		console.log("entering delete event in service with id " + id);
		return $http.delete(BASE_URL + "/event/"+id)
		.then(function(response){
			console.log(response.status)
			return response.status;
		},function(response){
			alert("Not authorized to delete event...")
			console.log(response.status)
			})
	};
	
	return eventService;
})