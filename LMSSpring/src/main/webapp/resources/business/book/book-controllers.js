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
	 * helper functions for creating a new book
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

	/**
	 * helper functions for updating a new book
	 */
	$scope.showEditItemModal = function(itemId) {
		bookService.getBookByPKService(itemId).then(function(data) {
			$scope.thisBookGenres = [];
			$scope.book = data;
			$scope.editItemModal = true;
			$scope.book.genres.forEach(function(e) {
				$scope.thisBookGenres.push(e.genreId);
			});
		});
		$http.get("http://localhost:8080/lms/genres").success(function(data) {
			$scope.genres = data;
		});
	};

	$scope.updateGenre = function(data) {
		var index = $scope.thisBookGenres.indexOf(data);

		// if currently selected
		if (index > -1) {
			$scope.thisBookGenres.splice(index, 1);
		}

		// if newly selected
		else {
			$scope.thisBookGenres.push(data);
		}
	};

	$scope.updateItem = function() {
		// clear out previous selection
		$scope.book.genres = [];
		$scope.thisBookGenres.forEach(function(e) {
			tmpGenreId = {
				genreId : e
			};
			$scope.book.genres.push(tmpGenreId);
		});

		$http.put("http://localhost:8080/lms/books", $scope.book).success(
				function() {
					$scope.editItemModal = false;
					bookService.getAllItemsService().then(
							function(backendItemsList) {
								$scope.items = backendItemsList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.items.length
												/ $scope.pagination.perPage);
							});
				});
	}

	// end of updating new book functions
	
	/**
	 * helper functions for deleting a book
	 */
	$scope.showDeleteBookModal = function(itemId) {
		bookService.getBookByPKService(itemId).then(function(data) {
			$scope.thisBookGenres = [];
			$scope.book = data;
			$scope.deleteItemModal = true;
			$scope.book.genres.forEach(function(e) {
				$scope.thisBookGenres.push(e.genreId);
			});
		});
		$http.get("http://localhost:8080/lms/genres").success(function(data) {
			$scope.genres = data;
		});
	};
	
	$scope.deleteItem = function(){
		$http.delete("http://localhost:8080/lms/books/"+$scope.book.bookId).success(function(){
			$scope.deleteItemModal = false;
			bookService.getAllItemsService().then(
					function(backendItemsList) {
						$scope.items = backendItemsList;
						$scope.pagination = Pagination.getNew(10);
						$scope.pagination.numPages = Math.ceil($scope.items.length
								/ $scope.pagination.perPage);
					});
		});
	}
	// end of deleting a book
	
	$scope.searchItems = function(){
		$http.get("http://localhost:8080/lms/books?searchString="+$scope.searchString).success(function(data){
			$scope.items = data;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.items.length / $scope.pagination.perPage);
		});
	}
	
	$scope.closeModal = function() {
		$scope.editItemModal = false;
		$scope.deleteItemModal = false;
	};

})