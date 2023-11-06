
package chatv2;

/**
 *
 * @author gu_si
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Chatfinal {
    static ArrayList<String> listaUsuarios = new ArrayList<String>();
    static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();

    public static void main(String [] args) throws Exception {
        System.out.println("Aguardando usuários...");
        ServerSocket ss = new ServerSocket(5555);
        while (true) {
            Socket usuario = ss.accept();
            System.out.println("Usuário conectado!");
            
            ManipuladorConversa conversa = new ManipuladorConversa(usuario);
            conversa.start();
        }
    }
    }

class ManipuladorConversa extends Thread {
    Socket usuarioConversa;
    BufferedReader entrada;
    PrintWriter saida;
    String nome;
  
    
    public ManipuladorConversa(Socket usuarioConversa) throws IOException {
        this.usuarioConversa = usuarioConversa;
    }
    public void run (){
        try {
            entrada = new BufferedReader(new InputStreamReader(usuarioConversa.getInputStream()));
            saida = new PrintWriter(usuarioConversa.getOutputStream(), true);
            int contador = 0;
            while (true) {
                if (contador > 0) {
                    saida.println("NOME_EXISTENTE");
                } else {
                    saida.println("NOME_REQUERIDO");
                }
                nome = entrada.readLine();
                
                if (nome == null) {
                    return;
                }
                
                if (!Chatfinal.listaUsuarios.contains(nome)) {
                    Chatfinal.listaUsuarios.add(nome);
                    break;
                }
                contador++;
            }
            saida.println("Nome aceito" + nome);
            Chatfinal.printWriters.add(saida);
            
            while (true) {
                String msg = entrada.readLine();
                
                if (msg == null) {
                    return;
                }
                
                for (PrintWriter writer : Chatfinal.printWriters) {
                    writer.println(nome + ":" + msg);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erro no Servidor!!!" + e.getMessage());
            e.printStackTrace();
        }
    }
}