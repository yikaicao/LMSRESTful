package com.gcit.lms;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;

/**
 * Handles requests for the application web pages
 * 
 * @author yikaicao
 */
@Controller
public class HomeController {

	@Autowired
	AuthorDAO adao;

	@Autowired
	BookDAO bdao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "welcome";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Locale locale, Model model) {
		return "admin";
	}

	@RequestMapping(value = "/author", method = RequestMethod.GET)
	public String author(Locale locale, Model model) {
		return "author";
	}

	@RequestMapping(value = "/addAuthor", method = RequestMethod.GET)
	public String addAuthor(Locale locale, Model model) {
		return "addauthor";
	}

	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET)
	public String viewAuthors(Locale locale, Model model) {
		return "viewauthors";
	}

	@RequestMapping(value = "/updateAuthor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
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
