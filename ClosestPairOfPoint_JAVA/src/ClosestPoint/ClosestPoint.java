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
			
			//x 기분 정렬
			readPoint = pqs.pointQuickSort(readPoint, true);
			
			double shortDistance = findclosestPoint(readPoint, arrayLength);
			
			System.out.println("거리 = "+shortDistance);
			System.out.println("DONE");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static double findclosestPoint(Point[] p, int n) {
		int mid_x = n/2;
		
   		if(n <= 8 ){
			return bruteForce(p, n);
		}
		
		double dLeft = findclosestPoint(p, mid_x);
		double dRight = findclosestPoint(p, n-mid_x);
		
		double dis = Math.min(dLeft, dRight);
		
		
		Point[] tt = new Point[ n];
		int j =0;
		for(int i=0; i<n; i++){
			double tempDis = Math.abs(p[i].x_point - p[mid_x].x_point);
			if(tempDis < dis){
				tt[j] = p[i];
				j++;
			}
		}
		
		
		return Math.min(dis, closestTT(tt, j, dis));
	}
	
	private static double closestTT(Point[] tt, int size, double dis) {
		double min = dis;
		
		Point[] reArray = removeNULLValue(tt, size);
		
		tt = pqs.pointQuickSort(reArray, false);
		
		for(int i=0; i<size; i++){
			for(int j=i+1; j<size && (tt[j].y_point - tt[i].y_point) < min; ++j){
				if(distance(tt[i], tt[j]) < min){
					min = distance(tt[i], tt[j]);
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

	private static double bruteForce(Point[] p, int n){
		double min = MAXDis;
		double d;
		
		for(int i=0; i<n; ++i){
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
