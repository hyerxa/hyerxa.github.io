/*13.8 Friendly Numbers
Determines all of the friendly numbers between 1 and 1 million. Friendly numbers are numbers such that sum of the first adds up to the second and vice versa
11/1/17
Haley Yerxa*/

#include <iostream>
using namespace std;

void factor(int num, int &nfactors, int* factors); //Passes the original number by value, the number of factors by reference so that it can be changed for each number, and passes the array of numbers by pointer to change the values in the array.
int sum(int nfactors, int factors[]); // Takes the number of factors and the array of factors.

int main()
{
    int length1, length2; //Variables used to store the number of factors each word has
    int factors1[300];
    int factors2[300];
    for (int i=1; i<1000000; i++)
    {
        factor(i,length1,factors1); // Finds all the factors of i(number in loop, starts at 1 and goes up)

        int sumnum1 = sum(length1,factors1); // sumnum1 is the sum of the factors of the first number

        int num2 = sumnum1; // The second number is the sum of the first number (to be friendly, the second number must be equal to the sum of the first number, so there are no other possibilities)

        factor(num2, length2, factors2); // finds the factors of the second number

        int sumnum2 = sum(length2, factors2); // sumnum2 is the the sum of the second number

        if (sumnum1 == i || sumnum1<i) continue; // This prevents it from printing a perfect number (the factors of the first number will add up to itself, so we don't ant it to print it as a friendly pair)
        // It will also prevent it from printing a pair twice, so it only prints the pair if the first number is smaller.

        else if (sumnum2 == i) //If the sum of the factors of the second number is equal to the first number, then they are friendly numbers (we already know that the sum of the factors of the first number is the second number)
        {
            cout<<i<<" and "<<num2<<" are friendly numbers."<<endl;
        }
    }
    return 0;
}

void factor(int num, int &nfactors, int* factors)
{
    nfactors = 0; // When we created the variable for the length, it was given a random value, so we pass by reference so that we can set it to zero in the function.
    for (int i=(num/2); i>0; i--) //Goes through all of the numbers half or less (a factor will never be larger than half the number)
    {
        if (num%i == 0)
       {
           factors[nfactors] = i;
           nfactors++;
       }
    }
}

int sum(int nfactors, int factors[])//Takes the number of factors and the array of factors that were already calculated (pass by value because these values won't be changed)
{
    int sumfactors = 0; //The sum starts at zero and will be returned as the sum of the factors
    for (int i=0; i<nfactors; i++) //Goes through the length of the array
    {
        sumfactors = sumfactors + factors[i]; //Adds each number in the factor array
    }
    return sumfactors;
}


