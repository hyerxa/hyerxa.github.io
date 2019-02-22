/* 24.1 Animal and Human OOP
Animal Super class and two class that inherit from it: Human and Dog
11/21/17
Haley Yerxa */

#include <iostream>
using namespace std;

class Animal // Super class
{
    protected:        //attributes for all classes that inherit from it
        char name[30];
        char sex;
        int happiness;
        int health;

    public:

        Animal() //basic constructor, will construct for all animals even in subclasses
        {
            cout<<"An animal is born!"<<endl;
        }

        Animal(char* n, char s) // constructs with name and sex
        {
            setName(n);
            setSex(s);
        }

        ~Animal() // Basic destructor
        {
            cout<<"The animal is dead :("<<endl;
        }

        void setSex(char s)
        {
            if (s == 'm' || s == 'f')
                sex = s;
            else cout <<"unidentified gender";

            return;
        }

        void setName(char *pn)
        {
            int length = 0;
            for (int i=0; pn[i] != '\0'; i++)
            {
                name[i] = pn[i];
                length++;
            }
            name[length] = '\0';
            return;
        }

        void setHappiness(int h)
        {
            happiness = h;
            return;
        }

        void setHealth(int s)
        {
            health = s;
            return;
        }

        char* getName()
        {
            return name;
        }

        int getHappiness()
        {
            return happiness;
        }

        int getHealth()
        {
            return health;
        }

        char getSex()
        {
            return sex;
        }

};

class Dog; // prototype so that interactions between human and dog class can be recognized

class Human: public Animal // Human inherits from Animal
{
    private: // no attributes other than ones inherited from super class

    public:

        Human(char* n, char s) // Constructs with name and sex
        {
            setName(n);
            setSex(s);
            cout<<name<<": Hello"<<endl;
        }

        ~Human()
        {
            cout<<name<<": Goodbye."<<endl;
        }

        // Basic methods
        void eat()
        {
            cout<<name<<": "<<"Food at LAST!"<<endl;
            return;
        }

        void greet()
        {
            cout<<name<<": "<<"Hey, how you doin'?"<<endl;
            return;
        }

        void sleep()
        {
            cout<<name<<": "<<"Look! A butterfly ... ZZZZZzzzzzzzzz"<<endl;
            return;
        }

        void sayname()
        {
            cout<<name<<": "<<"My name is "<<name<<endl;
            return;
        }

        void cheer()
        {
            cout<<name<<": "<<"Woo hoo!"<<endl;
            return;
        }

        void complain()
        {
            cout<<name<<": "<<"Aww man..."<<endl;
            return;
        }

        void giveGift(Human &receiver) // one human gives a gift to another
        {
            cout<<name<<": Here, have this gift"<<endl; // person calling the function gives the gift
            cout<<receiver.getName()<<": Thank you so much!"<<endl; // argument is receiving the gift
            happiness = happiness + 5; // gifter increases their happiness
            receiver.happiness = receiver.happiness + 5; // receiver increases their happiness
            return;
        }

        void checkHappiness()
        {
            if (happiness>8) cheer();
            else if (happiness<5) complain();
            return;
        }

        void operator + (Human &spouse) // Adds two humans together (marries them)
        {
            happiness = happiness + 3;
            spouse.happiness = spouse.happiness + 3;
            cout<<"Congratulations, "<<name<<" and "<<spouse.getName()<<", you are now married!"<<endl;
            return;
        }

        void operator - (Human &ex) // Subtracts two humans (divorces them)
        {
            happiness = happiness - 3;
            ex.happiness = ex.happiness - 3;
            cout<<"Unfortunately, "<<name<<" and "<<ex.getName()<<", you are divorced!"<<endl;
            return;
        }

        void feedDog(Dog &pet); // prototype for method between the two classes
        void walkDog(Dog &pet);

};

class Dog: public Animal // dog extends from animal
{
    private: // no other attributes other than the ones inherited

    public:

        Dog(char* n, char s) // constructs with name and sex
        {
            setName(n);
            setSex(s);
            cout<<name<<": Bork bork."<<endl;
        }

        ~Dog()
        {
            cout<<name<<": Woof woof, see you later!"<<endl;
        }

