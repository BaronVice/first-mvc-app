package baronvice.mvcstuff.util;

import baronvice.mvcstuff.dao.PersonDAO;
import baronvice.mvcstuff.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    // What classes are supported
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDAO.showByEmail(person.getEmail()).isPresent())
            errors.rejectValue("email", "", "This email is already taken");
    }
}
