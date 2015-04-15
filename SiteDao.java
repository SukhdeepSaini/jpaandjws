package database.jpa.assignment;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

public class SiteDao 
{
	private EntityManagerFactory emf =  Persistence.createEntityManagerFactory("databasesAssignment5");
	private EntityManager em;
	private EntityTransaction tx;
	
	public SiteDao()
	{
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}
	
	public Site findSite(int siteId)
	{
		tx.begin();
		Site site = em.find(Site.class, siteId); //This will work even without using transaction as it is not persisting anything to database
		tx.commit();
		em.close();
		
		if(site != null)
		{
			return site; 
		}
		else
		{
			System.out.println("Not able to find site in database with siteID = " + siteId);
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<Site> findAllSites()
	{
		List<Site> allSites = new ArrayList<Site>();
		
		try
		{
			tx.begin();
			allSites = em.createNamedQuery("GetAllSites").getResultList();
			tx.commit();
			em.close();
		}
		catch(Exception ex)
		{
			System.out.println("An Error Occuerred while Reteriving Site Data from Database, Please refer stack trace " + ex.getStackTrace());
			return null;
		}
		
		return allSites;
	}
	
	public List<Site> updateSite(int siteId, Site site)
	{
		Site siteToBeUpdated = em.find(Site.class, siteId); //First find the Site, No Need to create transaction
		
		if(siteToBeUpdated != null) //If Site is present then update it, update can also be performed using merge method
		{
			tx.begin();
			siteToBeUpdated.SetId(site.GetId());
			siteToBeUpdated.SetLatitude(site.GetLatitude());
			siteToBeUpdated.SetLongitude(site.GetLongitude());
			siteToBeUpdated.SetName(site.GetName());
			siteToBeUpdated.SetTowers(site.GetTowers());
			tx.commit(); // Commit automatically persists the changes in database
			em.close();
			
			List<Site> allSites = findAllSites();
			return allSites;
			
		}
		else
		{
			System.out.println("Not Able to update the book as it is not found in the database, Site ID =  " + siteId);
			return null;
		}
	}
	
	public List<Site> removeSite(int siteId)
	{
		Site siteToBeDeleted = em.find(Site.class, siteId); //First find the Site
		
		if(siteToBeDeleted != null) //If Site is present then update it, update can also be performed using merge method
		{
			tx.begin();
			em.remove(siteToBeDeleted);
			tx.commit(); // Commit automatically persists the changes in database
			em.close();
			
			List<Site> allSites = findAllSites();
			return allSites;
			
		}
		else
		{
			System.out.println("Not Able to Delete the book as it is not found in the database, Site ID =  " + siteId);
			return null;
		}	
	}
	
	public List<Site> createSite(Site site)
	{
		try
		{
			tx.begin();
			em.persist(site);
			tx.commit(); 
			em.close();
			
			List<Site> allSites = findAllSites();
			return allSites;
		}
		catch(Exception ex)
		{
			System.out.println(" An Error Occuered creating new site " + site +  " refer stack trace " + ex.getStackTrace());
			tx.rollback(); //Roll back the transaction if it is not fully successfully
			return null;
		}
	}
}
