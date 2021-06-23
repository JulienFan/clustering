package pack;

import java.util.Comparator;

public class Mycomparator implements Comparator<Object>{

	@Override

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub

		Cluster a = (Cluster) o1;
		Cluster b = (Cluster) o2;


		//Descendant Number of points
		return new Integer(b.getPointnum()).compareTo(new Integer(a.getPointnum()));



	}
}
