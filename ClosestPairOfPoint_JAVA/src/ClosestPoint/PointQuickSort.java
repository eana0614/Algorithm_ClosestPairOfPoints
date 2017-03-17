package ClosestPoint;

public class PointQuickSort {
	
	public PointQuickSort(){
		
	}
	
	public Point[] pointQuickSort(Point[] inputArray, boolean xory){
	
		if(xory){
			inputArray = XQuickSort(inputArray, 0, inputArray.length-1);
		}else{
			inputArray = YQuickSort(inputArray, 0, inputArray.length-1);
		}
		
		return inputArray;
		
	}

	private Point[] YQuickSort(Point[] inputArray, int pivot, int r) {
		int q;
		
		if(pivot < r){
			q = partitionY(inputArray, pivot, r);
			inputArray = YQuickSort(inputArray, pivot, q-1);
			inputArray = YQuickSort(inputArray, q+1, r);
		}
		
		return inputArray;
	}

	private int partitionY(Point[] inputArray, int pivot, int r) {
		int temp = inputArray[r].y_point;
		int i = pivot-1;
		Point tPoint;
		
		for(int j=pivot; j<r;j++){
			if(inputArray[j].y_point < temp){
				i++;
				tPoint = inputArray[i];
				inputArray[i] = inputArray[j];
				inputArray[j] = tPoint;
			}
		}
		
		i++;
		tPoint = inputArray[i];
		inputArray[i] = inputArray[r];
		inputArray[r] = tPoint;
		
		return i;
	}

	private Point[] XQuickSort(Point[] inputArray, int pivot, int r) {
		int q;
		
		if(pivot < r){
			q = partitionX(inputArray, pivot, r);
			inputArray = XQuickSort(inputArray, pivot, q-1);
			inputArray = XQuickSort(inputArray, q+1, r);
			
		}
		
		return inputArray;
	}

	private int partitionX(Point[] inputArray, int pivot, int r) {
		int temp = inputArray[r].x_point;
		int i = pivot-1;
		Point tPoint;
		
		for(int j=pivot; j<r-1;j++){
			if(inputArray[j].x_point <= temp){
				i++;
				tPoint = inputArray[i];
				inputArray[i] = inputArray[j];
				inputArray[j] = tPoint;
			}
		}
		
		i++;
		tPoint = inputArray[i];
		inputArray[i] = inputArray[r];
		inputArray[r] = tPoint;
		
		return i;
	}

}
