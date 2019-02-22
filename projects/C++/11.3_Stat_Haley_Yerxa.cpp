/* 11.3 Stat() function
Takes 2 numbers and calculates the sum, difference, and average of the numbers
11/1/17
Haley Yerxa
*/

#include <iostream>
using namespace std;

void stat(float n1, float n2, float &sum, float &diff, float &avg)
//Passes two numbers by value (they don't change), and passes random values of sum, diff, and avg that have no set value by reference so they can be changed
{
    sum = n1 + n2;
    diff = n1-n2;
    avg = (n1 + n2)/2;

    return;
}

int main()
{
    float number1, number2, s, d, a; // creates five numbers, two to be inputed and the other three to be used for the sum, diff, and avg (doesn't matter what their value is because the function will change them)
    cout<<"Enter a number: ";
    cin>>number1;
    cout<<"Enter another number: ";
    cin>>number2;
    stat(number1, number2, s, d, a);
    cout<<"The sum is "<<s<<endl<<"The difference is "<<d<<endl<<"The average is "<<a;

    return 0;
}
