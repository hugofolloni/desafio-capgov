package domain;

/**
 *
 * @author hugofolloni
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agendamento")        
public class Agendamento implements Serializable {

    public Agendamento() {
    }
    
    public Agendamento (String nomeAgendado, Date dataAgendamento, String carro, String textoLivre, String cpf){
        super();
        this.nomeAgendado = nomeAgendado;
        this.dataAgendamento = dataAgendamento;
        this.carro = carro;
        this.textoLivre = textoLivre;
        this.cpf = cpf;
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_agendamento")
    private Date dataAgendamento;

    @Column(name = "nome_agendado")
    private String nomeAgendado;
    
    @Column(name = "carro")
    private String carro;

    @Column(name = "texto_livre")
    private String textoLivre;

    @Column(name = "cpf")
    private String cpf;

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

    public void setCpf (String cpf){
        this.cpf = cpf;
    }   

    public String getCpf () {
        return this.cpf;
    }
    
    public Long getId(){
        return this.id;
    }    

}