package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://localhost:5433/curso_jsp4?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	//metodo para retornar a conexao existente
	public static Connection getConnection() {
		return connection;
	}
	
	
	//2 formas: chamando a classe diretamente ou instanciando o objeto
	//sao as 2 formas para garantir uma conexao
	
	//se chamar a essa classe de forma direta
	static {
		conectar();
	}
	
	//quando tiver uma instancia vai conectar
	public SingleConnectionBanco() {
		conectar();//chamando o metodo conectar
	}
	
	private static void conectar() {
		try {
			
			//conexao so se abre uma vez
			
			if (connection == null) {
				//carregando o drive de conexao do banco
				Class.forName("org.postgresql.Driver");
				
				//atribuindo a conexao a variavel de connection
				//pegando a classe Driver Manager com o metodo getconnection
				connection = DriverManager.getConnection(banco, user, senha);
				
				//para nao efetuar alteracoes sem o nosso comando
				connection.setAutoCommit(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();//mostrar qualquer erro ao conectar
		}
	}

}
