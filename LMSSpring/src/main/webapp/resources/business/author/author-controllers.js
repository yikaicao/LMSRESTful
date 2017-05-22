/**
 * list of all functions to manipulate authors
 * 
 * This javascript file serves as a connector between front-end and service
 * layer.
 */

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
	
	/**
	 * Helper function to handle specific cases
	 */
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
	
	$scope.showDeleteAuthorModal = function(authorId) {
		authorService.getAuthorByPKService(authorId).then(function(data){
			$scope.author = data;
			$scope.deleteAuthorModal = true;
		});
	}
	
	$scope.closeModal = function(){
		$scope.editAuthorModal = false;
		$scope.deleteAuthorModal = false;
	}
	
	$scope.updateAuthor = function(){
		$http.put("http://localhost:8080/lms/authors", $scope.author).success(function(){
			$scope.editAuthorModal = false;
			authorService.getAllAuthorsService().then(function(backendAuthorsList){
				$scope.authors = backendAuthorsList;
				$scope.pagination = Pagination.getNew(10);
				$scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
			});
		});
	}
	
	$scope.deleteAuthor = function(){
		
		$http.delete("http://localhost:8080/lms/authors/"+$scope.author.authorId).success(function(){
			$scope.deleteAuthorModal = false;
			authorService.getAllAuthorsService().then(function(backendAuthorsList){
				$scope.authors = backendAuthorsList;
				$scope.pagination = Pagination.getNew(10);
				$scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
			});
		});
	}
	
	$scope.searchAuthors = function(){
		$http.get("http://localhost:8080/lms/authors?searchString="+$scope.searchString).success(function(data){
			$scope.authors = data;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
		});
	}
	/**
	 * End of helper functions
	 */
})