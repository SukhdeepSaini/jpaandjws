package database.jpa.assignment;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQuery(name="GetAllSites", query="SELECT site FROM Site site")
public class Site 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double latitude;
	private double longitude;
	
	@OneToMany(mappedBy = "site", fetch = FetchType.EAGER)
	private List<Tower> towers;
	
	public Site()
	{
		super();
	}
	
	public Site(String name, double latitude, double longitude)
	{
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Site(String name, double latitude, double longitude, List<Tower> towers)
	{
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.towers = towers;
	}
	
	//Getters and Setters
	
	public int GetId()
	{
		return this.id;
	}
	
	public void SetId(int id)
	{
		this.id = id;
	}
	
	public String GetName()
	{
		return this.name;
	}
	
	public void SetName(String name)
	{
		this.name = name;
	}
	
	public double GetLatitude()
	{
		return this.latitude;
	}
	
	public void SetLatitude(double latitude)
	{
		this.latitude = latitude;
	}
	
	public double GetLongitude()
	{
		return this.longitude;
	}
	
	public void SetLongitude(double longitude)
	{
		this.longitude = longitude;
	}
	
	public List<Tower> GetTowers()
	{
		return this.towers;
	}
	
	public void SetTowers(List<Tower> towers)
	{
		this.towers = towers;
	}
	
	public Tower AddTower(Tower tower)
	{
		this.GetTowers().add(tower);
		tower.SetSite(this);
		return tower;
	}
	
	public Tower RemoveTower(Tower tower)
	{
		this.GetTowers().remove(tower);
		tower.SetSite(null);
		return tower;
	}
}
