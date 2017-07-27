/*
 * Bundle.h
 *
 *  Created on: 20 Oct 2016
 *      Author: np183
 */

#ifndef BUNDLE_H_
#define BUNDLE_H_

// This is a container for storing positive and negative integers.
// The positive and negative numbers are held separately according
// to the order that they were added.
// The amount of space reserved for either positive or negative
// numbers is the same.
// When this space is full no more numbers (of that type) can be added
// until the size of the container is changed.
// Individual functions have further instructions

class Bundle {
 public:
  // Create a container with no space reserved.
  Bundle();

  // Create a container with size space reserved for each
  // type of numbers (positive / negative)
  Bundle(unsigned int size);

  // Copy constructor
  Bundle(const Bundle& other);

  // Move constructor
  Bundle(Bundle&& other);

  // Destructor
  ~Bundle();

  // Increase the amount of space reserved.
  // If the resize tries to reduce the amount of space it
  // has no effect.
  // Resize has no effect on contents
  void resize(unsigned int newsize);

  // Add either positive or a negative number.
  // If there is no space for that type of numbers the function
  // returns false.
  // If the addition is successful it return true.
  bool add(int val);

  // Check if this number exists in the container
  bool find(int val) const;

  void copy(const Bundle& other);

  // How many positive and how many negative numbers are currently stored.
  unsigned int pos_size() const;
  unsigned int neg_size() const;

  // Access individual elements.
  // location 0 refers to the last negative number to be added
  // location neg_size()-1 to the first negative number to be added
  // location neg_size() to the first positive number to be aded
  // location neg_size()+pos_size()-1 to the last positive number to
  // be added.
  //
  // For example the sequence of commands:
  // add -1, add -2, add 1, add -3, add 6 will result in
  // [0] -> -3
  // [1] -> -2
  // [2] -> -1
  // [3] -> 1
  // [4] -> 6
  //
  // If the parameter is out of range behavior of the function is
  // undefined
  int operator[] (unsigned int loc) const;
  int& operator[] (unsigned int loc);

  // Same as above except that access is through updating the integer
  // that is passed by reference.
  // If the location is out of bounds no change is done to the
  // reference.
  // Return value indicates whether the access was in bounds or out of bounds.
  bool get(unsigned int loc, int& val) const;

  // Remove all entries in the container equivalent to the value
  void remove(int val);

  // Assignment operator copy and move
  Bundle& operator=(const Bundle& other);
  Bundle& operator=(Bundle&& other);
 private:
  // TODO: Implement me
  // Add data members
  // You are allowed to add also private member functions (or public).

  int* arr1_;   //negative array
  int* arr2_;   //positive array
  int cnt_neg_; //count of negative array elements
  int cnt_pos_; //count of positive array elements
  int arrsize_; //maximum array size allowed
};

#endif /* BUNDLE_H_ */
