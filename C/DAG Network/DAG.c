#include <stdio.h>
#include <stdlib.h>
#include "dag.h"
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
   int v; fscanf(fp, "%d \n", &v);
   n** arr = malloc(v*sizeof(n*));
   char d [20];
   for(int i =0; i < v; i++){
       fscanf(fp, "%s\n", d);
       arr[i] = allocnode(d);
  }
  arr = sort(arr, v);
  char a [20], b [20]; int y;
  while (fscanf(fp, "%s %s %d\n", a, b, &y)!= EOF){ // insert weights & edges
       int v1 = indexfor(arr, v, a);
       int v2 = indexfor(arr, v, b);
       if (arr[v1]->edg == 0) arr[v1]->edg = alloc(b, y);
       else{
           arr[v1]->edg = insert(arr[v1]->edg, b, y);
       }
       arr[v1]->out +=1;
       arr[v2]->in += 1;
   }
   fclose(fp);
   int count = traverse(arr, v);
   if(count ==1){ // cycle checker
       for (int i = 0; i < v; i++) {freen(arr[i]->edg); free(arr[i]);}
       free(arr);
       printf("\nCYCLE\n");
       return EXIT_SUCCESS;
   }
   FILE* fp2 = fopen(argv[2], "r");
   char z [20];
   while (fscanf(fp2, "%s \n", z)!=EOF){ // runs topsort
        int v3 = indexfor(arr, v, z);
        printf("\n");
        shortpath(arr, v, v3);
        for(int i = 0; i < v; i++){ //prints dist
           if (arr[i]->dist == INT_MAX)    printf("%s INF\n", arr[i]->vtx);
           else    printf("%s %d\n", arr[i]->vtx, arr[i]->dist);
        }
        arr = visitedreset(arr, v);
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
   temp->dist =INT_MAX;
   temp->visited = 0;
   temp->in = 0;
   temp->out = 0;
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
 
e* insert(e* ptr, char v[20], int y){// insert in sorted order
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
s* push (n** ptr, s* arr, int b) { // adds to front
   if (arr->front == 0) { arr->front = alloc(ptr[b]->vtx, 0); return arr; }
   e* temp = arr->front;
   arr->front = alloc(ptr[b]->vtx, 0);
   arr->front->next = temp;
   return arr;
}
int pop (n** ptr, int max, s* arr) {// pops front
   e* temp = arr->front;
   int v =indexfor(ptr, max, arr->front->edgeto);
   arr->front = arr->front->next;
   free(temp);
   return v;
}
void shortpath(n** ptr, int max, int src){ //finds shortest path via stacks
   s* list = malloc(sizeof(s));
   list->front=0;
   ptr[src]->dist=0;
   for(int i = 0; i < max; i++){ // sorts if unvisited
       if(ptr[i]->visited == 0) topsort(list, ptr, i, max, ptr[i]->edg);
   }

   while(list->front !=0){ // while stack !=empty
       int b = pop(ptr, max, list);
       e* temp = ptr[b]->edg;
       if(ptr[b]->dist!=INT_MAX){
       while(temp!=0){// updates dist of adj list
           int v2 = indexfor(ptr, max, temp->edgeto);
           if(ptr[v2]->dist > (ptr[b]->dist + temp->w))  ptr[v2]->dist = ptr[b]->dist + temp->w;
           temp = temp->next;
       }
       }
   }
   free(list);
}
void topsort(s* list, n** ptr, int b, int max, e* temp){ // DFS sort
   ptr[b]->visited = 1;
   if (temp ==0) return;
   int i = indexfor(ptr, max, temp->edgeto);
   if (ptr[i]->visited ==0){
       topsort(list, ptr, i, max, ptr[i]->edg);
   }
   topsort(list, ptr, i, max, temp->next);
   push(ptr, list, b);// insert current vtx
}
/*void topsort(s* list, n** ptr, int b, int max, e* temp){ // DFS sort
   ptr[b]->visited = 1;
   if (temp ==0) return;
   while(temp !=0){
       int i = indexfor(ptr, max, temp->edgeto);
       if (ptr[i]->visited ==0) topsort(list, ptr, i, max, ptr[i]->edg);
       temp = temp->next;
   }
   push(ptr, list, b);// insert current vtx
}*/
 
n** sort(n** arr, int max){ // selection sort to find min
   for (int i = 0; i < max-1; i++)  {
       int min = i; 
       for (int j = i+1; j < max; j++){ 
           if (strcmp(arr[j]->vtx,arr[min]->vtx) < 0)  min= j; 
       }
       n* temp = arr[min];
       arr[min] = arr[i];
       arr[i] = temp;
   }
   return arr;
}
 
int traverse(n** ptr, int max){// checks for cycles
   int sum = 1;
   for (int i = 0; i < max; i++){     
       if(ptr[i]->out >=1 ){
           int v1 = indexfor(ptr, max,ptr[i]->edg->edgeto);
           e* temp = ptr[v1]->edg;
           while(temp!=0){
               if(strcmp(temp->edgeto, ptr[i]->vtx) ==0) return 1;
               temp = temp->next;
           }
       }
       if(ptr[i]->in ==0 || ptr[i]->out == 0) sum -=1;
   }
   return sum; // 1 - has cycle; >=0 - not cycle
}
n** visitedreset(n** arr, int max){
   n** temp = arr;
   for (int i = 0; i < max; i++){
       temp[i]->dist = INT_MAX;
       temp[i]->visited = 0;
   }
   return arr;
}
***
