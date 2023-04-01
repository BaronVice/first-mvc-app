package baronvice.mvcstuff.dao;

import baronvice.mvcstuff.models.Person;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class PersonDAO {
    private Map<String, Person> people;

    {
        people = new TreeMap<>(
                Map.ofEntries(
                        Map.entry("bv@duck.com",
                                new Person("bv@duck.com","Baron","Vice")),
                        Map.entry("toni.sas.228@mail.ru",
                                new Person("toni.sas.228@mail.ru", "Tony", "L")),
                        Map.entry("maestrovice@duck.com",
                                new Person("maestrovice@duck.com", "Maestro", "Vice"))
                )
        );
    }

    public Map<String, Person> index(){
        return people;
    }

    public Person show(String email){
        return people.get(email);
    }

    public void save(Person person) throws RuntimeException{
        if (people.containsKey(person.getEmail()))
            throw new RuntimeException("email is already used");

        // also add regex for mail validation
        people.put(person.getEmail(), person);
    }
}
