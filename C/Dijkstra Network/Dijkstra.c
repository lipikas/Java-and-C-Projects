#include <stdio.h>
#include <stdlib.h>
#include "sixth.h"
#include <string.h>
#include <limits.h>

int main (int argc, char* argv[argc+1]){
    if (argc < 3){
        printf("Insufficient arguments\n");
        return EXIT_SUCCESS;
    }
    FILE* fp = fopen(argv[1], "r");
    if (fp == 0){
        printf("error\n");
        return EXIT_SUCCESS;
    }
    int v; 
    fscanf(fp, "%d \n", &v);
    n** arr = malloc(v*sizeof(n*));
    char d [20];
    for(int i =0; i < v; i++){ 
        fscanf(fp, "%s\n", d);
        arr[i] = allocnode(d);
        e* tp =0; arr[i]->edg= tp;
   }
   sort(&arr, v);
   char a [20]; char b [20]; int y;
   while (fscanf(fp, "%s %s %d\n", a, b, &y)!= EOF){
       int v1 = indexfor(arr, v, a);
       if (arr[v1]->edg == 0) arr[v1]->edg = alloc(b, y);
       else{
           arr[v1]->edg = insert(arr[v1]->edg, b, y);
        }
    }
    fclose(fp);

    FILE* fp2 = fopen(argv[2], "r");
    char z [20];
    while (fscanf(fp2, "%s \n", z)!=EOF){
        int v3 = indexfor(arr, v, z);
        dijkstra(arr, v, v3);
        arr = distreset(arr, v);
        printf("\n");
    }  
    fclose(fp2);
    for (int i = 0; i < v; i++) {freen(arr[i]->edg); free(arr[i]);}
    free(arr);
    return EXIT_SUCCESS;
}

e* alloc(char val [20], int h){
    e* temp = malloc(sizeof(e));
    strcpy(temp->edgeto, val);
    temp->w = h;
	temp->next = 0;
    return temp;
}

n* allocnode(char val [20]){
    n* temp = malloc(sizeof(n));
    strcpy(temp->vtx, val);
	temp->edg= 0;
    temp->visited = 0;
    temp->dist =INT_MAX;
    return temp;
}

int indexfor(n** ptr, int v, char a[20]){
    for (int i = 0; i < v; i++){
        char dynamite [20];
        strcpy(dynamite, ptr[i]->vtx); 
        if (strcmp(dynamite, a) == 0)  return i;
    }
    return -1;
}

void freen(e* root){ // frees nodes
    if (root == 0) return;
    freen(root->next);
    free(root);
    return;
}

