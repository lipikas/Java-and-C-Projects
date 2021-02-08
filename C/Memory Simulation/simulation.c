#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include "second.h"
int main(int argc, char* argv[argc+1]){
  if (argc != 9){
  printf("error\n");
  return EXIT_SUCCESS;
  }

  // reading L1 & L2 cachsize, associativity, cache replacement policy, blocksize, cache type
  unsigned long long L1cachsize = atoi(argv[1]);
  char L1asso [20]; char L1policy [20];
  strcpy (L1asso,argv[2]);
  strcpy (L1policy,argv[3]);
  long blocksize = atoi(argv[4]);
  unsigned long long L2cachsize = atoi(argv[5]);
  char L2asso [20]; char L2policy [20];
  strcpy (L2asso,argv[6]);
  strcpy (L2policy,argv[7]);
  
  FILE* fp = fopen(argv[8], "r");
  if (fp ==0 || power(L1cachsize) ==0 || power(blocksize) ==0 || power(L2cachsize) ==0){ //checks if it's power of 2
      printf("error\n");
      return EXIT_SUCCESS;
  }

  int L1set = 0, L2set = 0, L1block = 0, L2block = 0; // seting no of sets & blocks based on cache type

  if (strcmp(L1asso, "direct")==0) { L1block = 1; L1set = L1cachsize/blocksize;} // reading assoscitiivty of caches
  else if (strcmp(L1asso, "assoc")==0) { L1set = 1; L1block = L1cachsize/blocksize;}
  else if (L1asso[5] == ':'){
      char* b = &L1asso[strlen(L1asso)-1];
      char v = *b; L1block = v - '0';
      L1set = L1cachsize/ (blocksize* L1block);
      if (power(L1block)==0){ printf("error\n"); return EXIT_SUCCESS;}
    }
  else { printf("error\n"); return EXIT_SUCCESS; }
  
  if (strcmp(L2asso, "direct")==0) { L2block = 1; L2set = L2cachsize/blocksize;}
  else if (strcmp(L2asso, "assoc")==0) { L2set = 1; L2block = L2cachsize/blocksize;}
  else if (L2asso[5] == ':'){
  char* b = &L2asso[strlen(L2asso)-1];
  char v = *b; L2block = v - '0';
  L2set = L2cachsize/ (blocksize* L2block);
  if (power(L2block)==0){ printf("error\n"); return EXIT_SUCCESS;}
  }
  else { printf("error\n"); return EXIT_SUCCESS; }

  n** L1 = malloc(L1set*sizeof(n*));
  for(int i =0; i < L1set; i++){  // allocates space & intialization for L1 cache
        L1[i] = malloc(L1block*sizeof(n));
        for (int j = 0; j < L1block; j++){
            L1[i][j].tag = 0;
            L1[i][j].valid = 0;
            L1[i][j].time = 0;
            L1[i][j].address = 0;
        }
    }
    
    n** L2 = malloc(L2set*sizeof(n*));
    for(int i =0; i < L2set; i++){  // allocates space & intialization for L2 cache
        L2[i] = malloc(L2block*sizeof(n));
        for (int j = 0; j < L2block; j++){
            L2[i][j].tag = 0;
            L2[i][j].valid = 0;
            L2[i][j].time = 0;
            L2[i][j].address = 0;
        }
    }

  char num; unsigned long long int address1;
  unsigned long long L1setbit = log(L1set)/log(2);
  unsigned long long L2setbit = log(L2set)/log(2);
  unsigned long long offset = log(blocksize)/log(2);// is blockbits

  while(fscanf(fp, "%c %llx\n", &num, &address1) != EOF){
      unsigned long long L1tag = address1 >> (offset + L1setbit); // sets tag
      unsigned long long L1power = pow(2, L1setbit) -1;
      unsigned long long int L1index = (address1 >> offset) & L1power; //  finds index of set
      unsigned long long L2tag = address1 >> (offset + L2setbit); // sets tag
      unsigned long long L2power = pow(2, L2setbit) -1;
      unsigned long long int L2index = (address1 >> offset) & L2power; //  finds index of set
      int a = check(L1, L1block, L1index, L1tag, L1policy, num, 0, 0);// hit check
      int b = check(L2, L2block, L2index, L2tag, L2policy, num, 0, 1);
      address11 = address1; // contains original address

      if(a == 1) L1hit++;// L1 hit
      else if (b == 1){ // L2 hit
          L2hit++; L1miss++;
          L1tag = address2 >> (offset + L1setbit);
          L1index = (address2 >> offset) & L1power;

          if (read(L1, L1index, L1tag, L1block, 0, L1policy, num, 0, L1[L1index][0].time, 0) == 1){// evicts blocks from L1 and replaced with L2 address
              L2tag = address2 >> (offset + L2setbit);
              L2index = (address2 >> offset) & L2power;
              int g = read(L2, L2index, L2tag, L2block, 0, L2policy, num, 0, L2[L2index][0].time, 0); // evicts blocks from L2 and replaced with prev L1 address
              g++;
          }
      }
      else if (a == 0 && b == 0){ // misses in L1 & L2
          L1miss++; L2miss++; memread++; 
          if(num == 'W') memwrite++;
          if (read(L1, L1index, L1tag, L1block, 0, L1policy, num, 0, L1[L1index][0].time, 1) == 1){// if full, evicts block from L1 and replaced with original address
              L2tag = address2 >> (offset + L2setbit);
              L2index = (address2 >> offset) & L2power;
              int g = read(L2, L2index, L2tag, L2block, 0, L2policy, num, 0, L2[L2index][0].time, 0); // evicts block from L2 and replaced with prev L1 address
              g ++;
            }
        }
   }

  printf("memread:%lu", memread);
  printf("\nmemwrite:%lu", memwrite);
  printf("\nl1cachehit:%lu", L1hit);
  printf("\nl1cachemiss:%lu", L1miss);
  printf("\nl2cachehit:%lu", L2hit);
  printf("\nl2cachemiss:%lu", L2miss);
  
  freen(L1, L1set, 0); free(L1); // frees L1 & L2
  freen(L2, L2set, 0); free(L2);
  fclose(fp);
  return EXIT_SUCCESS;
}

