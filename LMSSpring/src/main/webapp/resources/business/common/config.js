lmsApp.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/", {
		redirectTo: "/home"
	}).when("/home",{
		templateUrl: "welcome.html"
	}).when("/admin",{
		templateUrl: "admin.html"
	}).when("/author",{
		templateUrl: "admin-management/author.html"
	}).when("/viewauthors",{
		templateUrl: "admin-management/viewauthors.html"
	}).when("/addauthor",{
		templateUrl: "admin-management/addauthor.html"
	})
}])