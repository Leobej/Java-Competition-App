package database.model;

import javax.persistence.*;

@Entity
@Table(name = "stage", schema = "public", catalog = "MIP")
public class StageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_stage")
    private long idStage;
    @Basic
    @Column(name = "score")
    private int score;
    @Basic
    @Column(name = "stage_number")
    private int stageNumber;
    @Basic
    @Column(name = "id_user")
    private int idUser;

    public long getIdStage() {
        return idStage;
    }

    public void setIdStage(long idStage) {
        this.idStage = idStage;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StageEntity that = (StageEntity) o;

        if (idStage != that.idStage) return false;
        if (score != that.score) return false;
        if (stageNumber != that.stageNumber) return false;
        if (idUser != that.idUser) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idStage ^ (idStage >>> 32));
        result = 31 * result + score;
        result = 31 * result + stageNumber;
        result = 31 * result + idUser;
        return result;
    }
}
