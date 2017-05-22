lmsApp.factory("librarianService", function($http, librarianConstants) {
	return {
		getAllItemsService : function() {
			var getItemData = {};
			return $http({
				url : librarianConstants.GET_ALL_BRANCHES_URL
			}).success(function(data) {
				getItemData = data;
			}).then(function() {
				return getItemData;
			})
		},

		getBranchByPKService : function(branchId) {
			var getItemData = {};
			return $http({
				url : librarianConstants.GET_BRANCH_BY_PK_URL + branchId
			}).success(function(data) {
				getItemData = data;
			}).then(function() {
				return getItemData;
			})
		},

		getBookCopiesAtBranchService : function(branchId) {
			var getItemData = {};
			return $http(
					{
						url : librarianConstants.GET_BOOKCOPIES_AT_BRANCH_URL
								+ branchId
					}).success(function(data) {
				getItemData = data;
			}).then(function() {
				return getItemData;
			})
		},
		
		getBooksNotAtBranchService: function(branchId) {
			var getItemData = {};
			return $http({
				url: librarianConstants.GET_BOOK_NOT_AT_BRANCH_URL + branchId
			}).success(function(data) {
				getItemData = data;
			}).then(function(){
				return getItemData;
			})
		}
	}
})