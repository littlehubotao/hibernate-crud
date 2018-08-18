
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;



public class UsersTest {
	
	@Test
	public void testAdd(){
		Transaction tx =null;
		Session session =null;
		try {
			Configuration config = new Configuration().configure();
			SessionFactory factory = config.buildSessionFactory();
			session = factory.openSession();
			tx = session.beginTransaction();
			Users users1 = new Users("2","hubotao", "123456", "150", "hubotao", "1");
			session.save(users1);
			//System.out.println(session.isConnected());;
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
	}
	@Test
	public void testUpdate(){
		Transaction tx =null;
		Session session =null;
		try {
			Configuration config = new Configuration().configure();
			SessionFactory factory = config.buildSessionFactory();
			session = factory.openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery("update Users set name ='update-hubotao' where id='0'");
			q.executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
	}
	//查询整个映射对象 所有字段  查整表
	@Test
	public void testSelect1(){
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session  = factory.openSession();
		Query q = session.createQuery("from Users as u");
		List<Users> result = q.list();
		for (Users users : result) {
			System.out.println(users.toString());
		}
		
	}
	
	//查询单个字段（多条记录的）
		@Test
		public void testSelect2(){
			Configuration config = new Configuration().configure();
			SessionFactory factory = config.buildSessionFactory();
			Session session  = factory.openSession();
			Query q = session.createQuery("select u.name from Users as u");
			List<String> a = q.list();
			System.out.println(a);
		}
		
		
	// 查询多个字段（多条记录的）   Object[]接收
	@Test
	public void testSelect3() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		Query q = session.createQuery("select u.name,u.id from Users as u");
		List<Object[]> a = q.list();
			for (Object[] objects : a) {
				System.out.println("name:"+objects[0]);
				System.out.println("id:"+objects[1]);
		}
	}
	
	// 查询多个字段  Map接受   
	/**
	 * 留意：取map集合数据的时候，map.get("")里面记得加引号，因为map中的key值是索引值，字符串类型。
	 */
		@Test
		public void testSelect4() {
			String hql = "select new map(u.name,u.id) from Users as u";
			Configuration config = new Configuration().configure();
			SessionFactory factory = config.buildSessionFactory();
			Session session = factory.openSession();
			Query q = session.createQuery(hql);
			List<Map> maps = q.list();
			for (Map map : maps) {
				System.out.println("name:"+map.get("0"));
				System.out.println("id:"+map.get("1"));
			}
		}
		
		// 查询多个字段  List接收  
				@Test
				public void testSelect5() {
					String hql ="select new list(u.name,u.id) from Users as u";
					Configuration config = new Configuration().configure();
					SessionFactory factory = config.buildSessionFactory();
					Session session = factory.openSession();
					Query q = session.createQuery(hql);
					List<List> a = q.list();
					for (List list : a) {
						System.out.println("name:"+list.get(0));
						System.out.println("id:"+list.get(1));
					}
				}

}
