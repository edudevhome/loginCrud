package com.devhome.usuariocrud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.devhome.usuariocrud.model.Usuario;

/*
 * Esta classe DAO fornece operações de banco de dados CRUD para a
 *  tabela usuários no banco de dados..
 * */

public class UsuarioDao {
	private String jdbcUrl = "jdbc:mysql://localhost:3306/usuario_crud?useTimezone=true&serverTimezone=America/Sao_Paulo";
	private String jdbcUsername = "root";
	private String jdbcPassword = "98490127";

	private static final String Insert_Usuario_Sql = "INSERT INTO usuario" + " (nome, email, senha) VALUES"
			+ " (?, ?, ?)";

	private static final String Select_Usuario_By_Id = "select id, nome, email, senha from usuario where id=?";
	private static final String Select_Todos_Usuarios = "SELECT * from usuario";
	private static final String Delete_Usuario_Sql = "delete from usuario where id=?;";
	private static final String Update_Usuario_Sql = "update usuario set nome=?,  email=?,  senha=? where id=?;";

	public UsuarioDao() {

	}

	protected Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public void inserirUsuario(Usuario usuario) throws SQLException {
		System.out.println(Insert_Usuario_Sql);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(Insert_Usuario_Sql)) {
			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setString(2, usuario.getEmail());
			preparedStatement.setInt(3, usuario.getSenha());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Usuario selectUsuario(Integer id) {
		Usuario usuario = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(Select_Usuario_By_Id);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				Integer senha = rs.getInt("senha");
				usuario = new Usuario(id, nome, email, senha);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return usuario;
	}

	public List<Usuario> selectTodosUsuarios() {

		// using try-with-resources to avoid closing resources (boiler plate code).
		List<Usuario> usuario = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(Select_Todos_Usuarios);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				Integer senha = rs.getInt("senha");
				usuario.add(new Usuario(id, nome, email, senha));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return usuario;
	}

	public boolean deleteUsuario(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(Delete_Usuario_Sql);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateUsuario(Usuario usuario) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(Update_Usuario_Sql);) {
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getEmail());
			statement.setInt(3, usuario.getSenha());
			statement.setInt(4, usuario.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
