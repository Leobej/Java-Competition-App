
package database.dao;

import database.connection.DatabaseConnection;
import database.model.StageteamEntity;
import database.model.TeamEntity;


import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TeamDao {
    private DatabaseConnection connection;

    public TeamDao() {
        connection = new DatabaseConnection();
    }

    public TeamEntity get(int id)
    {
        return connection.getEntityManager().find(TeamEntity.class,id);
    }

    public List<TeamEntity> getAll() {
        TypedQuery<TeamEntity> query = connection.getEntityManager().createQuery("SELECT a FROM TeamEntity a", TeamEntity.class);
        return query.getResultList();
    }

    public void create(TeamEntity teamEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(teamEntity));
    }


    public void update(TeamEntity teamEntity)
    {
        connection.executeTransaction(entityManager -> entityManager.merge(teamEntity));
    }

    public void delete(long id)
    {
        connection.executeTransaction(entityManager -> entityManager.remove(entityManager.find(TeamEntity.class,id)));
    }
    /*
          I used userEntity.setId(userDao.getMax()) because i couldn't find a way to make
      postgresql to autoincrement a primary key, basically it returns biggest id from person entity
   and I add 1 to setId in table
  */
    public int getMax()
    {
        Query count=connection.getEntityManager().createNativeQuery("Select MAX(id_team) from team")  ;
        System.out.println( count.getSingleResult());
        if (count.getSingleResult()!= null)
            return Integer.valueOf(count.getSingleResult().toString());
        else return 0;


    }
}
