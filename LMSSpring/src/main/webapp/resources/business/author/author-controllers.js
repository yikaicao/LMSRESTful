lmsApp.controller("authorController", function($scope, $http, $window, $location, authorService, $filter, Pagination){
	if($location.$$path === "/viewauthors"){
		authorService.getAllAuthorsService().then(function(backendAuthorsList){
			$scope.authors = backendAuthorsList;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
		});
	}else if($location.$$path === "/addauthor"){
		$http.get("http://localhost:8080/lms/initAuthor").success(function(backendAuthorsList){
			$scope.author = backendAuthorsList;
		});
	}
	
	$scope.saveAuthor = function(){
		$http.post("http://localhost:8080/lms/addAuthor", $scope.author).success(function(){
			$window.location.href = "#/viewauthors";
		});
	}
	
	$scope.sort = function(){
		$scope.authors = $filter('orderBy')($scope.authors, 'authorName');
	}
	
	$scope.showEditAuthorModal = function(authorId){
		authorService.getAuthorByPKService(authorId).then(function(data){
			$scope.author = data;
			$scope.editAuthorModal = true;
		});
	}
	
	$scope.closeModal = function(){
		$scope.editAuthorModal = false;
	}
	
	$scope.updateAuthor = function(){
		$http.post("http://localhost:8080/lms/updateAuthor", $scope.author).success(function(){
			$scope.editAuthorModal = false;
			authorService.getAllAuthorsService().then(function(backendAuthorsList){
				$scope.authors = backendAuthorsList;
				$scope.pagination = Pagination.getNew(10);
				$scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
			});
		});
	}
	
	$scope.searchAuthors = function(){
		$http.get("http://localhost:8080/lms/searchAuthors/"+$scope.searchString).success(function(data){
			$scope.authors = data;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
		});
	}
})