void freen(n** temp, int set, int count){ // frees cache & blocks
 if(count == set)  return;
 free(temp[count]);
 freen(temp, set, ++count);
}

int power(unsigned long num){ // checks if num is power of 2
  if(num == 0) return 0;
  while(num != 1){
      if(num%2 != 0) return 0;
      num = num/2;
  }
  return 1;
}

int check (n** temp, int block, unsigned long long int index, unsigned long long tag1, char policy [20], char num, int count, int id){
 if (count == block) return 0; // it's a miss
 if (temp[index][count].valid == 1 && temp[index][count].tag == tag1) { // hit
     address2 = temp[index][count].address;
     if(id == 1){// if it's L2
         temp[index][count].tag = 0; 
         temp[index][count].valid = 0;
     }
     if(num == 'W') memwrite++;
     if (strcmp(policy, "lru") == 0) { temp[index][count].time = timer++; }
     return 1; // means it's a hit
 }
 return check (temp, block, index, tag1, policy, num, ++count, id);
}

int read (n** temp, unsigned long long int index, unsigned long long tag1, int block,  int count, char policy [20], char num, int track, int min, int id){
  if (count == block){ // miss & set is full - needs block replacement
     temp[index][track].time = timer++; // track is min index
     temp[index][track].tag = tag1;
     unsigned long long int ad = temp[index][track].address;
     if (id == 0) temp[index][track].address= address2;
     else temp[index][track].address= address11;
     address2 = ad;
     return 1;// set is full
    }
  if (temp[index][count].valid == 0) { // miss & at least one block is empty
     temp[index][count].time = timer++; // track is min index
     temp[index][count].tag = tag1;
     temp[index][count].valid = 1;
     if(id == 0){
        temp[index][count].address= address2;
     }
     else{
        temp[index][count].address= address11; 
     }
     address2 = temp[index][count].address;
     return 0;// set is empty
   }
 if (count >= 1 && min > temp[index][count].time) { // occurs if valid is 1 & tags don't match
     track = count; min = temp[index][count].time;
    }
 return read (temp, index, tag1, block, ++count, policy, num, track, min, id);
}
// multilevel cache works only if u have same blocksize
