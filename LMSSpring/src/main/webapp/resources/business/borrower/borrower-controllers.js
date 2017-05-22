lmsApp.controller("borrowerController", function($scope, $http, $window,
		$location, borrowerService, $filter, Pagination) {
	if ($location.$$path === "/viewbranches-borrower") {
		borrowerService.getAllBranchesService().then(
				function(backendItemsList) {
					$scope.items = backendItemsList;
				});
	}

	/**
	 * helper functions for validating borrower
	 */
	$scope.showValidateModal = function(branchId, borrowOrReturn) {
		$scope.branchId = branchId;
		$scope.borrow = false;
		$scope.toReturn = false;
		if (borrowOrReturn == 1)
			$scope.borrow = true;
		else
			$scope.toReturn = true;
		$scope.validateModal = true;
	};

	$scope.validate = function() {

		$http({
			method : 'GET',
			url : "http://localhost:8080/lms/borrowers/" + $scope.borrowerId
		}).then(function successCallback(response) { 
			if (response.status == 200) {
				$scope.closeModal();
				if ($scope.borrow)
					$scope.checkoutModal = true;
				else
					$scope.returnModal = true;
				
				// init borrower object
				$http.get("http://localhost:8080/lms/borrowers/"+$scope.borrowerId).success(function (backendBorrower){
					$scope.borrower = backendBorrower;
				});
				
				// init available books object
				$http.get("http://localhost:8080/lms/availablebooks/"+$scope.borrowerId+"/"+$scope.branchId).success(function (backendAvailableBooks){
					
					backendAvailableBooks.forEach(function(e, i){
						$http.get("http://localhost:8080/lms/bookcopy/"+$scope.branchId+"/"+e.bookId).success(function(data){
							backendAvailableBooks[i].noOfCopies = data.noOfCopies;
						});
					});
					$scope.bookCopies = backendAvailableBooks;
				});
				
				// init book loan records
				$http.get("http://localhost:8080/lms/bookloans/"+$scope.branchId+"/"+$scope.borrowerId).success(function(backendBookLoanList){
					console.log(backendBookLoanList);
				});
			}
			else 
				alert("Borrower validation failed. Please enter again.");
		});
	}
	// end of validating borrower
	
	/**
	 * Check out a book
	 */
	$scope.checkoutBookCopy = function (bc) {
		$http.post("http://localhost:8080/lms/checkoutbook/"+$scope.branchId+"/"+bc.bookId+"/"+$scope.borrowerId).success(function(){
			
			// refresh available books
			$http.get("http://localhost:8080/lms/availablebooks/"+$scope.borrowerId+"/"+$scope.branchId).success(function (backendAvailableBooks){
				
				backendAvailableBooks.forEach(function(e, i){
					$http.get("http://localhost:8080/lms/bookcopy/"+$scope.branchId+"/"+e.bookId).success(function(data){
						backendAvailableBooks[i].noOfCopies = data.noOfCopies;
					});
				});
				$scope.bookCopies = backendAvailableBooks;
			});
			
		});
	}
	// end of checking out a book
	
	/**
	 * Return a book
	 */
	$scope.returnBookCopy = function() {
		
	}

	$scope.closeModal = function() {
		$scope.validateModal = false;
		$scope.checkoutModal = false;
		$scope.returnModal = false;
	};

})