package database.jpa.assignment;
import javax.persistence.*;

@Entity
@NamedQuery(name="GetAllEquipment", query="SELECT equipment FROM Equipment equipment")
public class Equipment 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String brand;
	private String description;
	private double price;

	@ManyToOne
	@JoinColumn(name = "towerId")
	private Tower tower;

	public Equipment() 
	{
		super();
	}
	
	public Equipment(String brand, String description, String name,double price, Tower tower) 
	{
		super();
		this.brand = brand;
		this.description = description;
		this.name = name;
		this.price = price;
		this.tower = tower;
	}

	public int GetId() 
	{
		return this.id;
	}

	public void SetId(int id) 
	{
		this.id = id;
	}

	public String GetBrand() 
	{
		return this.brand;
	}

	public void SetBrand(String brand) 
	{
		this.brand = brand;
	}

	public String GetDescription() 
	{
		return this.description;
	}

	public void SetDescription(String description) 
	{
		this.description = description;
	}

	public String GetName() 
	{
		return this.name;
	}

	public void SetName(String name) 
	{
		this.name = name;
	}

	public double GetPrice() 
	{
		return this.price;
	}

	public void SetPrice(double price)
	{
		this.price = price;
	}

	public Tower GetTower() 
	{
		return this.tower;
	}

	public void SetTower(Tower tower)
	{
		this.tower = tower;
	}
}
