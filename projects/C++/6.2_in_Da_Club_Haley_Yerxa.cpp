/*6.2 In Da Club
Sees if a name is in the list of names
09/27/17
Haley Yerxa */

#include <iostream>
using namespace std;
int main()
{
    char nameslist[10][20] = {"jacob", "michael", "matthew", "joshua", "christopher", "emily", "hannah", "madison", "ashley", "sarah"}; // All names are lower case so that making it case sensitive is easier
    char name[100];        // Creates array for inputed name (100 characters is realistic that it won't be longer)
    bool good = false;     // Flag used to see if name is in the list or not
    cout<<"Please enter your name: ";
    cin>>name;

        for (int i=0; i<10 && good == false; i++)       // Loop goes through list of names (as long as the flag doesn't become true which would break it)
        {
            for (int j=0; nameslist[i][j] != '\0' && name[j] != '\0'; j++)     // Inner loop goes through each name such that neither the given name or the name in the list is at the terminator (end of name)
            {
                if (nameslist[i][j] != tolower(name[j])) break;        // As soon as a letter in a name isn't equal it breaks the inner loop and goes to the next word
                else
                {
                    if (nameslist[i][j+1] == '\0' && name[j+1] == '\0')   // Otherwise, the letters are the same and if the next character for both of them is the terminator, they are equal
                    {
                        cout<<"Welcome "<<name<<" to our exclusive club!";
                        good = true;      // Setting this flag to true will break the outer loop
                        break;
                    }
                }
            }
        }
    if (good == false)   // If it exits the loops and the flag is still false, the names aren't the same
    {
        cout<<"Sorry "<<name<<", you are not on the list.";
    }
    return 0;

    }
