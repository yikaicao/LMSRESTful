lmsApp.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		redirectTo : "/home"
	}).when("/home", {
		templateUrl : "welcome.html"
	}).when("/admin", {
		templateUrl : "admin.html"
	}).when("/author", {
		templateUrl : "html/admin-management/author.html"
	}).when("/viewauthors", {
		templateUrl : "html/admin-management/viewauthors.html"
	}).when("/addauthor", {
		templateUrl : "html/admin-management/addauthor.html"
	}).when("/book", {
		templateUrl : "html/book-management/book.html"
	}).when("/viewbooks", {
		templateUrl : "html/book-management/viewbooks.html"
	}).when("/addbook", {
		templateUrl : "html/book-management/addbook.html"

	})
} ])