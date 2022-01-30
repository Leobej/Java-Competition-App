package database.dao;

import database.connection.DatabaseConnection;
import database.model.PersonEntity;
import database.model.StageEntity;
import database.model.TeamEntity;


import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class StageDao {
    private DatabaseConnection connection;

    public StageDao() {
        connection = new DatabaseConnection();
    }

    public StageEntity get(int id)
    {
        return connection.getEntityManager().find(StageEntity.class,id);
    }

    public List<StageEntity> getAll() {
        TypedQuery<StageEntity> query = connection.getEntityManager().createQuery("SELECT a FROM StageEntity a", StageEntity.class);
        return query.getResultList();
    }

    public void create(StageEntity stageEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(stageEntity));
    }

    public void update(StageEntity stage)
    {
        connection.executeTransaction(entityManager -> entityManager.merge(stage));
    }


    public void delete(long id)
    {
        connection.executeTransaction(entityManager -> entityManager.remove(entityManager.find(StageEntity.class,id)));
    }

    /*
          I used userEntity.setId(userDao.getMax()) because i couldn't find a way to make
          postgresql to autoincrement a primary key, basically it returns biggest id from person entity
          and I add 1 to setId in table
  */
    public int getMax()
    {
        Query count=connection.getEntityManager().createNativeQuery("Select MAX(id_stage) from stage")  ;
        System.out.println( count.getSingleResult());
        if (count.getSingleResult()!= null)
            return Integer.valueOf(count.getSingleResult().toString());
        else return 0;


    }
}
