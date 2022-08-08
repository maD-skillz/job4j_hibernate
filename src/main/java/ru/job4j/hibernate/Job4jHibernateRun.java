package ru.job4j.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class Job4jHibernateRun {

	public static void main(String[] args) {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure()
				.build();

		try {
			SessionFactory sf = new MetadataSources(registry)
					.buildMetadata()
					.buildSessionFactory();

			Session session = sf.openSession();
			session.beginTransaction();

			Query query = session.createQuery("from Candidate ");
			for (Object st : query.list()) {
				System.out.println(st);
			}

			Query query1 = session.createQuery("from Candidate c where c.id = 1");
			System.out.println(query1.uniqueResult());

			Query query2 = session.createQuery(
					"from Candidate c where c.name like 'Tony'");
			System.out.println(query2.uniqueResult());

			session.createQuery(
					"update Candidate c "
							+ "set c.name = :newName,"
							+ " c.experience = :newExperience "
							+ "where c.id = :fId")
					.setParameter("newName", "Marcus")
					.setParameter("newExperience", 6)
					.setParameter("fId", 3)
					.executeUpdate();

			session.createQuery("delete from Candidate c where c.id = :fId")
					.setParameter("fId", 2)
					.executeUpdate();

			session.getTransaction();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

}
