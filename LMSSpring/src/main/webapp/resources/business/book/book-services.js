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
	
		getBookByPKService: function(bookId){
			var getBookByPkData = {};
			return $http({
				url: bookConstants.GET_BOOK_BY_PK_URL+bookId
			}).success(function(data){
				getBookByPkData = data;
			}).then(function(){
				return getBookByPkData;
			})
		}
	}
})