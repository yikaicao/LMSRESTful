lmsApp.factory("borrowerService", function($http, borrowerConstants) {
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
		}
	}
})