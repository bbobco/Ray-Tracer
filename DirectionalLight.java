import ThreeDimensional.Vector;


public class DirectionalLight extends Light 
{
	Vector direction;
	public DirectionalLight(Vector dir)
	{
		super(0);
		direction = dir;
		direction.normalize();
		type = "directional";
	}
	public Vector[] getLocs() 
	{
		return new Vector[]{direction};
	}
	public void move(Vector m) 
	{
	}
}
