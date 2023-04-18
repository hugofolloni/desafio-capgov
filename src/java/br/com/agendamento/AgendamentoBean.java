package br.com.agendamento;

import domain.Agendamento;
import domain.HandleAgendamento;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;

@RequestScoped
@ManagedBean
public class AgendamentoBean {

    private String nomeAgendado;    
    private Date dataAgendamento;
    private String carro;   
    private String textoLivre;
    private List<Agendamento> agendamentos;
    private Agendamento agendamento;
    private Long id;
    private String cpf;
    
    private String response;

    public void setNomeAgendado (String nomeDoAgendado) {
        this.nomeAgendado = nomeDoAgendado;
    }

    public String getNomeAgendado () {
        return this.nomeAgendado;
    }
    
    public void setTextoLivre (String textoLivre) {
        this.textoLivre = textoLivre;
    }   
    
    public String getTextoLivre (){
        return this.textoLivre;
    }
    
    public void setDataAgendamento (Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
    
    public Date getDataAgendamento (){
        return this.dataAgendamento;
    }
    
    public void setCarro (String carro){
        this.carro = carro;
    }
    
    public String getCarro () {
        return this.carro;
    }
    
    public Long getId(){
        return this.id;
    }    
    
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public String getCpf(){
        return this.cpf;
    }

    public void create () {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        
        Timestamp dateTimestamp = new Timestamp(dataAgendamento.getTime());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        int diff = dateTimestamp.compareTo(currentTimestamp);
        
        
        if(diff < 0){
            this.setResponse("Data inválida!");
        }
        else {
            handleAgendamento.create(nomeAgendado, dataAgendamento, carro, textoLivre, cpf);
            this.setResponse("Agendamento criado!");
        }
    }

    public List<Agendamento> getAgendamentos() {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        agendamentos = handleAgendamento.read();
        return agendamentos;
    }   

    public Agendamento getAgendamento(Long id) {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        agendamento = handleAgendamento.readById(id);
        return agendamento;
    }

    public List<Agendamento> getAgendamentoByCarro(String carro) {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        agendamentos = handleAgendamento.readByCar(carro);
        return agendamentos;
    }

    public String delete () {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        handleAgendamento.delete(id);
        return "agendamento.xhtml";
    }

    public String update () {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        handleAgendamento.update(id, nomeAgendado, dataAgendamento, carro, textoLivre, cpf);
        return "agendamento.xhtml";
    }
    
    public void setResponse (String response) {
        this.response = response;
    }
    
    public String getResponse (){
        return this.response;
    }

}

