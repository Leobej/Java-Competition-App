package database.model;

import javax.persistence.*;

@Entity
@Table(name = "team", schema = "public", catalog = "MIP")
public class TeamEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_team")
    private long idTeam;
    @Basic
    @Column(name = "id_user")
    private long idUser;
    @Basic
    @Column(name = "team_name")
    private String teamName;

    public long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(long idTeam) {
        this.idTeam = idTeam;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamEntity that = (TeamEntity) o;

        if (idTeam != that.idTeam) return false;
        if (idUser != that.idUser) return false;
        if (teamName != null ? !teamName.equals(that.teamName) : that.teamName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTeam ^ (idTeam >>> 32));
        result = 31 * result + (int) (idUser ^ (idUser >>> 32));
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        return result;
    }
}
