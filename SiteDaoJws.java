package database.jpa.assignment;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/api")
public class SiteDaoJws 
{	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("databasesAssignment5");
	EntityManager em;
	private EntityTransaction tx;
		
	public SiteDaoJws()
	{
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}
	
	@GET
	@Path("/site")
	@Produces(MediaType.APPLICATION_JSON)
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
	
	@GET
	@Path("/site/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Site findSite(@PathParam("id") int siteId)
	{
		tx.begin();
		Site site = em.find(Site.class, siteId); //This is not a transaction and hence no need for the begin and end transaction as it is not persisting anything
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
	@PUT
	@Path("/site/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> updateSite(@PathParam("id") int siteId, Site site)
	{
		Site siteToBeUpdated = em.find(Site.class, siteId); //First find the Site, this does not need transaction as not to persist to database
		
		if(siteToBeUpdated != null) //If Site is present then update it, update can also be performed using merge method
		{
			tx.begin();
			siteToBeUpdated.SetId(site.GetId());
			siteToBeUpdated.SetLatitude(site.GetLatitude());
			siteToBeUpdated.SetLongitude(site.GetLongitude());
			siteToBeUpdated.SetName(site.GetName());
			siteToBeUpdated.SetTowers(site.GetTowers());
			tx.commit(); // Commit automatically persists the changes in database
			
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
		else
		{
			System.out.println("Not Able to update the book as it is not found in the database, Site ID =  " + siteId);
			return null;
		}

	}
	
	@DELETE
	@Path("/site/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")	
	public List<Site> removeSite(@PathParam("id") int siteId)
	{
		Site siteToBeDeleted = em.find(Site.class, siteId); //First find the Site
		
		if(siteToBeDeleted != null) //If Site is present then update it, update can also be performed using merge method
		{
			tx.begin();
			em.remove(siteToBeDeleted);
			tx.commit(); // Commit automatically persists the changes in database			
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
		else
		{
			System.out.println("Not Able to Delete the book as it is not found in the database, Site ID =  " + siteId);
			return null;
		}
	}
	
	@POST
	@Path("/site")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")	
	public List<Site> createSite(Site site) 
	{
		try
		{
			tx.begin();
			em.persist(site);
			tx.commit(); 
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
		catch(Exception ex)
		{
			System.out.println(" An Error Occuered creating new site " + site +  " refer stack trace " + ex.getStackTrace());
			tx.rollback(); //Roll back the transaction if it is not fully successfully
			return null;
		}
	}
}
