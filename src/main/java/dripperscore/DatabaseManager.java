package dripperscore;
import dripperscore.entities.User;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class DatabaseManager {
    String type;
    String url;
    String port;
    String DDBBName;
    String username;
    String password;

    String uuid;
    @Getter
    private final DripperS_Core core;
    Connection connection;

    public DatabaseManager(DripperS_Core core) {
        this.core = core;
    }


    public void createBBDDConnection(String type,String url,String port,String DDBBName,String username,String password){
        this.type = type;
        this.url = url;
        this.port = port;
        this.DDBBName = DDBBName;
        this.username = username;
        this.password = password;

        PreparedStatement statement = null;

        if(type.equalsIgnoreCase("MySql")){
            String conectionUrl = "jdbc:"+this.type+"://"+this.url+":"+ this.port+"/??useUnicode=true&characterEncoding=UTF-8";

            try{
                connection = DriverManager.getConnection(conectionUrl,this.username,this.password);
                statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS "+this.DDBBName);
                statement.executeUpdate();
                statement = connection.prepareStatement("USE "+ this.DDBBName);
                statement.executeUpdate();

                //TABLAS DESAROLLO Y PRUEBAS
                createTableTests();
                addLog(null,"SQL Connected");

                //TABLAS DEFINITIVAS
                createTableUsers();
                createTableCommandLogs();

                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.GREEN+"SQL Connected");
            }catch (SQLException e){
                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error connecting SQL");
                core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.YELLOW+"Enabling WithOut SQL Connection");
            }
        }
    }
    public void createTableUsers(){
        try (
             PreparedStatement stmt = connection.prepareStatement(
             "CREATE TABLE IF NOT EXISTS users (" +
                     "uuid VARCHAR(40) NOT NULL," +
                     "username VARCHAR(255) NOT NULL," +
                     "last_login DATETIME NOT NULL," +
                     "first_ip VARCHAR(45) NOT NULL," +
                     "last_ip VARCHAR(45) NOT NULL," +
                     "freezed boolean DEFAULT 0 NOT NULL," +
                     "PRIMARY KEY (uuid)" +
                     ")"
             )
        ) {
            stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error al crear tabla 'users': " + ex.getMessage());
            addLog("ERROR","Error al crear tabla 'users': " + ex.getMessage());
        }
    }
    public void createTableCommandLogs(){
        try (
                PreparedStatement stmt = connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS commandLogs ("+
                                "id INT PRIMARY KEY AUTO_INCREMENT, "+
                                "type VARCHAR(200) DEFAULT 'INFO' NOT NULL, "+
                                "user VARCHAR(200) DEFAULT 'CONSOLE' NOT NULL, "+
                                "command VARCHAR(500), "+
                                " timestamp TIMESTAMP )"
                )
        ) {
            stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error al crear tabla 'commandLogs': " + ex.getMessage());
        }
    }
    public void createTableTests(){
        try (
              PreparedStatement stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS logs ("+
                        "id INT PRIMARY KEY AUTO_INCREMENT, "+
                        "type VARCHAR(200) DEFAULT 'INFO' NOT NULL, "+
                        "data VARCHAR(500), "+
                        " timestamp TIMESTAMP )"
              )
        ) {
            stmt.execute();
            //System.out.println("Tabla 'logs' creada exitosamente.");
        } catch (SQLException ex) {
            System.err.println("Error al crear tabla 'logs': " + ex.getMessage());
        }
    }
    public User GetUserByUUID(String uuid){

        String uuidWithDashes = uuid;

        // Eliminar los guiones ("-") de la UUID
        String uuidWithoutDashes = uuidWithDashes.replace("-", "");
        PreparedStatement statement = null;
        ResultSet result = null;
        User user = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE uuid = ?");
            statement.setString(1, uuidWithoutDashes);
            result = statement.executeQuery();

            if (result.next()) {
                String id = result.getString("uuid");
                String username = result.getString("username");
                boolean freezed = result.getBoolean("freezed");

                // Otros campos...

                // Obtener la columna de LocalDateTime de la base de datos
                /*Timestamp registrationTimestamp = result.getTimestamp("registration_date");
                LocalDateTime registrationDate = null;
                if (registrationTimestamp != null) {
                    registrationDate = registrationTimestamp.toLocalDateTime();
                }*/
                user = new User(id, username,freezed);
            }
        } catch (SQLException e) {
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "DripperS-Core: " + ChatColor.RED + "Error searching user");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + e.toString());
        } finally {
            // Cerrar el statement y el resultset para liberar recursos
            //TODO Mejoras proximas cerrar conexiones y iniciarlas cada vez que se haga algo con la bbdd
        }
        return user;
    }
    public void addLog(String type ,String log){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO logs (type, data, timestamp) VALUES (?,?, ?)");
            if(type != null){
                preparedStatement.setString(1, type);
            }else{
                preparedStatement.setString(1, "INFO");
            }
            preparedStatement.setString(2, log);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error adding log");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
        }
    }
    public void addCommandLog(String type,String username ,String command){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO commandLogs (type, user, command, timestamp) VALUES (?,?, ?, ?)");
            if(type != null){
                preparedStatement.setString(1, type);
            }else{
                preparedStatement.setString(1, "INFO");
            }
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, command);
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error adding commandLog");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
        }
    }
    public void SaveUser(User user){
        PreparedStatement statement = null;

        try {
            User userFinded = GetUserByUUID(user.getUuid());
            if(userFinded != null){
                //PreparedStatement stmt = connection.prepareStatement("UPDATE INTO users (username, last_login, last_IP) VALUES (?, ?, ?)");
                PreparedStatement stmt = connection.prepareStatement("UPDATE users SET  last_login = ?, last_IP = ? WHERE uuid = ?");
                //stmt.setString(1, user.getUUID());
                //stmt.setString(1, user.getUsername());
                stmt.setTimestamp(1, Timestamp.from(Instant.now()));
                stmt.setString(2, user.getLastIp());
                stmt.setString(3, user.getUuid());
                //stmt.setString(5, user.getFirstIP());
                stmt.executeUpdate();
            }else{
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (uuid, username, last_login, last_IP, first_IP) VALUES (?, ?, ?, ?, ?)");
                String uuidWithDashes = String.valueOf(user.getUuid());

                // Eliminar los guiones ("-") de la UUID
                String uuidWithoutDashes = uuidWithDashes.replace("-", "");

                stmt.setString(1, uuidWithoutDashes);
                stmt.setString(2, user.getUsername());
                stmt.setTimestamp(3, Timestamp.from(user.getLastLogin().toInstant(ZoneOffset.UTC)));
                stmt.setString(4, user.getLastIp());
                stmt.setString(5, user.getFirstIp());
                stmt.executeUpdate();
            }
        }catch (SQLException e){
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error saving user");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
            addLog("ERROR", "Error saving user in users table: "+ e.toString());
        }
    }
    public void Freeze(String freezedPlayerUUID, boolean freezed) {
        PreparedStatement statement = null;

        try {
            User userFinded = GetUserByUUID(freezedPlayerUUID);
            if(userFinded != null){
                //PreparedStatement stmt = connection.prepareStatement("UPDATE INTO users (username, last_login, last_IP) VALUES (?, ?, ?)");
                PreparedStatement stmt = connection.prepareStatement("UPDATE users SET  freezed = ? WHERE uuid = ?");
                //stmt.setString(1, user.getUUID());
                //stmt.setString(1, user.getUsername());
                if(freezed){
                    stmt.setBoolean(1, true);
                    //core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"freezed");
                }else{
                    stmt.setBoolean(1, false);
                    //core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"UnFreezed");
                }
                //stmt.setString(2, user.getLastIp());
                stmt.setString(2, freezedPlayerUUID);
                //stmt.setString(5, user.getFirstIP());
                stmt.executeUpdate();
            }
        }catch (SQLException e){
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error saving user");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
            addLog("ERROR", "Error saving user in users table: "+ e.toString());
        }
    }
    public void closeConnection(){
        try{
            addLog(null,"SQL Disconnected");
            connection.close();
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.YELLOW+"SQL Disconnected");

        }catch (SQLException e){
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error Disconnecting SQL");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
            addLog("ERROR", "Error Disconnecting SQL: "+ e.toString());
        }
    }


}

