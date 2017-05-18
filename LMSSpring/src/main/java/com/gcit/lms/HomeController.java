package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;

	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public String home(Locale locale, Model model) {
	// return "welcome";
	// }
	//
	// @RequestMapping(value = "/admin", method = RequestMethod.GET)
	// public String admin(Locale locale, Model model) {
	// return "admin";
	// }
	//
	// @RequestMapping(value = "/author", method = RequestMethod.GET)
	// public String author(Locale locale, Model model) {
	// return "author";
	// }
	//
	// @RequestMapping(value = "/addAuthor", method = RequestMethod.GET)
	// public String addAuthor(Locale locale, Model model) {
	// return "addauthor";
	// }
	//
	// @RequestMapping(value = "/viewAuthors", method = RequestMethod.GET)
	// public String viewAuthors(Locale locale, Model model) {
	// List<Author> authors = adminService.getAllAuthors(1);
	// model.addAttribute("authors", authors);
	// return "viewauthors";
	// }
	
	@RequestMapping(value = "/viewAuthors/{pageNo}", method = RequestMethod.GET, produces="application/json")
	public List<Author> viewAuthors(@PathVariable Integer pageNo) {
		List<Author> authors = new ArrayList<>();
		try {
				authors = adao.readAllAuthors(pageNo);
			for(Author a: authors){
				a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/updateAuthor", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	 public List<Author> updateAuthor(@RequestBody Author author) {
		try {
			adao.updateAuthor(author);
			return adao.readAllAuthors(1);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	 }
}
