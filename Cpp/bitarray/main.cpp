/*
 * main.cpp
 *
 *  Created on: Nov 14, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Informatics
 *      University of Leicester
 */

#include <iostream>
#include "Bits.h"

using std::cout;
using std::endl;

int main() {

	Bits b;
	if (b) {
		cout << "1Something is set (bad)" << endl;
	}
	else {
		cout << "1Nothing is set (good)" << endl;
	}

	b.set(64);
	b.set(32);

	if (b) {
		cout << "2Something is set (good)" << endl;
	}
	else {
		cout << "2Nothing is set (bad)" << endl;
	}
	if (b.size() != 2) {
		cout << "3Wrong size (bad)" << endl;
	}

	if (b.maxset() != 64) {
		cout << "3Wrong max (bad)" << endl;
	}
}

