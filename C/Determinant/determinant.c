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
};
int det (int** mat, int n){ // finds determinant
    if (n == 1) return mat[0][0];
    return mat[0][0]*mat[1][1] - mat[0][1]*mat[1][0];
};
int rec (int** matrix, int n){ //  finds determinant via recursion
    if (n==2)  return det(matrix, n);
    int result =0, a = 1; int** mat = malloc(n*sizeof(int*));
    for (int j = 0; j < n; j ++)  mat[j] = malloc(n*sizeof(int));
    for (int i  = 0; i < n; i++){
        creat(matrix, mat, n, i); // creates submatrix
        result = result+ rec(mat, n-1)*a*matrix[0][i];
        a *= -1;
    }
    freen(mat, n, 0);
    free(mat);
    return result;
};
void freen(int** matrix, int n, int count){
    if(count == n)  return;
    free(matrix[count]);
    freen(matrix, n, ++count);
};
void creat(int** matrix, int** mat, int n, int c){ //; intilaizes submatrixes
    int row =1;
    for (int y =0; y < n-1; y++){
        int col = c;
        if (c!=0) col =0;
        for (int z = 0; z < n-1; z++){  
            if (col == c)   col++;
			mat[y][z] = matrix[row][col];
            //printf("%d ", mat[y][z]);
            col++;
        }
        row++;
    }
};
