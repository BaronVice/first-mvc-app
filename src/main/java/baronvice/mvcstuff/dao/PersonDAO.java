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
                        Map.entry("BaronVice",
                                new Person("BaronVice","Baron","Vice")),
                        Map.entry("LV",
                                new Person("LV", "Tony", "L")),
                        Map.entry("Maestro1337",
                                new Person("Maestro1337", "Maestro", "Vice"))
                )
        );
    }

    public Map<String, Person> index(){
        return people;
    }

    public Person show(String nickname){
        return people.get(nickname);
    }

    public void save(Person person) throws RuntimeException{
        if (people.containsKey(person.getNickname()))
            throw new RuntimeException("nickname is already used");

        // also add regex for mail validation
        people.put(person.getNickname(), person);
    }

    public void update(String nickname, Person person){
        // I'm not sure, but probably getting person with show and updating it more correct than this
        // If id existed, then shouldn't be worried with remove
        delete(nickname);
        people.put(person.getNickname(), person);
    }

    public void delete(String nickname){
        people.remove(nickname);
    }
}
