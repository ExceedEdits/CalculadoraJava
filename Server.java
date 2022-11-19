import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Socket client_socket;
    static ServerSocket server_socket;

    public Server() {
        try {
            server_socket = new ServerSocket(9600);
            System.out.println("Servidor no ar...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Requisicao requisicao = new Requisicao();
        Resposta resposta = new Resposta();
        System.out.println("Servidor no ar...");
        new Server();
        if (connect()) {
            requisicao = (Requisicao)Conexao.receive(client_socket);
            switch (requisicao.getOperacao()) {
                case '+':
                    resposta.setResultado(requisicao.getNum1() + requisicao.getNum2());
                    resposta.setStatus(0);
                    System.out.println("Vai fazer "+requisicao.getNum1()+" + "+requisicao.getNum2());
                    System.out.println("E o resultado e: "+resposta.getResultado());
                    break;
                case '-':
                    resposta.setResultado(requisicao.getNum1() - requisicao.getNum2());
                    resposta.setStatus(0);
                    System.out.println("Vai fazer "+requisicao.getNum1()+" - "+requisicao.getNum2());
                    System.out.println("E o resultado e: "+resposta.getResultado());
                    break;
                case '*':
                    resposta.setResultado(requisicao.getNum1() * requisicao.getNum2());
                    resposta.setStatus(0);
                    System.out.println("Vai fazer "+requisicao.getNum1()+" * "+requisicao.getNum2());
                    System.out.println("E o resultado e: "+resposta.getResultado());
                    break;
                case '/':
                    if(requisicao.getNum2()!=0){
                        resposta.setResultado(requisicao.getNum1() / requisicao.getNum2());
                        resposta.setStatus(0);
                        System.out.println("Vai fazer "+requisicao.getNum1()+" / "+requisicao.getNum2());
                        System.out.println("E o resultado e: "+resposta.getResultado());
                        break;
                    } else {
                        resposta.setStatus(2);
                        System.out.println("Operacao invalida!");
                    }
                default:
                    resposta.setStatus(1);
                    System.out.println("Operador invalido!");
            }
            Conexao.send(client_socket, resposta);

            try {
                server_socket.close();
                client_socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean connect(){
        try {
            client_socket = server_socket.accept();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}