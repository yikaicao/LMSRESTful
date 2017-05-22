lmsApp.controller("borrowerController", function($scope, $http, $window, $location,
		borrowerService, $filter, Pagination) {
	if ($location.$$path === "/viewbranches") {
		librarianService.getAllItemsService().then(
				function(backendItemsList) {
					$scope.items = backendItemsList;
				});
	} 


})