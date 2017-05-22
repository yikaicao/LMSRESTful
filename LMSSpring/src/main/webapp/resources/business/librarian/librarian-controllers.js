lmsApp.controller("librarianController", function($scope, $http, $window, $location,
		librarianService, $filter, Pagination) {
	if ($location.$$path === "/viewbranches") {
		librarianService.getAllItemsService().then(
				function(backendItemsList) {
					$scope.items = backendItemsList;
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

	/**
	 * helper functions for managing branch detail
	 */
	$scope.showManageBranchModal = function(itemId) {
		$scope.manageBranchModal = true;
		librarianService.getBranchByPKService(itemId).then(function(data) {
			
			// set the branch to the scope
			$scope.branch = data;
			
			// retrieve book copies info from back end
			librarianService.getBookCopiesAtBranchService(itemId).then(function(backendBookCopies){
				
				// prepare front end object
				$scope.branch.bookCopies = [];
				
				backendBookCopies.forEach(function(bc){
					
					
					var bc = {
							bookId: bc.bookId,
							noOfCopies: bc.noOfCopies
					};
					

					$http.get("http://localhost:8080/lms/books/"+bc.bookId).success(function(backendBook) {
						bc.bookName = backendBook.title;
					});
					
					$scope.branch.bookCopies.push(bc);
				});
				
				//DEBUG: console.log($scope.branch.bookCopies);
			});
		});
	};

	/**
	 * helper functions for deleting a branch
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
	
	$scope.closeModal = function() {
		$scope.manageBranchModal = false;
		$scope.deleteBranchModal = false;
	};

})