#ifndef __SIMULATION_H
#define __SIMULATION_H

typedef struct Node{
    int valid;
    unsigned long long tag;
    unsigned long long int address; // storing address to convert from L1 to L2 or vice versa during eviction
    int time;
}n;
int timer = 0; unsigned long long int address2 = 0; // prev address
unsigned long L1hit = 0; unsigned long L1miss = 0; unsigned long memread = 0; unsigned long memwrite = 0;
unsigned long L2hit = 0; unsigned long L2miss = 0;
unsigned long long int address11; // original address

int check (n**, int, unsigned long long int, unsigned long long, char [], char, int, int); // checks if hit or miss
int power(unsigned long);
int read(n**, unsigned long long int, unsigned long long, int, int, char[20], char, int, int, int);
void freen(n**, int, int);

#endif
