#include<stdio.h>
#include<stdlib.h>
#include "determinant.h"

int main (int argc, char* argv[argc+1]){
    if (argc < 2)	{
		printf("Insufficient arguemnts/n");
		return EXIT_SUCCESS;
	}
	FILE* fp = fopen(argv[1], "r");
   
    if (fp == 0 ){
        printf("error");
        return EXIT_SUCCESS;
    }
    int n;
    fscanf(fp, "%d\n", &n);
    int**matrix = malloc(n*sizeof(int*));
    for (int i = 0; i < n; i ++){
        matrix[i] = malloc(n*sizeof(int));
        for (int j =0; j < n; j++){
            fscanf(fp, "%d", &matrix[i][j]);
        }
        fscanf(fp, "\n");
    }

    int result = 0;
    if (n == 1 || n == 2) result = det(matrix, n);
    else result = rec(matrix, n);

    freen(matrix, n, 0);
    free(matrix);
    printf("%d\n", result);
	return EXIT_SUCCESS;
}
int det (int** mat, int n){ // finds determinant
    if (n == 1) return mat[0][0];
    return mat[0][0]*mat[1][1] - mat[0][1]*mat[1][0];
}
void freen(int** matrix, int n, int count){
    if(count == n)  return;
    free(matrix[count]);
    freen(matrix, n, ++count);
}
int creat(int** matrix, int** mat, int n, int c, int row, int col, int x, int y){
    if(col >= n-1) return 0;
    if(row >= n-1) return 1;
    //if (col == 0) y = c;
    if(col == 0 && c!=0) y =0;
    if (y == c) y++;
    mat[row][col] = matrix[x][y];
    if(creat(matrix, mat, n, c, row, ++col, x, ++y) ==1) return 1;
    return creat(matrix, mat, n, c, ++row, 0, ++x, c);

}
int rec (int** matrix, int n){ //  finds determinant via recursion
    if (n==2)  return det(matrix, n);
    int result =0, a = 1; int** mat = malloc(n*sizeof(int*));
    for (int j = 0; j < n; j ++)  mat[j] = malloc(n*sizeof(int));
    for (int i  = 0; i < n; i++){
        creat(matrix, mat, n, i, 0, 0, 1, i); // creates submatrix
        result = result+ rec(mat, n-1)*a*matrix[0][i];
        a *= -1;
    }
    freen(mat, n, 0);
    free(mat);
    return result;
}
