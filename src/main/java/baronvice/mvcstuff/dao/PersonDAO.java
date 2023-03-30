package baronvice.mvcstuff.dao;

import baronvice.mvcstuff.models.Person;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class PersonDAO {
    private static int PEOPLE_ID;
    private Map<Integer, Person> people;

    {
        people = new TreeMap<>(
                Map.ofEntries(
                        Map.entry(++PEOPLE_ID, new Person(PEOPLE_ID, "BaronVice")),
                        Map.entry(++PEOPLE_ID, new Person(PEOPLE_ID, "ToniSas")),
                        Map.entry(++PEOPLE_ID, new Person(PEOPLE_ID, "Maestro1337"))
                )
        );
    }

    public Map<Integer, Person> index(){
        return people;
    }

    public Person show(int id){
        return people.get(id);
    }
}
