#define _CRT_SECURE_NO_DEPRECATE

#include "readhead.h"

#define MAX 100000000;

int compareX(const void *a, const void *b);
int compareY(const void *a, const void *b);
int compareDistance(double a, double b);
Point* removeNullElement(Point* p, int size);

float distancePointToPoint(Point a, Point b);
float closest(Point* p, int s, int n);
float bruteForce(Point* p, int s, int n);
float scanPoint(Point* point, int size, double dis);



int main(void) {
	char* filename = "point.txt";

	float distance;

	int pointSize = countElement(filename);
	Point* pointArray = readPoint(filename, pointSize);

	//sort x-coordinate
	qsort(pointArray, pointSize, sizeof(Point), compareX);

	distance = closest(pointArray, 0, pointSize);

	printf("최소 거리 : %f\n", distance);

}

float closest(Point* p, int s, int n) {
	int mid = (n + s) / 2;
	int i, j;
	float dLeft, dRight, dis, tempD;
	Point midP = p[mid];

	//Compute
	if (n - s <= 8) {
		return bruteForce(p, s, n);
	}

	dLeft = closest(p, s, mid);
	dRight = closest(p, mid+1, n);
	
	dis = min(dLeft, dRight);


	//Delete
	Point* tempPoint = (Point*)malloc(sizeof(Point)*(n-s));
	j = 0;

	for (i = s; i < n; i++) {
		tempD = abs( p[i].x - p[mid].x);
		if (tempD < dis) {
			tempPoint[j] = p[i];
			j++;
		}
	}

	//Sort y-coordinate
	Point* linePoint = removeNullElement(tempPoint, j);
	qsort(linePoint, j, sizeof(Point), compareY);


	//Scan
	float scanValue = scanPoint(linePoint, j, dis);

	return min(dis, scanValue);
}

float scanPoint(Point* point, int size, double dis) {
	float min = dis;

	for (int i = 0; i < size; i++) {
		for (int j = i + 1; j < size && (point[j].y - point[i].y) < min; ++j) {
			if (distancePointToPoint(point[i], point[j]) < min) {
				min = distancePointToPoint(point[i], point[j]);
			}
		}
	}

	return min;

}
Point* removeNullElement(Point* p, int size) {
	Point* result = (Point*)malloc(sizeof(Point)*size);

	for (int i = 0; i < size; i++) {
		result[i] = p[i];
	}

	return result;
}

float bruteForce(Point* p, int s, int n) {
	float min = MAX;
	float d;

	for (int i = s; i < n; ++i) {
		for (int j = i + 1; j < n; ++j) {
			d = distancePointToPoint(p[i], p[j]);
			if (d < min) {
				min = d;
			}
		}
	}

	return min;
}

int compareDistance(double a, double b) {
	int result = 0;

	if (a < b) {
		result = -1;
	}
	else if (a > b) {
		result = 1;
	}
	else {
		result = 0;
	}

	return result;
}

int compareX(const void *a, const void *b) {
	return compareDistance(((const Point*) a) ->x, ((const Point*)b) -> x);
}

int compareY(const void *a, const void *b) {
	return compareDistance(((const Point*)a)->y, ( (const Point*)b)-> y);
}

float distancePointToPoint(Point a, Point b) {
	float disX = abs(a.x - b.x);
	float disY = abs(a.y - b.y);

	float result = sqrt(pow(disX,2)+pow(disY,2));
	return result;
}