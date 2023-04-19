package domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class HandleAgendamento {

    public boolean create (String nomeAgendado, Date dataAgendamento, String carro, String textoLivre, String cpf) {
        try {
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();

            Timestamp dateTimestamp = new Timestamp(dataAgendamento.getTime());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            int diff = dateTimestamp.compareTo(currentTimestamp);
            
            if(diff < 0){
                return false;
            }

            if(nomeAgendado == null || nomeAgendado.isEmpty()){
                return false;
            }

            if(carro == null || carro.isEmpty()){
                return false;
            }

            if(cpf == null || cpf.isEmpty()){
                return false;
            }

            String nomeCpf = this.readNameByCPF(cpf);
            if(nomeCpf != null && !nomeCpf.isEmpty()){
                if(!nomeCpf.equals(nomeAgendado)){
                    return false;
                }   
            }

            em.getTransaction().begin();
            Agendamento agendamento = new Agendamento(nomeAgendado, dataAgendamento, carro, textoLivre, cpf);
            em.persist(agendamento);
            em.getTransaction().commit();

            em.close();
            emf.close();
        } catch (Exception e) {
            return false;
        }   

        return true;
    }

    public boolean update (Long id, String nomeAgendado, Date dataAgendamento, String carro, String textoLivre, String cpf) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            Agendamento agendamento = em.find(Agendamento.class, id);
            agendamento.setNomeAgendado(nomeAgendado);
            agendamento.setDataAgendamento(dataAgendamento);
            agendamento.setCarro(carro);
            agendamento.setTextoLivre(textoLivre);
            agendamento.setCpf(cpf);
            em.getTransaction().commit();

            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }   

        return true;
    }

    public boolean delete (Long id) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            Agendamento agendamento = em.find(Agendamento.class, id);
            em.remove(agendamento);
            em.getTransaction().commit();

            em.close();
            emf.close();
        } catch (Exception e) {
            return false;
        }   

        return true;
    }

    public List<Agendamento> read () {
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();

            agendamentos = em.createQuery("SELECT a FROM Agendamento a", Agendamento.class).getResultList();

            em.close();
            emf.close();
        } catch (Exception e) {
            return null;
        }   

        return agendamentos;
    }   

    public Agendamento readById (Long id) {
        Agendamento agendamento = new Agendamento();

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();

            agendamento = em.find(Agendamento.class, id);

            em.close();
            emf.close();
        } catch (Exception e) {
            return null;
        }   

        return agendamento;
    }

    public List<Agendamento> readByCar (String carro) {
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();
          
            agendamentos = em.createQuery("SELECT a FROM Agendamento a WHERE a.carro = :placa_carro", Agendamento.class).setParameter("placa_carro", carro).getResultList();

            em.close();
            emf.close();
        } catch (Exception e) {
            return null;
        }   

        return agendamentos;
    }
    
    public List<Agendamento> readByCpf (String cpf) {
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();

            agendamentos = em.createQuery("SELECT a FROM Agendamento a WHERE a.cpf = :cpf", Agendamento.class).setParameter("cpf", cpf).getResultList();

            em.close();
            emf.close();
        } catch (Exception e) {
            return null;
        }   

        return agendamentos;
    }

    public String readNameByCPF (String cpf) {
        String nome = "";

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("capgov_crud");
            EntityManager em = emf.createEntityManager();

            nome = em.createQuery("SELECT a.nomeAgendado FROM Agendamento a WHERE a.cpf = :cpf", String.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            em.close();
            emf.close();
        } catch (Exception e) {
            return null;
        }   

        return nome;
    }
}