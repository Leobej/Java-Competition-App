
import database.dao.PersonDao;
import database.dao.TeamDao;
import database.model.PersonEntity;
import database.model.TeamEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args) {
 /*       PersonEntity userEntity = new PersonEntity();

        UserDao userDao = new UserDao();
        userEntity.setAdmin(false);
        userEntity.setUsername("Leo3");

        *//*
         * I used userEntity.setId(userDao.getMax()) because i couldn't find a way to make
         * postgresql to autoincrement a primary key, basically it returns biggest id from person entity
         * and I add 1 to setId in table
         * *//*
        userEntity.setId(userDao.getMax()+1);
        userDao.create(userEntity);
*/

     /*   StageEntity stageEntity= new StageEntity();
        StageDao stageDao=new StageDao();
        stageEntity.setIdStage(stageDao.getMax()+1);
        stageEntity.setScore(1);
        stageEntity.setStageNumber(1);
        stageEntity.setIdUser(1);
        stageDao.create(stageEntity);*/

/*
        TeamEntity teamEntity = new TeamEntity();
        TeamDao teamDao = new TeamDao();
        teamEntity.setTeamName("Echipa serpilor");
        teamEntity.setIdUser(2);
        teamEntity.setIdTeam(teamDao.getMax() + 1);
        teamDao.create(teamEntity);
*/
        ServerSocket server = null;

        try {
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            while (true) {
                Socket client = server.accept();
                System.out.println("Client nou conectat " + client.getInetAddress().getHostAddress());
                ClientHandler clientSocket = new ClientHandler(client);
                new Thread(clientSocket).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null)
                try {
                    server.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public Boolean userExists(PersonDao personDao, String username) {
            Boolean exists = false;
            List<PersonEntity> ceva = personDao.getAll();
            for (PersonEntity ceva1 : ceva)
                if (ceva1.getUsername().equals(username)) {
                    exists = true;
                    return exists;
                }
            return exists;
        }

        public Boolean userIsAdmin(PersonDao personDao, String username) {
            Boolean exists = false;
            List<PersonEntity> ceva = personDao.getAll();
            for (PersonEntity ceva1 : ceva)
                if (ceva1.getUsername().equals(username)) {
                    if (ceva1.isAdmin() == true) {
                        exists = true;
                        return exists;
                    }
                }
            return exists;
        }

        public void createPerson(PersonEntity personEntity, PersonDao personDao, String username, String firstname, String lastname, String isAdmin) {
            personEntity.setId(personDao.getMax() + 1);
            personEntity.setUsername(username);
            personEntity.setFirstname(firstname);
            personEntity.setLastname(lastname);
            personEntity.setAdmin(Boolean.parseBoolean(isAdmin));
            personDao.create(personEntity);


        }

        public void createPersonTeam(TeamEntity teamEntity, TeamDao teamDao, PersonEntity personEntity, String teamname) {
            teamEntity.setIdUser(personEntity.getId());
            teamEntity.setIdTeam(teamDao.getMax() + 1);
            teamEntity.setTeamName(teamname);
            teamDao.create(teamEntity);
        }

        public Boolean checkAction(String string) {

            PersonEntity personEntity = new PersonEntity();
            PersonDao personDao = new PersonDao();

            TeamEntity teamEntity = new TeamEntity();
            TeamDao teamDao = new TeamDao();
            Boolean exists = false;
            try {

                if (string.contains("LOGIN")) {
                    System.out.println("Incerc o logare");
                    String username = string.replace("LOGIN", "");
                    System.out.println(username);
                    exists = userExists(personDao, username);
                    System.out.println("\nCeva getbyusername " + exists);
                    return exists;
                }

                if (string.contains("REGISTER")) {
                    System.out.println("Incerc UN REGISTER");
                    String temp = string.replace("REGISTER", "");

                    String username = (temp + " ").split(" ")[0];
                    String firstname = (temp + " ").split(" ")[1];
                    String lastname = (temp + " ").split(" ")[2];
                    String isAdmin = (temp + " ").split(" ")[3];
                    String teamname = (temp + " ").split(" ")[4];

                    if (userExists(personDao, username) == false) {
                        createPerson(personEntity, personDao, username, firstname, lastname, isAdmin);
                        createPersonTeam(teamEntity, teamDao, personEntity, teamname);
                    } else exists = true;


                    System.out.println("Username " + username + " " + exists);
                    return exists;


                }
                if (string.contains("REGISTER")) {

                }
                if (string.contains("ADMIN")) {
                    String username = string.replace("ADMIN", "");
                    exists = userIsAdmin(personDao, username);
                }
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
            return exists;
        }


        @Override
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.printf("trimis de la clientul cu %s:\n", line);
                    Boolean bool = checkAction(line);

                    if (line.contains("LOGIN")) {
                        out.println("LOGIN" + checkAction(line));
                        break;
                    }

                    if (line.contains("REGISTER")) {
                        out.println("REGISTER" + bool);
                        break;
                    }

                    if (line.contains("STAGE")) {
                        out.println("STAGE" + bool);
                        break;
                    } else out.println("INCORRECT");
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}


