lmsApp.controller("librarianController", function($scope, $http, $window, $location,
		librarianService, $filter, Pagination) {
	if ($location.$$path === "/viewbranches" || $location.$$path === "/branch") {
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
		if (b == undefined) {
			alert("need to select a book");
			return;
		}
		if (b.bookId == null) {
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
	
	/**
	 * Override due date
	 */
	$scope.showOverrideModal = function(branchId) {
		$scope.overrideModal = true;
		$scope.branchId = branchId;
		$http.get("http://localhost:8080/lms/bookloans/"+branchId).success(function(backendBookLoanList){
			backendBookLoanList.forEach(function(e, i){
				$http.get("http://localhost:8080/lms/books/"+e.bookId).success(function(backendBook){
					backendBookLoanList[i].title = backendBook.title;
				});
				$http.get("http://localhost:8080/lms/borrowers/"+e.cardNo).success(function(backendBorrower){
					backendBookLoanList[i].borrowerName = backendBorrower.name;
				});
				var d = new Date(backendBookLoanList[i].dueDate);
				d.setDate(d.getDate() + 1);
				backendBookLoanList[i].realDueDate = d;
			});
			$scope.bookLoans = backendBookLoanList;
		});
	}
	
	
	$scope.extendDate = function(bl) {
		bl.dueDate = bl.realDueDate;
		console.log(bl);
		
		$http.put("http://localhost:8080/lms/bookloans", bl).success(function(){
			$scope.closeModal();
			$http.get("http://localhost:8080/lms/bookloans/"+bl.branchId).success(function(backendBookLoanList){
				backendBookLoanList.forEach(function(e, i){
					$http.get("http://localhost:8080/lms/books/"+e.bookId).success(function(backendBook){
						backendBookLoanList[i].title = backendBook.title;
					});
					$http.get("http://localhost:8080/lms/borrowers/"+e.cardNo).success(function(backendBorrower){
						backendBookLoanList[i].borrowerName = backendBorrower.name;
					});
					var d = new Date(backendBookLoanList[i].dueDate);
					d.setDate(d.getDate() + 1);
					backendBookLoanList[i].realDueDate = d;
				});
				$scope.bookLoans = backendBookLoanList;
			});
		});
		
		
//		$http.put("http://localhost:8080/lms/extendduedate/"+data.branchId+"/"+data.bookId+"/"+data.cardNo).success(function(){
//			$http.get("http://localhost:8080/lms/bookloans/"+data.branchId).success(function(backendBookLoanList){
//				backendBookLoanList.forEach(function(e, i){
//					$http.get("http://localhost:8080/lms/books/"+e.bookId).success(function(backendBook){
//						backendBookLoanList[i].title = backendBook.title;
//					});
//					$http.get("http://localhost:8080/lms/borrowers/"+e.cardNo).success(function(backendBorrower){
//						backendBookLoanList[i].borrowerName = backendBorrower.name;
//					});
//				});
//				$scope.bookLoans = backendBookLoanList;
//			});
//		});
	}
	// end of overriding
	
	$scope.closeModal = function() {
		$scope.manageBranchModal = false;
		$scope.addBookModal = false;
		$scope.deleteBranchModal = false;
		$scope.overrideModal = false;
	};

})