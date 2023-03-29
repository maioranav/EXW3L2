package controller;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import model.EventType;
import model.Evento;
import utils.JpaUtil;

public class EventoDAO {

	static EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	static EntityManager em = emf.createEntityManager();

	public static void main(String[] args) {
		
		Evento e1 = new Evento();
		e1.setTipo(EventType.pubblico);
		e1.setTitolo("Bianconiglio");
		e1.setData(LocalDate.of(2023, 12, 24));
		e1.setMax(150);
		
		try {
			save(e1);
			System.out.println("\n\t *** FINDALL METHOD");
			System.out.println(findAllEvents());
			
			System.out.println("\n\t *** GETBYID (2) METHOD");
			Evento e2 = getById(2l);
			System.out.println(e2);
			
			System.out.println("\n\t *** GETBYID (2) METHOD AFTER UPDATE");
			e2.setData(LocalDate.of(2024, 11, 03));
			e2.setTipo(EventType.privato);
			update(e2);
			e2 = getById(2l);
			System.out.println(e2);
			
			
			System.out.println("\n\t *** FIND ALL AFTER DELETE");
			e2.setData(LocalDate.of(2024, 11, 03));
			delete(e2);
			System.out.println(findAllEvents());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void save(Evento e) throws PersistenceException {
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
		System.out.println("Evento aggiunto al DB");
	}

	public static Evento getById(Long id) throws PersistenceException {
		em.getTransaction().begin();
		Evento e = em.find(Evento.class, id);
		em.getTransaction().commit();
		return e;
	}

	public static void update(Evento e) throws PersistenceException {
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
		System.out.println("Evento aggiornato");
	}

	public static void delete(Evento e) throws PersistenceException {
		em.getTransaction().begin();
		em.remove(e);
		em.getTransaction().commit();
		System.out.println("Evento annullato");
	}

	public static List<Evento> findAllEvents() {
		Query q = em.createNamedQuery("Evento.findAll");
		return q.getResultList();

	}

}
