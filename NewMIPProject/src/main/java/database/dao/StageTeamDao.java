package database.dao;

import database.connection.DatabaseConnection;
import database.model.PersonEntity;
import database.model.StageEntity;
import database.model.StageteamEntity;
import database.model.TeamEntity;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class StageTeamDao {
    private DatabaseConnection connection;

    public StageTeamDao() {
        connection = new DatabaseConnection();
    }

    public StageteamEntity get(int id) {
        return connection.getEntityManager().find(StageteamEntity.class, id);
    }

    public List<StageteamEntity> getAll() {
        TypedQuery<StageteamEntity> query = connection.getEntityManager().createQuery("SELECT a FROM StageteamEntity a", StageteamEntity.class);
        return query.getResultList();
    }

    public void create(StageteamEntity stageteamEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(stageteamEntity));
    }


    public void update(StageteamEntity stageteamEntity)
    {
        connection.executeTransaction(entityManager -> entityManager.merge(stageteamEntity));
    }

    public void delete(long id)
    {
        connection.executeTransaction(entityManager -> entityManager.remove(entityManager.find(StageteamEntity.class,id)));
    }
    /*
          I used userEntity.setId(userDao.getMax()) because i couldn't find a way to make
      postgresql to autoincrement a primary key, basically it returns biggest id from person entity
   and I add 1 to setId in table
  */
    public int getMax() {
        Query count = connection.getEntityManager().createNativeQuery("Select MAX(id_stage_team) from stageteam");
        System.out.println(count.getSingleResult());
        if (count.getSingleResult()!= null)
            return Integer.valueOf(count.getSingleResult().toString());
        else return 0;


    }
}
