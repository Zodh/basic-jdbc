import io.github.zodh.dao.AuthorDAO;
import io.github.zodh.dao.base.DAOFactory;
import io.github.zodh.entity.Author;
import java.util.Scanner;

public class Application {

  public static void main(String[] args) throws Exception {

    var authorDAO = DAOFactory.getFactory().getAuthorDAO();
    authorDAO.openConn();

    var scanner = new Scanner(System.in);
    var opt = 10;

    while (true){
      exibirMenu();
      opt = scanner.nextInt();
      switch (opt){
        case 1:
          System.out.println("Insira o nome do author: ");
          var nome = scanner.next();
          System.out.println("Insira o apelido para " + nome);
          var apelido = scanner.next();
          criarAuthor(nome, apelido, authorDAO);
          System.out.println("Author criado com sucesso!");
          break;
        case 2:
          listarAuthors(authorDAO);
          break;
        default:
          authorDAO.closeConn();
          System.exit(0);
      }
    }
  }

  private static void criarAuthor(String nome, String apelido, AuthorDAO authorDAO) throws Exception {
    // Cria uma instancia de author.
    var author = Author
        .builder()
        .name(nome)
        .fantasyName(apelido)
        .build();

    // Executa o comando para criar um author.
    authorDAO.create(author);
  }

  private static void listarAuthors(AuthorDAO authorDAO) throws Exception {
    // Busca todos os authors e lista eles
    var authors = authorDAO.listAll();
    authors.forEach(System.out::println);
  }

  private static void exibirMenu(){
    System.out.println("Deseja fazer o que? \n 1 - Criar author \n 2 - Listar authors \n 0 - Sair");
  }

}
