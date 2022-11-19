import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static Socket socket;

    public Client() {
     try {
        socket = new Socket("localhost", 9600);
     } catch (Exception ex) {
        ex.printStackTrace();
     }
    }

    public static void main(String args[]) {
        Resposta resposta = new Resposta();
        boolean continuar = true;
        Scanner in = new Scanner(System.in);

        System.out.println("-- Calculadora distribuida --");
        do{
            System.out.println("Digite o primeiro numero: ");
            float num1 = in.nextFloat();
            System.out.println("Digite o segundo numero: ");
            float num2 = in.nextFloat();
            System.out.println("Digite a operacao que deseja realizar [(+)Soma (-)Subtracao (*)Multiplicacao (/Divisao)] ");
            char op = in.next().charAt(0);

            Requisicao requisicao = new Requisicao (op, num1, num2);
            new Client();
            Conexao.send(socket, requisicao);
            resposta = (Resposta)Conexao.receive(socket);

            switch(resposta.getStatus()){
                case 0:
                    System.out.println("O restulado da conta e: " + resposta.getResultado());
                    break;
                case 1:
                    System.out.println("Operacao nao implementada.");
                    break;
                case 2:
                    System.out.println("Operacao invalida.");
                    break;
            }
            try {
                socket.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        } while(continuar);

        

        System.out.println("O resultado e: "+resposta.getResultado());

        in.close();
    }
}