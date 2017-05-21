lmsApp.controller("bookController", function($scope, $http, $window, $location, bookService, $filter, Pagination){
	if($location.$$path === "/viewbooks"){
		bookService.getAllItemsService().then(function(backendItemsList){
			$scope.items = backendItemsList;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.items.length / $scope.pagination.perPage);
		});
	}else if($location.$$path === "/addbook"){
		$http.get("http://localhost:8080/lms/initBook").success(function(data){
			$scope.item = data;
		});
		$http.get("http://localhost:8080/lms/publishers").success(function(data){
			$scope.publishers = data;
		});
		$http.get("http://localhost:8080/lms/genres").success(function(data){
			$scope.genres = data;
		});
	}
	
	$scope.selection = [];
	$scope.toggleSelection = function (data){
		var index = $scope.selection.indexOf(data);

	    // if currently selected
	    if (index > -1) {
	      $scope.selection.splice(index, 1);
	    }

	    // if newly selected
	    else {
	      $scope.selection.push(data);
	    }
	};
	
	$scope.selectGenre = function() {
		console.log($scope.genres);
	}
	
	$scope.sort = function(){
		$scope.items = $filter('orderBy')($scope.items, 'title');
	}
	
	$scope.saveBook = function() {
		console.log($scope.item);
		console.log($scope.selection);
	}
	
})