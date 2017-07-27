/*
 * Bits.cpp
 *
 *  Created on: Nov 14, 2015
 *      Author: np183
 *      Module: CO7105
 *      Department of Informatics
 *      University of Leicester
 */

#include "Bits.h"
#include <iostream>

using std::cout;
using std::endl;

typedef unsigned int uint;

Bits::Bits()
{
	bits_ = new unsigned long[bitsinlong];
	size_ = 0;
}

Bits::Bits(const Bits& other)
{
	// implement me
}

Bits::Bits(Bits&& other)
{
	// implement me
}

Bits::~Bits() 
{
	// implement me
}

void Bits::set(unsigned int loc) 
{
	if ((loc % bitsinlong) == 0) {
		bits_[(loc / bitsinlong) - 1] |= 1 << (loc % bitsinlong);
	} else if ((loc % bitsinlong) - 1){
		bits_[loc / bitsinlong] |= 1 << (loc % bitsinlong);
	}
}

void Bits::unset(unsigned int loc) 
{
	// implement me	
}

bool Bits::operator[](uint loc) const 
{
	// implement me
	return false;
}

Bits& Bits::operator=(const Bits& other) 
{
	// implement me
	return *this;
}

Bits& Bits::operator=(Bits&& other) 
{
	// implement me
	return *this;
}

Bits Bits::operator|(const Bits& other) const 
{
	// implement me
	return Bits();
}

Bits Bits::operator&(const Bits& other) const 
{
	// implement me
	return Bits();
}

unsigned int Bits::size() const 
{
	// implement me
	return 0;
}

unsigned int Bits::maxset() const 
{
	// implement me
	return 0;
}

Bits::operator bool() const 
{
	// implement me
	return false;
}

Bits::operator unsigned long() const 
{
	// implement me
	return 0;
}


// DO NOT CHANGE
// DO NOT CHANGE
// DO NOT CHANGE
void Bits::set_(unsigned long* bits, unsigned int size) 
{
	if (bits_) {
		delete [] bits_;
	}
	bits_ = bits;
	size_ = size;
}


unsigned long* Bits::get_(unsigned int& size) 
{
	size = size_;
	return bits_;
}
