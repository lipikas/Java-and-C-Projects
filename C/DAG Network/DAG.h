#ifndef __DAG_H
#define __DAG_H

typedef struct node{ // node
    char vtx [20]; // vertex index
    int visited;
    int in;
    int out;
    int dist;
    struct edge* edg;
} n;

typedef struct edge{ // LL
    char edgeto[20];
    int w;// weight
    struct edge* next;
} e;

typedef struct stack{
    struct edge* front;
} s;
s* push (n**, s*, int);
int pop (n**, int, s*);
void freen(e*);
e* alloc(char [20], int);
int indexfor(n**, int , char [20]);
e* insert(e*, char [20], int);
n* allocnode(char [20]);
void shortpath(n**, int, int);
void topsort(s*, n**, int, int, e*);
n** sort(n**, int);
int traverse(n**, int);
n** visitedreset(n**, int);
#endif
