package com.secproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<Credential> credenciais = new ArrayList<>();
    private Scanner entrada = new Scanner(System.in);

    public void loop() {

        boolean condicao = true;
        do {
            System.out.println("Selecione uma opcao baixo:\n\n1. Adiconar\n2. Listar\n3. Buscar\n4. Sair");
            switch (entrada.nextInt()) {
                case 1:
                    //metodo adicionar
                    adicionar();
                    break;
                case 2:
                    //
                    break;
                case 3:
                    //
                    break;
                case 4:
                    condicao = false;
                    break;
            }
        }while(condicao);
    }

    private void adicionar() {
        System.out.println("Favor, adicionar todas informacoes(Servico, usuario e senha)\n");
        Credential entradaUsuario = new Credential(entrada.nextLine(), entrada.nextLine(), entrada.nextLine());
        credenciais.add(entradaUsuario);
    }

    private void listar() {
        for (Credential credential : credenciais) {
            System.out.println(credential);
        }
    }

    private void buscar() {
        System.out.println("Insira o nome do serviço desejado:");
        String servicoDesejado = entrada.nextLine();
        for (Credential credenciai : credenciais) {
            String serv = credenciai.getService();
            if (serv.toLowerCase().contains(servicoDesejado.toLowerCase())){
                System.out.println(credenciai);
            }
        }
    }

}

