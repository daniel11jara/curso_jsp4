package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//metodo sem retorna apenas grava o usuario
	//passando como parametros o objeto responsavel que pega os dados do usuario
	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {
			
			//inserir na tabela model logins os campos                      e os valores que vao ser passados
			String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?);";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			
			//cada campo recebe um --> campo 1 recebe login --> campo 2 recebe senha
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			
			//executando a instrucao sql
			preparedSql.execute();
			connection.commit();
			
			//consultando o usuario pelo login - reaproveitando o codigo abaixo
			return this.consultarUsuario(objeto.getLogin());
		
	}
	
	public ModelLogin consultarUsuario(String login) throws Exception {
		
		//iniciando o objeto
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('?')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		
		//executando o sql
		ResultSet resultado = statement.executeQuery();
		
		//se tem resultado
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			
		}
		
		return modelLogin;
				
	}

}
