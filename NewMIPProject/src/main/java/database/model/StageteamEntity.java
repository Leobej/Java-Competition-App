package database.model;

import javax.persistence.*;

@Entity
@Table(name = "stageteam", schema = "public", catalog = "MIP")
public class StageteamEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_stage_team")
    private long idStageTeam;
    @Basic
    @Column(name = "id_team")
    private int idTeam;
    @Basic
    @Column(name = "id_stage")
    private int idStage;

    public long getIdStageTeam() {
        return idStageTeam;
    }

    public void setIdStageTeam(long idStageTeam) {
        this.idStageTeam = idStageTeam;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getIdStage() {
        return idStage;
    }

    public void setIdStage(int idStage) {
        this.idStage = idStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StageteamEntity that = (StageteamEntity) o;

        if (idStageTeam != that.idStageTeam) return false;
        if (idTeam != that.idTeam) return false;
        if (idStage != that.idStage) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idStageTeam ^ (idStageTeam >>> 32));
        result = 31 * result + idTeam;
        result = 31 * result + idStage;
        return result;
    }
}
