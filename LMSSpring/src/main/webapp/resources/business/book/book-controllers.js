lmsApp.controller("bookController", function($scope, $http, $window, $location, bookService, $filter, Pagination){
	if($location.$$path === "/viewbooks"){
		bookService.getAllItemsService().then(function(backendItemsList){
			$scope.items = backendItemsList;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.items.length / $scope.pagination.perPage);
		});
	}else if($location.$$path === "/addbook"){
		$http.get("http://localhost:8080/lms/initBook").success(function(backendItem){
			$scope.item = backendItem;
		});
		$http.get("http://localhost:8080/lms/publishers").success(function(backendPublishers){
			$scope.publishers = backendPublishers;
		});
	}
	
	$scope.sort = function(){
		$scope.items = $filter('orderBy')($scope.items, 'title');
	}
	
	$scope.saveBook = function() {
		console.log($scope.item);
	}
})