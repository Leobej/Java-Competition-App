package database.dao;

import database.connection.DatabaseConnection;
import database.model.PersonEntity;
import database.model.TeamEntity;


import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonDao {

    private DatabaseConnection connection;

    public PersonDao() {
        connection = new DatabaseConnection();
    }


    public PersonEntity get(int id) {
        return connection.getEntityManager().find(PersonEntity.class, id);
    }

    public List<PersonEntity> getByUsername(String username){
        TypedQuery<PersonEntity> query = connection.getEntityManager().createQuery("SELECT a.username FROM PersonEntity a WHERE a.username='"+username+"'", PersonEntity.class);
        return query.getResultList();
    }

    public List<PersonEntity> getAll() {
        TypedQuery<PersonEntity> query = connection.getEntityManager().createQuery("SELECT a FROM PersonEntity a", PersonEntity.class);
        return query.getResultList();
    }

    public void create(PersonEntity person) {
        connection.executeTransaction(entityManager -> entityManager.persist(person));
    }


    public void update(PersonEntity person)
    {
        connection.executeTransaction(entityManager -> entityManager.merge(person));
    }

    public void delete(long id)
    {
        connection.executeTransaction(entityManager -> entityManager.remove(entityManager.find(PersonEntity.class,id)));
    }

        /*
             I used userEntity.setId(userDao.getMax()) because i couldn't find a way to make
             postgresql to autoincrement a primary key, basically it returns biggest id from person entity
          and I add 1 to setId in table
         */

    public int getMax() {
        Query count = connection.getEntityManager().createNativeQuery("Select MAX(id) from person");
        System.out.println(count.getSingleResult());

        if (count.getSingleResult()!= null)
            return Integer.valueOf(count.getSingleResult().toString());
        else return 0;
    }

}
