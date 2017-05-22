lmsApp.controller("librarianController", function($scope, $http, $window, $location,
		librarianService, $filter, Pagination) {
	if ($location.$$path === "/viewbranches") {
		librarianService.getAllItemsService().then(
				function(backendItemsList) {
					$scope.items = backendItemsList;
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
							branchId: bc.branchId,
							noOfCopies: bc.noOfCopies
					};
					

					$http.get("http://localhost:8080/lms/books/"+bc.bookId).success(function(backendBook) {
						bc.bookName = backendBook.title;
					});
					
					$scope.branch.bookCopies.push(bc);
				});
				
				// DEBUG: console.log($scope.branch.bookCopies);
			});
		});
	};
	
	$scope.updateBookCopies = function (bc) {
		if (bc.noOfCopies == undefined) {
			alert("input needs to be equal to or greater than 0");
			return;
		}
		
		$http.put("http://localhost:8080/lms/bookcopies", bc).success(function() {
			$scope.closeModal();
			librarianService.getAllItemsService().then(
					function(backendItemsList) {
						$scope.items = backendItemsList;
					});
		});
	}
	
	$scope.updateBranch = function() {
		$http.put("http://localhost:8080/lms/branches", $scope.branch).success(function(){
			$scope.closeModal();
			librarianService.getAllItemsService().then(
					function(backendItemsList) {
						$scope.items = backendItemsList;
					});
		});
	}
	// end of managing branch detail
	
	/**
	 * helper functions for adding new book copy to the branch
	 */
	$scope.showAddBookModal = function(branchId){
		$scope.addBookModal = true;
		librarianService.getBooksNotAtBranchService(branchId).then(function(data) {
			$scope.missedBooks = data;
			$scope.addCopyToBranch = branchId;
		});
	}
	
	$scope.addBookCopy = function(b) {
		if ($scope.b == undefined) {
			alert("need to select a book");
			return;
		}
			
		if ($scope.b.noOfCopies == undefined) {
			alert("input needs to be equal to or greater than 0");
			return;
		}
		
		var bc = {
				bookId: $scope.b.bookId,
				branchId: $scope.addCopyToBranch,
				noOfCopies: $scope.b.noOfCopies
		}
		$http.post("http://localhost:8080/lms/bookcopies", bc).success(function(){
			$scope.closeModal();
		});
	}
	// end of adding new copy
	
	
	$scope.closeModal = function() {
		$scope.manageBranchModal = false;
		$scope.addBookModal = false;
		$scope.deleteBranchModal = false;
	};

})