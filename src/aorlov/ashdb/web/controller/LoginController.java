package aorlov.ashdb.web.controller;

import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.persist.PersonHelper;
import aorlov.ashdb.persist.PersonHelperImpl;
import aorlov.ashdb.web.form.SearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.BindingResult;

import java.sql.SQLException;
import java.util.Map;
import javax.validation.Valid;

@Controller
@RequestMapping("loginform.html")
public class LoginController {
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map model) {
		SearchForm searchForm = new SearchForm();
		model.put("searchForm", searchForm);
		return "loginform";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processForm(@Valid SearchForm searchForm, BindingResult result,
			Map model) {
		String personalCode = "2764";
//		String password = "password";
		if (result.hasErrors()) {
			return "loginform";
		}
		searchForm = (SearchForm) model.get("searchForm");
        personalCode = searchForm.getPersonalCode();

        PersonHelper personHelper = new PersonHelperImpl();
        int personalCodeInt = Integer.valueOf(personalCode);
        Dancer dancer = null;
        try{
            dancer = personHelper.getPersonById(personalCodeInt);
        } catch (SQLException ex){
            //todo
        }
//		if (!searchForm.getPersonalCode().equals(personalCode)) {
//			return "loginform";
//		}
		model.put("Dancer", dancer);
		return "table";
	}

}
