package aorlov.ashdb.web.form;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
public class SearchForm {
	@NotEmpty
	@Size(min = 1, max = 50)
	private String personalCode;

	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}
	public String getPersonalCode() {
		return personalCode;
	}
}
