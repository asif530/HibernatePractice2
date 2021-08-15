package org.controller;

import org.dto.Address;
import org.dto.Songs;
import org.dto.UserDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Continuation of hibenate1
 * Here in place of object we will use collection which will create another table but will still keep relation with userDetails
 * */

public class HibernateTest {
    public static void main(String[] args) {

        UserDetails userDetails = new UserDetails();
        Address office = new Address();
        Address home = new Address();
        Songs music1 = new Songs();
        Songs music2 = new Songs();
        Songs music3 = new Songs();
        Songs vids1 = new Songs();
        Songs vids2 = new Songs();

        Set<Address> officeAddr = new HashSet<Address>();
        List<Songs> songs = new ArrayList<Songs>();



        userDetails.setUserName("First User");

        office.setStreet("kafrul");
        office.setCity("Dhaka");

        home.setLocation("Sitakunda");
        home.setCity("Chittagong");

        music1.setsName("Leave her Johnny");
        music1.setSource("Assassins Creed");


        music2.setsName("Hoist the colours");
        music2.setSource("Pirates of the Carabian");


        music3.setsName("The Willow Maid");
        music3.setSource("Kate Covington");


        vids1.setSource("Exposing Work Space");
        vids1.setsName("Joshua Flock");


        vids2.setSource("Marvel News");
        vids2.setsName("Charlie");



        //Setting in user collection
        userDetails.getAddress().add(office);
        userDetails.getAddress().add(home);
        /*
        * Could be done only one object.In a for loop or a for each loop we could assign value...Then assign it to the colletion via userDetails.
        * */
        userDetails.getSongs().add(music1);
        userDetails.getSongs().add(music2);
        userDetails.getSongs().add(music3);
        userDetails.getSongs().add(vids1);
        userDetails.getSongs().add(vids2);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        /*
        * If it gets errors in later parts like in playlists or alien_address it will not rollback transaction.
        * It tries to insert all in a same save...So when error happens in the later(playlists/alien_address) it will not enter data in those
        * table but it already saved data in other tables...Just will not insert in the troubled tables.
        * So its better to wrap the save in try catch and outside of try catch we will commit by checking if there are any error not
        * */
        session.save(userDetails);
        session.getTransaction().commit();

        //Session returns an object..So we have to cast it to appropriate class
        //get(String,Serializable) means get(className to map, primaryKey)
        userDetails = (UserDetails)session.get(UserDetails.class,1);
        /*We will get NullPointerException because even though we set 3 in userId hibernate set userId as 1.
        * Why??? Because we used strategy and it let hibernate the power to generate a userId for the entry
        * */
        /*
        *
        * Eager and lazy fetch... Hibernate by default is Lazy... Meaning it will not pull all the data when it is initiated
        * To know why its that way we have to know about Proxy object
        * Hibernate pulls data with proxy object
        * When a call is made with an object a proxy object of the parent object is created.
        * Then when a get call  of the object's field is made it then fetches the value from db.
        * Although we might think  the object we instantiated fetches the value under the hood its the proxy object which fetches the value
        * We can test it like this: First make a get call with a user. Then close the session. And then try to get the objects field.
        * RESULT: We cant get anything...Because as soon as the session is closed the proxy who makes the call is also destroyed...SO THIS IS LAZY FETCH
        * But we can get the value if we made the fetch type Eager... This way after initialization the proxy gets all value associated from the db and loads it
        * So after you close the session the data still is available... But that's resource consuming if you have a lot of data...
        *
        * */



        for (Address a: userDetails.getAddress()) {
            System.out.println(a.getCity());
            System.out.println(a.getStreet());
            System.out.println(a.getLocation());
        }

        /*Table A,B. B had fk relation with A. But later C was created and B was just not used or mentioned in code.
        * But it was not deleted from database.
        * If that's the case when A,C will be run with ddl.AUTO=create it wont be able to delete table A*/
        session.close();
    }
}
