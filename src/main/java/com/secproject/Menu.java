package com.secproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<Credential> credenciais = new ArrayList<>();
    private Scanner entrada = new Scanner(System.in);
    private VaultRepository repository = new VaultRepository("cofre.vault");
    private String senhaMestra;

    public Menu(){
        System.out.println("Favor, insira a senha mestra: ");
        this.senhaMestra = entrada.nextLine();
        try {
            this.credenciais = repository.load(senhaMestra);
        } catch (Exception e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
            this.credenciais = new ArrayList<>();
        }
    }

    public void loop() throws Exception {

        boolean condicao = true;
        do {
            System.out.println("Selecione uma opcao baixo:\n\n1. Adiconar\n2. Listar\n3. Buscar\n4. Sair");
            switch (Integer.parseInt(entrada.nextLine().trim())) {
                case 1:
                    //metodo adicionar
                    adicionar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    condicao = false;
                    break;
            }
        }while(condicao);
    }

    private void adicionar() throws Exception {
        System.out.println("Favor, adicionar todas informacoes(Servico, usuario e senha)\n");
        Credential entradaUsuario = new Credential(entrada.nextLine(), entrada.nextLine(), entrada.nextLine());
        credenciais.add(entradaUsuario);
        repository.save(credenciais, senhaMestra);
        entrada.nextLine();
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