        // basic class methods
        void speak()
        {
            cout<<name<<": Woof woof"<<endl;
        }

        void growl()
        {
            cout<<name<<": Grrrrrr"<<endl;
        }

        void sleep()
        {
            cout<<name<<": Zzzzzzzz"<<endl;
            happiness=happiness+2;
            health++;
            return;
        }

        void operator * (Dog &playmate) // * operator gets two dogs to play
        {
            cout<<name<<": woof woof <wags tail> let's play!"<<endl;
            cout<<playmate.getName()<<": Yay! <pounces>"<<endl;
            happiness++;
            playmate.happiness++; // both of their happiness increases
            return;
        }

        void eat()
        {
            health++;
            happiness=happiness+2;
            return;
        }

        void checkHappiness()
        {
            if (happiness>8) speak();
            else if (happiness<2) growl();
            return;
        }

        void lickHuman(Human &owner); // prototype for method that involves both classes
};

void Human::feedDog(Dog &pet) // we made a prototype for this in the human class, it is a human method that takes a dog as an argument
//Passing by reference allows us to change the dog
{
    cout<<name<<": Are you hungry? Do you want some food?"<<endl;
    pet.eat();
    return;
}

void Human::walkDog(Dog &pet)
{
    cout<<name<<": Do you want to go for a walk?"<<endl;
    pet.speak();
    cout<<name<<": Let's go!"<<endl;
    pet.setHappiness(pet.getHappiness()+3);
}

void Dog::lickHuman(Human &owner) // method for dog class, takes human argument
{
    speak();
    cout<<owner.getName()<<": Kisses! I love you too "<<name<<". What a good boy!"<<endl;
    owner.setHappiness(owner.getHappiness() + 4); // both of them increase in happiness
    happiness = happiness + 4;
    return;
}
int main()
{
    // Build our humans
    Human guy("Joey", 'm');
    Human girl("Sarah", 'f');
    // Set their happiness
    guy.setHappiness(5);
    girl.setHappiness(5);
    //Print their happiness when we do something that might affect it, so we can see the change
    cout<<guy.getName()<<"'s happiness is "<<guy.getHappiness()<<endl;
    cout<<girl.getName()<<"'s happiness is "<<girl.getHappiness()<<endl;

    guy+girl; // marries them
    cout<<guy.getName()<<"'s happiness is "<<guy.getHappiness()<<endl;
    cout<<girl.getName()<<"'s happiness is "<<girl.getHappiness()<<endl;

    guy.giveGift(girl); // guy is giving a gift to the girl
    cout<<guy.getName()<<"'s happiness is "<<guy.getHappiness()<<endl;
    cout<<girl.getName()<<"'s happiness is "<<girl.getHappiness()<<endl;

    guy-girl; // they get divorced
    cout<<guy.getName()<<"'s happiness is "<<guy.getHappiness()<<endl;
    cout<<girl.getName()<<"'s happiness is "<<girl.getHappiness()<<endl;

    guy.eat();
    girl.sleep();
    guy.sayname();

    //create our dogs
    Dog puppy("Pal", 'm');
    Dog puppy2("Sadie", 'f');
    puppy.setHappiness(6);
    puppy2.setHappiness(6);

    puppy*puppy2; // get them to play
    cout<<puppy.getName()<<"'s happiness is "<<puppy.getHappiness()<<endl;
    cout<<puppy2.getName()<<"'s happiness is "<<puppy2.getHappiness()<<endl;

    puppy.speak();

    girl.feedDog(puppy); // girl feeds the dog
    cout<<puppy.getName()<<"'s happiness is "<<puppy.getHappiness()<<endl;
    cout<<girl.getName()<<"'s happiness is "<<girl.getHappiness()<<endl;

    puppy.lickHuman(girl); // dog then licks the girl (gross!)
    cout<<puppy.getName()<<"'s happiness is "<<puppy.getHappiness()<<endl;
    cout<<girl.getName()<<"'s happiness is "<<girl.getHappiness()<<endl;

    guy.walkDog(puppy2);
    cout<<puppy2.getName()<<"'s happiness is "<<puppy2.getHappiness()<<endl;

    girl.checkHappiness();
    puppy.checkHappiness();
    return 0;
}

