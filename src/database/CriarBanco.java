/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package database;

import java.sql.*;
/**
 *
 * @author Monica
 */
public class CriarBanco {

    private String driver;
    private String database;
    private String user;
    private String senha;
    private Connection conexao;

    public CriarBanco() {
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.database = "loo2022";
        this.user = "root";
        this.senha = "root";
    }

    public CriarBanco(String driver, String database, String user, String senha) {
        this.driver = driver;
        this.database = database;
        this.user = user;
        this.senha = senha;
    }

    public void criarBanco() throws Exception {
        Class.forName(driver);
        String url = "jdbc:mysql://localhost:3306/";
        try {
            conexao = DriverManager.getConnection(url, user, senha);
        } catch (SQLException ex) {
            throw new Exception("driver incorreto - " + driver);
        }
        String sql = "create database " + database;
        try {
            Statement sessao = conexao.createStatement();
            sessao.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception("Erro na criação do banco - " + sql);
        }
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
