package ClosestPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClosestPoint {
	
	static int arrayLength = 1000;
	static double MAXDis = 100000;
	
	static PointQuickSort pqs = new PointQuickSort();

	public static void main(String[] args) {
		String filePath = "point.txt";
		
		Point[] readPoint;
		
		try {
			
			readPoint = readInputFile(filePath);
			
			//Sort x-coordinate
			readPoint = pqs.pointQuickSort(readPoint, true);
			
			double shortDistance = closestPoint(readPoint, 0, arrayLength);
			
			System.out.println("거리 = "+shortDistance);
			System.out.println("DONE");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static double closestPoint(Point[] p, int s, int n) {
		
		//Compute
		int midline = (n+s)/2;
		
   		if(n-s <= 8){
			return bruteForce(p, s, n);
		}
		
		double dLeft = closestPoint(p, s, midline);
		double dRight = closestPoint(p, midline+1, n);
		
		double dis = Math.min(dLeft, dRight);
		
		
		//Delete
		Point[] linePoint = new Point[n];
		int j=0;
		for(int i=s; i<n; i++){
			double temp = Math.abs(p[i].x_point - p[midline].x_point);
			if(temp < dis){
				linePoint[j] = p[i];
				j++;
			}
		}
	
		//Sort y-coordinate
		linePoint = removeNULLValue(linePoint, j);
		linePoint = pqs.pointQuickSort(linePoint, false);
		
		//Scan
		double scanValue = scanPoint(linePoint, dis);
		
		return Math.min(dis, scanValue);
	}
	
	private static double scanPoint(Point[] linePoint, double dis) {
		double min = dis;
				
		for(int i=0; i<linePoint.length; i++){
			for(int j=i+1; j <linePoint.length && (linePoint[j].y_point - linePoint[i].y_point) < min ; j++){
				if(distance(linePoint[i], linePoint[j]) < min){
					min = distance(linePoint[i], linePoint[j]);
				}
			}
		}
		return min;
	}

	private static Point[] removeNULLValue(Point[] tt, int size) {
		Point[] result = new Point[size];
		
		for(int i=0; i<size; i++){
			result[i] = tt[i];
		}
		return result;
	}

	private static double distance(Point point, Point point2) {
		long tempX = Math.abs(point.x_point - point2.x_point);
		long tempY = Math.abs(point.y_point - point2.y_point);
		
		return Math.sqrt(Math.pow(tempX, 2)+Math.pow(tempY, 2));
	}

	private static double bruteForce(Point[] p, int s, int n){
		double min = MAXDis;
		double d;
		
		for(int i=s; i<n; ++i){
			for(int j=i+1; j<n; ++j){
				d = distance(p[i], p[j]);
				if(d < min){
					min = d;
				}
			}
		}
		
		return min;
	}
	

	private static Point[] readInputFile(String filePath) throws NumberFormatException, IOException {
		File inputFile = new File(filePath);
		FileInputStream fis = new FileInputStream(inputFile);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		Point[] tempArray = new Point[arrayLength];
		
		String s;
		int xP;
		int yP;
		
		int i=0;
		
		while((s = br.readLine())!=null){
			String[] temp = s.split(",");
			String x_temp = temp[0].substring(1);
			String y_temp = temp[1].substring(0, temp[1].length()-1);
			
			Point point = new Point(Integer.parseInt(x_temp), Integer.parseInt(y_temp));
			tempArray[i]= point;
			i++;
		}
		
		br.close();
		isr.close();
		fis.close();
		
		return tempArray;
	}
	

	
}
