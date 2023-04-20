# desafio-capgov

Este repositório armazena o desafio do processo seletivo CAPGov. Consiste em um programa utilizando-se de Java, Hibernate e PrimeFaces para realizar agendamentos.

## 🌎 Modelagem 
Esse projeto modela um sistema de reserva de viagens, apelidado de honey. O usuário pode agendar sua viagem de carro, passando como parâmetros seu nome, CPF, data e hora da viagem, informações adicionais, além do carro no qual viajará. Essas informações podem ser acessadas de três formas:
- Pelo motorista: pesquisa-se as viagens cadastradas pela placa do carro.
- Pelo cliente: pesquisa-se as viagens cadastradsa pelo CPF do cliente.
- Pelo administrador: pesquisa-se todas as viagens. É possível editar e excluir as viagens, utilizando-se do ID.

## ⚒ Tecnologias
- Java 
- Hibernate
- PrimeFaces

## ⚙ Como rodar
- Baixar o JDK 1.8 do Java.
- Criar banco de dados MySQL de nome "agendamento" e conectá-lo.
- Utilizar o NetBeans 8.2 para abrir o projeto, clicando no símbolo de play.
- Configurar o persistence.xml e hibernate.cfg.xml para apontar para o banco de dados;
- Fazer uso do Glassfish 4.4.1 para rodar o servidor;

## 📝 Regras de negócio
- Só pode existir um nome de cliente por CPF cadastrado
- As viagens devem possui 10 minutos de diferença para cliente e para carro
- O administrador necessita logar com o par "admin"/"admin"