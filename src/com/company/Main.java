package com.company;


import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Person person = new Person("Barrack","Obama",      12, 5136112,12345);
        Person person1 = new Person("Name1", "LastName6",  13, 5136113,12346);
        Person person2 = new Person("Name2", "LastName7",  14, 5136114,12347);
        Person person3 = new Person("Name3", "LastName8",  15, 5136115,12348);
        Person person4 = new Person("Name4", "LastName9",  16, 5136116,12349);
        Person person5 = new Person("Name5", "LastName10", 17, 5136117,12350);


        createTable();
        insertPerson(person);
        insertPerson(person1);
        insertPerson(person2);
        insertPerson(person3);
        insertPerson(person4);
        insertPerson(person5);

        System.out.println(selectPerson(4));
        for(Person p: findAllPeople()){
            System.out.println(p);
        }
        deletePerson(4);

        for(Person p: findAllPeople()){
            System.out.println(p);
        }

    }

    public static Connection deletePerson(int id){
        Connection connection = null;
        Statement stmt = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            stmt = connection.createStatement();
            String sql = "DELETE from MARK where ID=" + id + ";";
            stmt.executeUpdate(sql);
            connection.commit();
            connection.close();

            System.out.println("(Deleted Person "+ id + ") done successfully");
        } catch ( Exception e ) {
            System.out.println(e);
            connection = null;
        }
        return connection;


    }

    public static ArrayList<Person> findAllPeople(){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Person> person = new ArrayList<>();

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM MARK;");
            while ( rs.next() ) {
                person.add(new Person(rs.getString("firstname"), rs.getString("lastname"), rs.getInt("age"), rs.getInt("creditcard"),rs.getInt("ssn")));
            }
            rs.close();
            statement.close();
            connection.close();

            System.out.println("(Find All People) done successfully");
        } catch ( Exception e ) {
            System.out.println(e);
            person = null;
        }
        return person;

    }

    public static Person selectPerson(int id){
        Connection connection = null;
        Statement statement = null;
        Person person = new Person();
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM MARK where ID=" + id + ";");
            while ( rs.next() ) {
                person.setFirstName(rs.getString("firstname"));
                person.setLastName(rs.getString("lastname"));
                person.setAge(rs.getInt("age"));
                person.setCreditCard(rs.getInt("creditcard"));
                person.setSsn(rs.getInt("ssn"));
            }
            rs.close();
            statement.close();
            connection.close();

            System.out.println("(Select Person " + id + ") done successfully");
        } catch ( Exception e ) {
            System.out.println(e);
            person = null;
        }
            return person;

    }

    public static Connection insertPerson(Person person){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String sql = "INSERT INTO MARK (FIRSTNAME,LASTNAME,AGE,CREDITCARD,SSN) " +
                    "VALUES ("+
                          "'" + person.getFirstName() + "'," +
                          "'" + person.getLastName() + "'," +
                                person.getAge() + "," +
                                person.getCreditCard() + "," +
                                person.getSsn() + " );";
            statement.executeUpdate(sql);

            statement.close();
            connection.commit();
            connection.close();

            System.out.println("(Insert Person " + person.getFirstName() + " " + person.getLastName() + ") done successfully");
        } catch ( Exception e ) {
            System.out.println(e);
            connection = null;
        }

            return connection;
    }


    public static Connection createTable(){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();

            statement = connection.createStatement();
            String sql = "CREATE TABLE MARK " +
                         "(ID INTEGER PRIMARY KEY        AUTOINCREMENT, " +
                         "FIRSTNAME           CHAR(30)   NOT NULL, " +
                         "LASTNAME            CHAR(30)   NOT NULL, " +
                         "AGE                 INT                , " +
                         "CREDITCARD          BIGINT             , " +
                         "SSN                 BIGINT             ) ";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();

            System.out.println("Table created successfully");
        }catch (Exception e){
            System.out.println(e);
            connection = null;
        }

        return connection;
    }

    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MyDatabase.db");
            System.out.println("Opened database successfully");
        }catch (Exception e){
            System.out.println(e);
            connection = null;
        }

        return connection;
    }


}
