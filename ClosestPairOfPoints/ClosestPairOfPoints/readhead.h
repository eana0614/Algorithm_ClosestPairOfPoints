#pragma once

#include<stdio.h>
#include<stdlib.h>
#include<memory.h>
#include<math.h>


typedef struct point{
	int x;
	int y;
} Point;

int countElement(char *filename) {

	int count = 0;
	int temp1, temp2;

	FILE *file = NULL;
	file = fopen(filename, "r");

	if (file != NULL) {
		while (!feof(file)) {
			fscanf(file, "(%d,%d)\n", &temp1, &temp2);
			count++;
		}
		fclose(file);
	}

	else {
		printf("!] FILE OPEN ERROR\n");
		exit(1);
	}

	return count;
}

Point* readPoint(char* filename, int length) {
	Point* temp = (Point*)malloc(sizeof(Point)*length);
	int xP, yP, i=0;

	FILE *file = NULL;
	file = fopen(filename, "r");

	if (file != NULL) {
		while (!feof(file)) {
			fscanf(file, "(%d,%d)\n", &xP, &yP);
			temp[i].x = xP;
			temp[i].y = yP;
			i++;
		}
	}
	else {
		printf("!] FILE OPEN ERROR\n");
		exit(1);
	}

	return temp;
}
