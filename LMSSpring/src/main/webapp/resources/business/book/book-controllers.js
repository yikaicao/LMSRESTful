lmsApp.controller("bookController", function($scope, $http, $window, $location,
		bookService, $filter, Pagination) {
	if ($location.$$path === "/viewbooks") {
		bookService.getAllItemsService().then(
				function(backendItemsList) {
					$scope.items = backendItemsList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math.ceil($scope.items.length
							/ $scope.pagination.perPage);
				});
	} else if ($location.$$path === "/addbook") {
		$http.get("http://localhost:8080/lms/initBook").success(function(data) {
			$scope.item = data;
		});
		$http.get("http://localhost:8080/lms/publishers").success(
				function(data) {
					$scope.publishers = data;
				});
		$http.get("http://localhost:8080/lms/genres").success(function(data) {
			$scope.genres = data;
		});
	}

	$scope.sort = function() {
		$scope.items = $filter('orderBy')($scope.items, 'title');
	};

	/**
	 * helper functions for adding a new book
	 */
	$scope.genreSelection = [];

	$scope.addGenre = function(data) {
		var index = $scope.genreSelection.indexOf(data);

		// if currently selected
		if (index > -1) {
			$scope.genreSelection.splice(index, 1);
		}

		// if newly selected
		else {
			$scope.genreSelection.push(data);
		}
	};

	$scope.saveBook = function() {

		$scope.item.genres = [];

		var tmpGenreId;
		// for each selected genre, prepare an object first
		$scope.genreSelection.forEach(function(e) {
			tmpGenreId = {
				genreId : e
			};
			$scope.item.genres.push(tmpGenreId);
		});

		$http.post("http://localhost:8080/lms/addBook", $scope.item).success(
				function() {
					$window.location.href = "#/viewbooks";
				});
	};
	// end of adding new book functions

})