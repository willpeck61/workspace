/*
 * Bits.h
 *
 *  Created on: Nov 14, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Informatics
 *      University of Leicester
 */

#ifndef BITS_H_
#define BITS_H_

/*
 * In this classtest you are asked to implement an unbounded bitset.
 * You will use an array of unsigned long int-s (64 bits each) to
 * store multiple Boolean values.
 *
 * I explained in the lecture on streams how bit operators work.
 * You should use the << and >> redirection operators on ulong_one (see
 * comment below) and use the bitwise | and & operators in order
 * to implement some of the functions below.
 *
 * You are obliged to use an array of unsigned longs and one variable
 * to store the size of the array.
 * These are set up in the private section below.
 * You can add additional private variables if you think these are needed.
 * There are also functions that allow access and setting of these private
 * data members. You are not allowed to change those as I will use those
 * for grading.
 *
 * Further comments regarding the various class members that are
 * required are given below.
 * Each member function is annotated with the expected difficulty of
 * implementing it.
 * I sorted the members in what I think is a good order to tackle them.
 * Essentially, building the class gradually and allowing yourself the
 * ability to test what you've done as you go along.
 *
 * The way I would suggest to work is to implement these functions in the
 * order that I included them ***ONE BY ONE***.
 * After every function that you implement, compile the code, and
 * write a small test that checks this function.
 * Once you are satisfied with a function proceed to the next.
 *
 */

constexpr unsigned int sizeoflong = sizeof(long);
constexpr unsigned int bitsinlong = sizeoflong*8;

// IMPORTANT: Be very careful with usage of constants!
// These two are different:
// unsinged long l=1<<63;
// unsinged long l=ulong_one<<63;
// The first is evaluated as int and then converted to long.
// The second is evaluated as long and stays long.
constexpr unsigned long ulong_one = 1;


class Bits {
private:
	// A pointer to an array unsingned longs.
	// Use dynamic memory to store the bits.
	unsigned long* bits_;

	// The lenght of the array above.
	unsigned int size_;
public:
	// Create an empty bit set
	Bits(); // Difficulty 1

	// Set the required bit in the bitset (make true)
	// Notice that the bitset does not behave as
	// an array.
	// So set(0) does not change the bitset
	// Set of a high location will cause to reallocate
	// and make sure that you have space for storing this bit
	void set(unsigned int loc); // Difficulty 3

	// Unset the required bit in the bitset (make false)
	void unset(unsigned int loc); // Difficulty 1

	// Return the value of the loc-s bit
	// Out of range access should return false
	bool operator[](unsigned int loc) const; // Difficulty 2

	// Return true if at least one bit is set
	// in the bitset
	operator bool() const; // Difficulty 1

	// Return the first 64 bits of the bitset as
	// un unsigned long
	operator unsigned long() const; // Difficulty 1

	// Copy constructor.
	Bits(const Bits& other); // Difficulty 2

	// Copy assignment operator
	Bits& operator=(const Bits& other); // Difficulty 2

	// Destructor
	~Bits(); // Difficulty 1

	// Move constructor
	Bits(Bits&& other); // Difficulty 2

	// Move assignment operator
	Bits& operator=(Bits&& other); // Difficulty 2

	// Implement the bitwise or between two bitsets
	Bits operator|(const Bits& other) const; // Difficulty 2

	// Implement the bitwise and between two bitsets
	Bits operator&(const Bits& other) const; // Difficulty 2

	// What is the maximal location for which there
	// is a set bit (true)
	// There is a way to implement this in O(log n)
	// In my implementation the logarithmic algorithm works
	// at the same speed as the simple implementation
	unsigned int maxset() const; // Difficulty 3

	// How many bits are set (true)
	// There is a way to implement this in O(log n)
	// In my implementation the logarithmic algorithm works
	// twice as slow as the simple implementation
	unsigned int size() const; // Difficulty 3

	// These functions I implemented. They are not to be touched!
	void set_(unsigned long*, unsigned int);
	unsigned long* get_(unsigned int&);
};

#endif /* BITS_H_ */
