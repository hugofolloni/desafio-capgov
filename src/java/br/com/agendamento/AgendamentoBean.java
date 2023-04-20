package br.com.agendamento;

import domain.Agendamento;
import domain.HandleAgendamento;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    private String parametro;
    
    private String response;
    private String corResponse;
    
    private List<Agendamento> agendamentoByCarro;

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
    
    public void setId(Long id){
        this.id = id;
    }
    
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public String getCpf(){
        return this.cpf;
    }

    public String create () {
        this.setResponse("");
        
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        
        if(dataAgendamento == null){
            this.setResponse("O campo data não pode estar vazio") ;
            this.setCorResponse("red");
            return "";
        }
        
        Timestamp dateTimestamp = new Timestamp(dataAgendamento.getTime());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        int diff = dateTimestamp.compareTo(currentTimestamp);
        
        
        if(diff < 0){
            this.setResponse("Data inválida!");
            this.setCorResponse("red");
            return "";
        }
        
        if(nomeAgendado == null || nomeAgendado.isEmpty()){
            this.setResponse("O par Cliente-CPF não pode ser agendado!") ;
            this.setCorResponse("red");
            return "";
        }

        if(cpf == null || cpf.isEmpty()){
            this.setResponse("O campo CPF não pode estar vazio") ;
            this.setCorResponse("red");
            return "";
        }

        
        if(carro == null || carro.isEmpty()){
            this.setResponse("O campo carro não pode estar vazio!") ;
            this.setCorResponse("red");
            return "";
        }

        String nomeCpf = handleAgendamento.readNameByCPF(cpf);

        if(nomeCpf != null && !nomeCpf.isEmpty()){
            if(!nomeCpf.equals(nomeAgendado)){
                this.setResponse("O par Cliente-CPF não pode ser agendado!") ;
                this.setCorResponse("red");
                return "";
            }   
        }
        
        if(!checkCpfData(cpf, dataAgendamento)){
            this.setResponse("Essa reserva é muito próxima de outra para o mesmo cliente!") ;
            this.setCorResponse("red");
            return "";
        }
        
        if(!checkCarroData(carro, dataAgendamento)){
            this.setResponse("Essa reserva é muito próxima de outra para o mesmo carro!") ;
            this.setCorResponse("red");
            return "";
        }
        
            
        handleAgendamento.create(nomeAgendado, dataAgendamento, carro, textoLivre, cpf);
        this.setResponse("Agendamento criado!");
        this.setCorResponse("green");
        
        return "index.xhtml?faces-redirect=true&includeViewParams=true";
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

    public List<Agendamento> getAgendamentoByCarro() {
        return this.agendamentoByCarro;
    }
    
    public void setAgendamentoByCarro(String carro){
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        this.agendamentoByCarro = handleAgendamento.readByCar(carro);
    }

    public String delete () {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        handleAgendamento.delete(id);
        return "verReservasAdmin.xtml";
    }

    public String update () {
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        if(!handleAgendamento.update(id, nomeAgendado, dataAgendamento, carro, textoLivre, cpf)){
           this.setResponse("Não foi possível atualizar");      
        }  
        return "verReservasAdmin.xtml";
    }
    
    public void setResponse (String response) {
        this.response = response;
    }
    
    public String getResponse (){
        return this.response;
    }
    
    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }
    
    private void setCorResponse (String cor){
        this.corResponse = cor;
    }
    
    public String getCorResponse (){
        return this.corResponse;
    }
    
    public List<Agendamento> buscaPorCarro(){
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        List<Agendamento> lista = new ArrayList<>();
       
        lista = handleAgendamento.readByCar(this.parametro);
        return lista;
    }
    
    public List<Agendamento> buscaPorCpf(){
        HandleAgendamento handleAgendamento = new HandleAgendamento();
        List<Agendamento> lista = new ArrayList<>();
       
        lista = handleAgendamento.readByCpf(this.parametro);
        return lista;
    }
    
     public boolean checkCpfData(String cpf, Date data){
         HandleAgendamento handleAgendamento = new HandleAgendamento();
         List<Agendamento> lista = new ArrayList<>();
         
         lista = handleAgendamento.readByCpf(cpf);
         
         for(Agendamento agendamento : lista){          
             int diff = (int) (data.getTime() - agendamento.getDataAgendamento().getTime());
             if(diff < 600000 && diff > -600000){
                 System.out.println(diff);
                 return false;
             }
         }
         
         return true;        
     }
     
     public boolean checkCarroData(String carro, Date data){
         HandleAgendamento handleAgendamento = new HandleAgendamento();
         List<Agendamento> lista = new ArrayList<>();
         
         lista = handleAgendamento.readByCar(carro);
             
         for(Agendamento agendamento : lista){          
             int diff = (int) (data.getTime() - agendamento.getDataAgendamento().getTime());
             if(diff < 600000 && diff > -600000){
                 System.out.println(diff);
                 return false;
             }
         }
     
         return true;        
     }
     
     
     
     private String loginAdmin;
     private String senhaAdmin;
     
     public String getLoginAdmin(){
         return this.loginAdmin;
     }
     
     public void setLoginAdmin(String login){
         this.loginAdmin = login;
     }
     
     public String getSenhaAdmin(){
         return this.senhaAdmin;
     }
     
     public void setSenhaAdmin(String senha){
         this.senhaAdmin = senha;
     }
     
     public String authenticateAdmin(){
         if(loginAdmin.equals("admin") && senhaAdmin.equals("admin")){
             return "verReservasAdmin.xhtml?faces-redirect=true&includeViewParams=true";
         }
         else {
             this.setResponse("Não foi possível autenticar!");
             return "";
         }
     }

}

