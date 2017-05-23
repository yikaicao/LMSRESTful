/**
 * list of all functions to manipulate entities
 * 
 * This javascript file serves as a connector between front-end and service
 * layer.
 */

lmsApp.controller("adminController", function($scope, $http, $window, $location, $filter, Pagination){
	if($location.$$path === "/viewpublishers"){
		$http.get("http://localhost:8080/lms/publishers").success(function(backendPublishersList){
			$scope.publishers = backendPublishersList;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.publishers.length / $scope.pagination.perPage);
		});
	}else if($location.$$path === "/addauthor"){
		$http.get("http://localhost:8080/lms/initAuthor").success(function(backendAuthorsList){
			$scope.author = backendAuthorsList;
		});
	}
	
	$scope.showEditPublisherModal = function(publisherId){
		$http.get("http://localhost:8080/lms/publishers/"+publisherId).success(function(data){
			$scope.publisher = data;
			$scope.editPublisherModal = true;
		});
	}
	

	$scope.updatePublisher = function(){
		$http.put("http://localhost:8080/lms/publishers", $scope.publisher).success(function(){
			$scope.closeModal();
			$http.get("http://localhost:8080/lms/publishers").success(function(backendPublishersList){
				$scope.publishers = backendPublishersList;
				$scope.pagination = Pagination.getNew(10);
				$scope.pagination.numPages = Math.ceil($scope.publishers.length / $scope.pagination.perPage);
			});
		});
	}
	
	$scope.showDeletePublisherModal = function(publisherId) {
		$http.get("http://localhost:8080/lms/publishers/"+publisherId).success(function(data){
			$scope.publisher = data;
			$scope.deletePublisherModal = true;
		});
	}
	
	$scope.deletePublisher = function(){
		
		$http.delete("http://localhost:8080/lms/publishers/"+$scope.publisher.publisherId).success(function(){
			$scope.deletePublisherModal = false;
			$http.get("http://localhost:8080/lms/publishers").success(function(backendPublishersList){
				$scope.publishers = backendPublishersList;
				$scope.pagination = Pagination.getNew(10);
				$scope.pagination.numPages = Math.ceil($scope.publishers.length / $scope.pagination.perPage);
			});
		});
	}

	$scope.closeModal = function() {
		$scope.editPublisherModal = false;
		$scope.deletePublisherModal = false;
	}
	
})