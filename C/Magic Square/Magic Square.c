#include<stdio.h>
#include<stdlib.h>
#include "magic.h"
int main (int argc, char*argv[argc+1]){
	if (argc < 2)	{
		printf("Insufficient arguemnts/n");
		return EXIT_SUCCESS;
	}
	int n = atoi(argv[1]);
	if ( n%2 ==0)	{
		printf("error\n");
		return EXIT_SUCCESS;
	}
	int** matrix = malloc(n*sizeof(int*));
	for (int i = 0; i <n; i++){ // allocates matrix space
		matrix[i] = malloc(n*sizeof(int));
	}
    initialize(matrix, n, 0, 0);
    if (n ==1)  matrix[0][0]=1;
	else matrix = mat(matrix, n);
    print(matrix, n, 0, 0);
	freen(matrix, n, 0);
    free(matrix);
    return EXIT_SUCCESS;
}
int ** mat (int** matrix, int n){
    int mid = (n/2); int a =0; int b =mid; // finds midpoint
    for (int i =1; i <= (n*n); i ++){ 
        if (i ==1)  {matrix[0][b]= i;} // intializes at midpoint
        else if ((a-1) <0){ // checks if going diagnol makes row
            if ((b+1) > n-1)  { a+=1; matrix[a][b] = i;}
            else    {a = a-1+n; b +=1; matrix[a][b] = i; }
        }
        else if ((b+1) > (n-1))  {b = b+1-n; a-=1; matrix[a][b] = i; } // checks if going diagnol makes row
        else if (matrix[a-1][b+1]==0) { a -=1; b +=1; matrix[a][b]= i; } // checks if the diagnol cell is empty
        else   {a+=1; matrix[a][b] = i; } // if not goes vertically down
    }
    return matrix;
}
int print(int ** matrix, int n, int row, int col){
    if(col >= n) { printf("\n"); return 0;}
    if(row >= n) return 1;
    printf("%d\t", matrix[row][col]);
    if(print(matrix, n, row, ++col) ==1) return 1;
    return print(matrix, n, ++row, 0);
}
int initialize(int** matrix, int n, int row, int col){
    if(col >= n) return 0;
    if(row >= n) return 1;
    matrix[row][col]=0;
    if(initialize(matrix, n, row, ++col) ==1) return 1;
    return initialize(matrix, n, ++row, 0);
}
void freen(int** matrix, int n, int count){
    if(count == n)  return;
    free(matrix[count]);
    freen(matrix, n, ++count);
}
