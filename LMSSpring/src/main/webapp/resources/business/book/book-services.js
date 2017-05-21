lmsApp.factory("bookService", function($http, bookConstants){
	return{
		getAllItemsService: function(){
			var getItemData = {};
			return $http({
				url: bookConstants.GET_ALL_BOOKS_URL
			}).success(function(data){
				getItemData = data;
			}).then(function(){
				return getItemData;
			})
		},
	
		getAuthorByPKService: function(authorId){
			var getAuthorByPkData = {};
			return $http({
				url: authorConstants.GET_AUTHOR_BY_PK_URL+authorId
			}).success(function(data){
				getAuthorByPkData = data;
			}).then(function(){
				return getAuthorByPkData;
			})
		}
	}
})