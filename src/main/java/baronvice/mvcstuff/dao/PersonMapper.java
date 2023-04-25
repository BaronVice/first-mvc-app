package baronvice.mvcstuff.dao;

import baronvice.mvcstuff.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Person(
                resultSet.getString("nickname"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("address")
        );
    }
}
