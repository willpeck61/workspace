/*
 * Bundle.cpp
 *
 *  Created on: 20 Oct 2016
 *      Author: np183
 */

#include "Bundle.h"
#include <iostream>

typedef unsigned int uint;

using std::cout;
using std::endl;

Bundle::Bundle() 
{
  arr1_ = new int[0];
  arr2_ = new int[0];
  cnt_neg_ = 0;
  cnt_pos_ = 0;
  arrsize_ = 0;
}

Bundle::Bundle(uint size)
{
	if (size > arrsize_) {
		arr1_ = new int[size];
		arr2_ = new int[size];
	  cnt_neg_ = 0;
	  cnt_pos_ = 0;
		arrsize_ = size;
	}
}

Bundle::Bundle(const Bundle& other)
{
  copy(other);
}

Bundle::Bundle(Bundle&& other) : arr1_{other.arr1_},
    arr2_{other.arr2_}, arrsize_{other.arrsize_},
    cnt_neg_{other.cnt_neg_}, cnt_pos_{other.cnt_pos_}
{
  other.arr1_ = nullptr;
  other.arr2_ = nullptr;
	other.cnt_neg_ = 0;
	other.cnt_pos_ = 0;
	other.arrsize_ = 0;
}

Bundle::~Bundle()
{
	delete [] arr1_;
	delete [] arr2_;
}

void Bundle::resize(unsigned int newsize)
{
	if (newsize > arrsize_) {
		arr1_ = new int[newsize];
		arr2_ = new int[newsize];
		arrsize_ = newsize;
	}
}


bool Bundle::add(int val)
{
	if (val < 0 && cnt_neg_ < arrsize_) {
	  arr1_[arrsize_ - cnt_neg_ - 1] = val;
    cnt_neg_++;
		return true;
	} else if (val >= 0 && cnt_pos_ < arrsize_) {
		arr2_[cnt_pos_] = val;
    cnt_pos_++;
		return true;
	} else {
	  return false;
	}
}

bool Bundle::find(int val) const
{
  bool flag = false;
  if (val < 0){
    for (int i = arrsize_; i >= 0; i--){
      if (arr1_[i] == val) {
        flag = true;
      }
    }
  } else {
    for (int i = 0; i < arrsize_; i++){
      if (arr2_[i] == val){
        flag = true;
      }
    }
  }
  return flag;
}

uint Bundle::pos_size() const
{
	return cnt_pos_;
}

uint Bundle::neg_size() const
{
	return cnt_neg_;
}

int Bundle::operator[] (uint loc) const
{
  if (loc < neg_size()) {
    return arr1_[loc + (arrsize_ - neg_size())];
  } else {
    return arr2_[loc - neg_size()];
  }
}

int& Bundle::operator[] (uint loc)
{
  if (loc < neg_size()) {
    return arr1_[loc + (arrsize_ - neg_size())];
  } else {
    return arr2_[loc - neg_size()];
  }
}

bool Bundle::get(uint loc, int& val) const
{
  if (loc < cnt_neg_) {
    val = arr1_[loc];
    return true;
  } else if (loc > cnt_neg_ &&
    loc < (cnt_neg_ + cnt_pos_)){
    val = arr2_[loc];
    return true;
  } else {
    return false;
  }
}

void Bundle::remove(int val)
{
  if (val < 0){
    for (int i = cnt_pos_ + cnt_neg_; i >= 0; i--){
      if (arr1_[i] == val) {
        for (int k = 0; k < i; k++) {
          if (arr1_[i] < 0){
            arr1_[i - k] = arr1_[i - k - 1];
          }
        }
        cnt_neg_--;
      }
    }
  } else {
	  for (int i = 0; i < cnt_pos_; i++){
		  if (arr2_[i] == val){
			  for (int k = 0; k < pos_size(); k++){
				  arr2_[i + k] = arr2_[i + k + 1];
			  }
	      cnt_pos_--;
		  }
	  }
  }
}

void Bundle::copy(const Bundle& other) {
  arrsize_ = other.arrsize_;
  cnt_neg_ = other.cnt_neg_;
  cnt_pos_ = other.cnt_pos_;
  arr1_ = new int[arrsize_];
  arr2_ = new int[arrsize_];
  for (int i = 0; i < arrsize_; i++) {
    arr1_[(arrsize_ - cnt_neg_ - 1) + i] =
            other.arr1_[(arrsize_ - cnt_neg_ - 1) + i];
    arr2_[i] = other.arr2_[i];
  }
}

Bundle& Bundle::operator=(const Bundle& other)
{
  if (!(this == &other)) {
    if (arr1_ || arr2_) {
      delete [] arr1_;
      delete [] arr2_;
      copy(other);
    }
  }
	return *this;
}

Bundle& Bundle::operator=(Bundle&& other)
{
  if (!(this == &other)) {
    int* temparr1 = new int[arrsize_];
    int* temparr2 = new int[arrsize_];
    other.arr1_ = temparr1;
    other.arr2_ = temparr2;

    int temp_cnt_neg{other.cnt_neg_};
    cnt_neg_ = other.cnt_neg_;
    other.cnt_neg_ = temp_cnt_neg;

    int temp_cnt_pos{other.cnt_pos_};
    cnt_pos_ = other.cnt_pos_;
    other.cnt_pos_ = temp_cnt_pos;

  }
  return *this;
}
