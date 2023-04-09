package baronvice.mvcstuff.dao;

import baronvice.mvcstuff.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
public class PersonDAO {

    // To get DB connection there should be its url, username and password
    // TODO: set default values as null and change them from properties after is kinda lame
//    @Value("${database.url}")
//    private String url;
//    @Value("${database.username}")
//    private String username;
//    @Value("${database.password}")
//    private String password;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "abobus";

    private static Connection connection;

    static {
        // Not required in last postgre versions.
        try {
            // To make sure that driver exists
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index(){
        List<Person> people = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Person"
            );
            // Here is the execution (returns ResultSet), executeQuery to get data from DB
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Person person = new Person(
                        resultSet.getString("nickname"),
                        resultSet.getString("name"),
                        resultSet.getString("surname")
                );
                people.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public Person show(String nickname){
        // If something goes wrong inside try/catch - null will be returned
        Person person = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Person WHERE nickname=?"
            );

            preparedStatement.setString(1, nickname);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            person = new Person(
                    resultSet.getString("nickname"),
                    resultSet.getString("name"),
                    resultSet.getString("surname")
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    public void save(Person person) throws RuntimeException{
        try {
            // PreparedStatement - protection from SQL injections
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Person VALUES (?, ?, ?)"
            );

            preparedStatement.setString(1, person.getNickname());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getSurname());

            // executeUpdate to change data inside DB
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String previousNickname, Person updatedPerson){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Person SET nickname=?, name=?, surname=? WHERE nickname=?"
            );

            preparedStatement.setString(1, updatedPerson.getNickname());
            preparedStatement.setString(2, updatedPerson.getName());
            preparedStatement.setString(3, updatedPerson.getSurname());
            preparedStatement.setString(4, previousNickname);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String nickname){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Person WHERE nickname=?"
            );

            preparedStatement.setString(1, nickname);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
