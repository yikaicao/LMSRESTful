lmsApp.controller("bookController", function($scope, $http, $window, $location, authorService, $filter, Pagination){
	if($location.$$path === "/viewbooks"){
		bookService.getAllItemsService().then(function(backendItemsList){
			$scope.items = backendItemsList;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.items.length / $scope.pagination.perPage);
		});
	}else if($location.$$path === "/additem"){
		$http.get("http://localhost:8080/lms/initBook").success(function(backendItemsList){
			$scope.item = backendItemsList;
		});
	}
	
})