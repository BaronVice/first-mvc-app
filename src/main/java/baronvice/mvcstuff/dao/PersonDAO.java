package baronvice.mvcstuff.dao;

import baronvice.mvcstuff.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonDAO {

    // All required stuff is configured so no need to create connection
    private final JdbcTemplate jdbcTemplate;

    public List<Person> index(){
        // Using JdbcTemplate:
        // First argument is request, second is RowMapper (presents records from table)
        // In other words what is my request and how I want to handle each result
//        return jdbcTemplate.query(
//                "SELECT * FROM Person",new PersonMapper()
//        );

        // But instead of PersonMapper we can use BeanPropertyRowMapper as every field of Person has the same
        // name as in the table.
        return jdbcTemplate.query(
                "SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class)
        );
    }

    public Person show(String nickname){
        // JdbcTemplate apply PreparedStatement by default
        // Second argument - array of values that will be placed inside '?'
        // jdbcTemplate.query returns List
        // TODO: orElse(new SomeSortOfError("Some sort of text"))
        return jdbcTemplate.query(
                "SELECT * FROM Person WHERE nickname=?",
                new Object[]{nickname},
                new BeanPropertyRowMapper<>(Person.class)
        ).stream().findAny().orElse(null);
    }

    public void save(Person person) throws RuntimeException{
        jdbcTemplate.update(
                "INSERT INTO Person VALUES(?, ?, ?)",
                person.getNickname(),
                person.getName(),
                person.getSurname()
        );
    }

    public void update(String previousNickname, Person updatedPerson){
        jdbcTemplate.update(
                "UPDATE Person SET nickname=?, name=?, surname=? WHERE nickname=?",
                updatedPerson.getNickname(),
                updatedPerson.getName(),
                updatedPerson.getSurname(),
                previousNickname
        );
    }

    public void delete(String nickname){
        jdbcTemplate.update(
                "DELETE FROM Person WHERE nickname=?",
                nickname
        );
    }
}
