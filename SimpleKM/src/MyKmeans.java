
import java.util.*;

// main class: 1. Invokes the parser
//             2. finds the min/max values
//             3. does the normalization
//             4. invokes the clusterer.


public class MyKmeans {
	static ArrayList<NamedVectorInstance> list = new ArrayList<NamedVectorInstance>();
	static ArrayList<NamedVectorInstance> normalizedList;
	static double[] mins;
	static double[] maxs;
	
	
	// import the list of instances from a file, normalize them
	// and then pass the instances as well as k to a KmeansClusterer
	public static void main (String[] args){
		int k = 3;
		getData();
		findMinMax(list); 
		createNormalizedList(list);	//normalizedList = list;
		KmeansClusterer clusterer = new KmeansClusterer(normalizedList, k);
		clusterer.cluster();
	}

	
	// accepts the non-normalized list as a parameter, saves a normalized list
	// to the class variable normalizedList
	private static void createNormalizedList(ArrayList<NamedVectorInstance> listParam) {
		// don't destroy the original list, make a clone
		normalizedList = (ArrayList<NamedVectorInstance>) listParam.clone(); //gives a warning, the code still works though
		
		int arraySize = list.get(0).size();
		
		for (int i = 0 ; i < list.size(); i ++){
			NamedVectorInstance inst = list.get(i);
			NamedVectorInstance newInst = normalizedList.get(i);
			for (int j = 0; j < arraySize; j ++){
				double val = inst.get(j);
				double newValue = (val - mins[j]) / (maxs[j] - mins[j]);
				newInst.set(j, newValue);
			}
		}
	}

	
	// find the minimum and maximum values for each attribute in a data set
	// stores these values in the maxs and mins arrays
	private static void findMinMax(ArrayList<NamedVectorInstance> list2) {
		int arraySize = list2.get(0).size();
		mins = new double[arraySize];
		maxs = new double[arraySize];
		
		for (int i =0; i < arraySize; i++){
			mins[i] = Double.MAX_VALUE;
			maxs[i] = Double.MIN_VALUE;
		}
		
		for (int i = 0 ; i < list2.size(); i ++){
			NamedVectorInstance inst = list2.get(i);
			for (int j = 0; j < arraySize; j ++){
				double val = inst.get(j); 
				if (val < mins[j])
					mins[j]=val;
				if (val>maxs[j])
					maxs[j] = val;
			}
		}
/*		
		for (int i = 0; i < arraySize; i++){
			System.out.print(mins[i] + " ");
		}	
		System.out.println();
		for (int i = 0; i < arraySize; i++){
			System.out.print(maxs[i] + " ");
		}	
		System.out.println();*/
	}

	private static void getData() {
		ParseCSV parser = new ParseCSV();
		list = parser.getInstances("/home/aas/workspace_original/SimpleKM/src/iris-random.csv");
	}
}
