package baronvice.mvcstuff.dao;

import baronvice.mvcstuff.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public Person showByNickname(String nickname){
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

    public Optional<Person> showByEmail(String email){
        return jdbcTemplate.query(
                "SELECT * FROM Person WHERE email=?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)
        ).stream().findAny();
    }

    public void save(Person person) throws RuntimeException{
        jdbcTemplate.update(
                "INSERT INTO Person VALUES(?, ?, ?, ?)",
                person.getNickname(),
                person.getName(),
                person.getEmail(),
                person.getAddress()
        );
    }

    public void update(String previousNickname, Person updatedPerson){
        jdbcTemplate.update(
                "UPDATE Person SET nickname=?, name=?, email=?, address=? WHERE nickname=?",
                updatedPerson.getNickname(),
                updatedPerson.getName(),
                updatedPerson.getEmail(),
                updatedPerson.getAddress(),
                previousNickname
        );
    }

    public void delete(String nickname){
        jdbcTemplate.update(
                "DELETE FROM Person WHERE nickname=?",
                nickname
        );
    }

    ///////////////////////////////
    //// To test brunch update ////
    ///////////////////////////////

    public void separateUpdate(){
        List<Person> people = generateThousandPeople();

        long start = System.currentTimeMillis();
        for (Person person : people)
            jdbcTemplate.update(
                    "INSERT INTO PersonTest VALUES (?, ?, ?, ?)",
                    person.getNickname(),
                    person.getName(),
                    person.getEmail(),
                    person.getAddress()
            );
        long end = System.currentTimeMillis();

        System.out.println("Separate update time: " + (end - start));
    }

    public void batchUpdate(){
        List<Person> people = generateThousandPeople();
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(
                "INSERT INTO PersonTest VALUES (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, people.get(i).getNickname());
                        preparedStatement.setString(2, people.get(i).getName());
                        preparedStatement.setString(3, people.get(i).getEmail());
                        preparedStatement.setString(4, people.get(i).getAddress());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                }
        );
        long end = System.currentTimeMillis();

        System.out.println("Batch update time: " + (end - start));
    }

    @Deprecated
    private List<Person> generateThousandPeople(){
        List<Person> people = new ArrayList<>(200);
        for (int i = 0; i < 200; i++)
            people.add(i, new Person("User" + i, "Nick" + i, "Name" + i, "Address" + ""));

        return people;
    }
}
