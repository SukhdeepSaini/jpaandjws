package database.jpa.assignment;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQuery(name="GetAllTowers", query="SELECT tower FROM Tower tower")
public class Tower 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private double height;
	private int sides;

	@OneToMany(mappedBy="tower", fetch=FetchType.EAGER)
	private List<Equipment> equipments;

	@ManyToOne
	@JoinColumn(name="siteId")
	private Site site;

	public Tower() 
	{
		super();
	}

	public Tower(double height, String name, int sides, List<Equipment> equipments, Site site) 
	{
		super();
		this.height = height;
		this.name = name;
		this.sides = sides;
		this.equipments = equipments;
		this.site = site;
	}
	
	public int GetId() 
	{
		return this.id;
	}

	public void SetId(int id) 
	{
		this.id = id;
	}

	public double GetHeight() 
	{
		return this.height;
	}

	public void SetHeight(double height) 
	{
		this.height = height;
	}

	public String GetName() 
	{
		return this.name;
	}

	public void SetName(String name) 
	{
		this.name = name;
	}

	public int GetSides() {
		return this.sides;
	}

	public void SetSides(int sides) 
	{
		this.sides = sides;
	}
	
	public Site GetSite() 
	{
		return this.site;
	}

	public void SetSite(Site site) 
	{
		this.site = site;
	}

	public List<Equipment> GetEquipments() 
	{
		return this.equipments;
	}

	public void SetEquipments(List<Equipment> equipments) 
	{
		this.equipments = equipments;
	}

	public Equipment AddEquipment(Equipment equipment) 
	{
		this.GetEquipments().add(equipment);
		equipment.SetTower(this);
		return equipment;
	}

	public Equipment RemoveEquipment(Equipment equipment) 
	{
		this.GetEquipments().remove(equipment);
		equipment.SetTower(null);
		return equipment;
	}
}
