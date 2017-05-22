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
	$scope.showValidateModal = function(branchId) {
		$scope.branchId = branchId;
		$scope.validateModal = true;
	};

	$scope.validate = function() {

		$http({
			method : 'GET',
			url : "http://localhost:8080/lms/borrowers/" + $scope.borrowerId
		}).then(function successCallback(response) { 
			if (response.status == 200) {
				$scope.closeModal();
				$scope.checkoutModal = true;
				
				// init borrower object
				$http.get("http://localhost:8080/lms/borrowers/"+$scope.borrowerId).success(function (backendBorrower){
					$scope.borrower = backendBorrower;
				});
				
				// init available books obkect
				$http.get("http://localhost:8080/lms/availablebooks/"+$scope.borrowerId+"/"+$scope.branchId).success(function (backendAvailableBooks){
					
				});
			}
			else 
				alert("Borrower validation failed. Please enter again.");
		});
	}
	// end of validating borrower

	$scope.closeModal = function() {
		$scope.validateModal = false;
		$scope.checkoutModal = false;
	};

})