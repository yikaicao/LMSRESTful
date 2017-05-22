lmsApp.factory("borrowerService", function($http, borrowerConstants) {
	return {
		getAllBranchesService : function() {
			var getItemData = {};
			return $http({
				url : borrowerConstants.GET_ALL_BRANCHES_URL
			}).success(function(data) {
				getItemData = data;
			}).then(function() {
				return getItemData;
			})
		}
	}
})