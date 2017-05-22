lmsApp.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		redirectTo : "/home"
	}).when("/home", {
		templateUrl : "welcome.html"
	}).when("/admin", {
		templateUrl : "admin.html"
	}).when("/author", {
		templateUrl : "html/author-management/author.html"
	}).when("/viewauthors", {
		templateUrl : "html/author-management/viewauthors.html"
	}).when("/addauthor", {
		templateUrl : "html/author-management/addauthor.html"
	}).when("/book", {
		templateUrl : "html/book-management/book.html"
	}).when("/viewbooks", {
		templateUrl : "html/book-management/viewbooks.html"
	}).when("/addbook", {
		templateUrl : "html/book-management/addbook.html"
	}).when("/librarian", {
		templateUrl : "librarian.html"
	}).when("/viewbranches", {
		templateUrl : "html/librarian/viewbranches.html"
	}).when("/addbranch", {
		templateUrl : "html/librarian/addbranch.html"
	}).when("/borrower", {
		templateUrl : "borrower.html"
	}).when("/viewbranches-borrower", {
		templateUrl : "html/borrower/viewbranches.html"
	})
} ])