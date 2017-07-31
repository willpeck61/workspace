// ConsoleApplication1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

void operationsByReference(int& a, int& b, int& c) 
{
	a += 2;
	b += 2;
	c += 2;
}

void factorialNonRecursive(int& a) 
{
	unsigned int i, b = 1;
	for (i = a; i != 0; --i) {
		cout << b << endl;
		b = b * i;
		a = b;
	}
}

int main()
{
	int a = 5;
	int b = 2;
	a = a + 1;
	int result = a - b;

	float price;
	int qty;

	string str;

	cout << "Hello World " << result << "\n";

	int x = 3, y = 4, z = 5;

	operationsByReference(x, y, z);

	cout << "x = " << x << " y = " << y << " z = " << z << endl;
	int v = 10;

	factorialNonRecursive(v);

	cout << "v = " << v << endl;
	int * pointer;
	pointer = &v;

	const int * pointer2 = &x;
	cout << &v << " " << pointer << endl;

	*pointer = 1000;
	cout << v << " " << *pointer << endl;

	cout << *pointer << " " << *pointer2 << endl;
	
	*pointer++ = *pointer2++;

	cout << *pointer << " " << *pointer2 << endl;
	cout << &pointer << " " << &pointer2 << endl;

	return EXIT_SUCCESS;
}
