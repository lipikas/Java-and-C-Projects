#ifndef __DIJKSTRA_H
#define __DIJKSTRA_H

typedef struct node{ // node
    char vtx [20]; // vertex index
    int dist;
    int visited;
    struct edge* edg;
} n;

typedef struct edge{ // LL
    char edgeto[20];
    int w;// weight
    struct edge* next;
} e;

typedef struct queue{
    struct edge* front;
} q;

void push (n**, q*, int, int);
int pop (n**, int, q*);
void freen(e*);
e* alloc(char [20], int);
int indexfor(n**, int , char [20]);
e* insert(e*, char [20], int);
n* allocnode(char [20]);
void dijkstra (n**, int, int);
void sort(n***, int);
q* traverse(q*, char [20], int);
n** distreset(n**, int);
void merges(n**, int, e**);
void split(e*, e**, e** );
e* merge(n**, int, e*, e*);
#endif