e* insert(e* ptr, char v[20], int y){
    e* temp = alloc(v, y);
    char dynamite [20];
    strcpy(dynamite, ptr->edgeto);
    if (strcmp(dynamite, v) >0){ // if temp > curr ptr
        temp->next = ptr;
        return temp;
    }
    e* pt = ptr; e* prev;
    while(pt != 0){
        char bts [20];
        strcpy(bts, pt->edgeto);
        if(strcmp(bts, v) >0){
            temp->next = prev->next;
            prev->next = temp;
            return ptr;
        }
        prev = pt;
        pt = pt->next;
    }
    prev->next = temp;// largest vertex
    return ptr;
}
void push (n**ptr, q* arr, int b, int max) { // adds items and finds min
    if(arr->front == 0){
        arr->front = alloc(ptr[b]->vtx, 0);
        return;
    }
    int i = indexfor(ptr, max, arr->front->edgeto);
    e* temp = alloc(ptr[b]->vtx, 0);
    if (ptr[b]->dist < ptr[i]->dist ){
        temp->next = arr->front;
        arr->front= temp;
        return;
    }
    e* pt = arr->front; e* prev;
    while(pt != 0){
        int v = indexfor(ptr, max, arr->front->edgeto);
        if(ptr[b]->dist < ptr[v]->dist){
            temp->next = prev->next;
            prev->next = temp;
            return;
        }
        prev = pt;
        pt = pt->next;
    }
    prev->next = temp;
    return;
}
int pop (n** ptr, int max, q* arr) {
    e* temp = arr->front;
    int v =indexfor(ptr, max, arr->front->edgeto);
    arr->front = arr->front->next;
    free(temp);
    return v; // return popped index
}
void dijkstra (n**ptr, int max, int src){
    q* list = malloc(sizeof(q)); // queue
    list->front= 0;
    ptr[src]->dist=0; e* t1 = ptr[src]->edg;
    while(t1!=0){ // each neighbor of src, dist = edge w
        int v1 = indexfor(ptr, max, t1->edgeto);
        ptr[v1]->dist = t1->w;
        push(ptr, list, v1, max);
        t1= t1->next;
    }
   while(list->front !=0){ //fringe not empty
        int b = pop(ptr, max, list);// min index
        e* temp = ptr[b]->edg; // adjlist
        while(temp!=0){ // for each neighbor of min
            int v2 = indexfor(ptr, max, temp->edgeto);
             if(ptr[v2]->dist == INT_MAX){
                ptr[v2]->dist = ptr[b]->dist + temp->w;
                push(ptr, list, v2, max);
                merges(ptr, max, &list->front);
            } else if (ptr[v2]->dist > ptr[b]->dist + temp->w) {
                ptr[v2]->dist = ptr[b]->dist + temp->w;
                merges(ptr, max, &list->front);
            }
            temp = temp->next;
        }
    }
    for(int i = 0; i < max; i++){
        if (ptr[i]->dist == INT_MAX)    printf("%s INF\n", ptr[i]->vtx);
        else    printf("%s %d\n", ptr[i]->vtx, ptr[i]->dist);
    }
    free(list);
}

void sort(n*** temp, int max){ // selection sort
    n** arr = *temp;
    for (int i = 0; i < max-1; i++)  {
        int min = i;  
        for (int j = i+1; j < max; j++){  
            if (strcmp(arr[j]->vtx,arr[min]->vtx) < 0)  min= j;  
        }
        n* temp = arr[min];
        arr[min] = arr[i];
        arr[i] = temp;
    } 
    *temp = arr;
}
void merges(n** pt, int max, e** ptr){ //merge sort
    e* temp = *ptr;
    e* var1; e* var2;
    if(temp == 0 || temp->next == 0) return; // base
    split(temp, &var1, &var2);
    merges(pt, max, &var1);
    merges(pt, max, &var2);
    *ptr = merge(pt, max, var1, var2);

}
void split(e* original, e** head, e** tail){ // splits into 2 adj lists
    e* low = original;
    e* high = original->next;
    while(high !=0){
        high = high->next;
        if (high != 0){
        low = low->next;
        high = high->next;}
    }
    *head = original;
    *tail = low->next;
    low->next = 0;
}
e* merge(n** arr, int max, e* p1, e* p2){ //merges 2 lists
    e* ptr = 0;
    if (p1 ==0) return p2;
    if (p2 ==0) return p1;
    int a = indexfor(arr, max, p1->edgeto);
    int b = indexfor(arr, max, p2->edgeto);
    if (b >= a){
        ptr = p1;
        ptr->next = merge(arr, max, p1->next, p2);
    }
    else{
        ptr = p2;
        ptr->next = merge(arr, max, p1, p2->next);
    }
    return ptr;
}

q* traverse(q* ptr, char val [20], int d){
    e* temp = ptr->front;
    while(temp!= 0){
        if(strcmp(temp->edgeto, val) ==0) {temp->w = d; return ptr;}
        temp = temp->next;
    }
    return ptr;
}
n** distreset(n** arr, int max){
    n** temp = arr;
    for (int i = 0; i < max; i++){
        temp[i]->visited = 0;
        temp[i]->dist = INT_MAX;
    }
    return arr;
}
