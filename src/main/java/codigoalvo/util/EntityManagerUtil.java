package codigoalvo.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EntityManagerUtil implements ServletContextListener {

    private static final String PERSISTENCE_UNIT = "default";

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @Override
    public void contextInitialized(ServletContextEvent event) {
	emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
	if (em != null) {
	    em.close();
	}
	if (emf != null) {
	    emf.close();
	}
    }

    private static EntityManager createEntityManager() {
	if (emf == null) {
	    emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}
	return emf.createEntityManager();
    }

    public static EntityManager getEntityManager() {
	if (em == null) {
	    em = createEntityManager();
	}
	return em;
    }

}
