package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//metodo sem retorna apenas grava o usuario
	//passando como parametros o objeto responsavel que pega os dados do usuario
	//o parametro Long userLogado foi colocado na aula 51
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
		
		if (objeto.isNovo()) {//se for novo usuario - aula 37
			
			//inserir na tabela model logins os campos                      e os valores que vao ser passados
			String sql = "INSERT INTO model_login (login, senha, nome, email, usuario_id) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			
			//cada campo recebe um --> campo 1 recebe login --> campo 2 recebe senha
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setLong(5, userLogado);
			
			//executando a instrucao sql
			preparedSql.execute();
			connection.commit();
		}else {//atualizando
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			
			prepareSql.executeUpdate();
			
			connection.commit();
		}
			
			//consultando o usuario pelo login - reaproveitando o codigo abaixo
			return this.consultarUsuario(objeto.getLogin(), userLogado);
		
	}
	
	//aula 49
public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		//selecionando todos menos o admin (aula 50)
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		//varrendo as linhas da lista
		while (resultado.next()) {
			
			//para cada linha precisa iniciar um novo objeto para colocar na lista
			ModelLogin modelLogin = new ModelLogin();
			
			//preenchendo o objeto
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
			
		}
		
		return retorno;
	}
	
	
	//esse metodo fica responsavel pela lista que aparece no modal (aula 45)
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		//upper = como se fosse o ignoreCase, ignora se e maiuscula ou minuscula
		String sql = "select * from model_login where upper (nome) like upper(?) and useradmin is false and usuario_id = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%"+nome+"%" );
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		//varrendo as linhas da lista
		while (resultado.next()) {
			
			//para cada linha precisa iniciar um novo objeto para colocar na lista
			ModelLogin modelLogin = new ModelLogin();
			
			//preenchendo o objeto
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
			
		}
		
		return retorno;
	}
	
	
	//aula 51
public ModelLogin consultarUsuarioLogado(String login) throws Exception {
		
		//iniciando o objeto
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"')";
		
		//preparando o sql
		PreparedStatement statement = connection.prepareStatement(sql);
		//statement.setString(1, login); essa linha retirada na aula 35
		
		//executando o sql
		//executeQuery = selecao de dados
		//updateQuery = modificacao de dados
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
	
	
	
	//aula 51 - apenas consultando o login
public ModelLogin consultarUsuario(String login) throws Exception {
		
		//iniciando o objeto
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false";
		
		//preparando o sql
		PreparedStatement statement = connection.prepareStatement(sql);
		//statement.setString(1, login); essa linha retirada na aula 35
		
		//executando o sql
		//executeQuery = selecao de dados
		//updateQuery = modificacao de dados
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
	
	//mesmo metodo acima, mas consultando o login e tambem o userLogado
	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception {
		
		//iniciando o objeto
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id = " + userLogado;
		
		//preparando o sql
		PreparedStatement statement = connection.prepareStatement(sql);
		//statement.setString(1, login); essa linha retirada na aula 35
		
		//executando o sql
		//executeQuery = selecao de dados
		//updateQuery = modificacao de dados
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
	
	//AULA 47
	public ModelLogin consultarUsuarioID(String id, Long userLogado) throws Exception {
		
		//iniciando o objeto
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
		
		//preparando o sql
		PreparedStatement statement = connection.prepareStatement(sql);
		//statement.setString(1, login); essa linha retirada na aula 35
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		
		//executando o sql
		//executeQuery = selecao de dados
		//updateQuery = modificacao de dados
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
	
	//metodo que vai ver se o usuario logado ja existe
	public boolean validarLogin(String login) throws Exception {
		
		String sql = "select count (1) > 0 as existe from model_login where upper(login) = upper('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
				
		ResultSet resultado = statement.executeQuery();
		
		//tinha colocado um if, mas depois retirou
		//para entrar nos resultados do sql
		resultado.next();
			
		return resultado.getBoolean("existe");
		
	}
	
	//deletando -- aula 39
	public void deletarUser(String idUser) throws SQLException {
		
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();
		
		
		connection.commit();
		
	}

}
