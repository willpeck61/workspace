/*
 * Actor.cpp
 *
 *  Created on: Nov 7, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Computer Science
 *      University of Leicester
 */

#include "Actor.h"
#include <iostream>

using std::string;
using std::cout;
using std::endl;

Actor::Actor() {

}

Actor::~Actor() {
}

Masked::Masked() {
	mask_ = nullptr;
	size_ = 0;
}

Masked::~Masked() {
}

unsigned int Masked::size() const {
	return size_;
}

bool Masked::operator[](unsigned int loc) const {
	if (!(mask_)) {
		return false;
	}
	if (size_ <= loc) {
		return mask_[loc % size_];
	} else {
		return mask_[loc];
	}
}

Container ToLC::act(const Container& input) const {
	if (size_ == 0) {
		return input;
	} else {
		Container tempcont;
		char* tempstr = input.first();
		while (tempstr) {
			for (int i = 0; tempstr[i] != '\0'; i++) {
				if (operator[](i) == true) {
					tempstr[i] = tolower(tempstr[i]);
				}
			}
			tempcont.add(tempstr);
			tempstr = input.next();
		}
		return tempcont;
	}
}

ToLC::ToLC(bool* mask, unsigned int size) {
	mask_ = mask;
	size_ = size;
}

ToLC::~ToLC() {
}

Container ToUC::act(const Container& input) const {
	if (size_ == 0) {
		return input;
	} else {
		Container tempcont;
		char* tempstr = input.first();
		while (tempstr) {
			for (int i = 0; tempstr[i] != '\0'; i++) {
				if (operator[](i) == true) {
					tempstr[i] = toupper(tempstr[i]);
				}
			}
			tempcont.add(tempstr);
			tempstr = input.next();
		}
		return tempcont;
	}
}

ToUC::ToUC(bool* mask, unsigned int size) {
	mask_ = mask;
	size_ = size;
}

ToUC::~ToUC() {
}

Unary::Unary(Actor* op) {
	op_ = op;
}

Unary::~Unary() {
}

Container Shorten::act(const Container& input) const {

	Container in = input;
	if ((op_) && size_ == 0) {
		return in;
	} else {
		char* tempstr = in.first();
		Container tempcont;
		if (op_) {
			Container opcont;
			opcont = op_->act(in);
			tempstr = opcont.first();
			int z = 0;
			while (tempstr) {
				if (operator[](z) == true) {
					tempcont.add(tempstr);
				}
				tempstr = opcont.next();
				z++;
			}
			return tempcont;
		} else {
			int z = 0;
			while (tempstr) {
				if (operator[](z) == true) {
					tempcont.add(tempstr);
				}
				tempstr = in.next();
				z++;
			}
			return tempcont;
		}
	}
}

Shorten::Shorten(bool* mask, unsigned int size, Actor* op) : Unary(op) {
	mask_ = mask;
	size_ = size;
}

Shorten::~Shorten() {
}

Binary::Binary(Actor* op1, Actor* op2) {
	op_ = op1;
	op2_ = op2;
}

Binary::~Binary() {
	delete op_;
	delete op2_;
}

Actor* Binary::op1() const {
	return op_;
}

Actor* Binary::op2() const {
	return op2_;
}

Container Interleave::act(const Container& input) const {
	Container tempcont;
	if (op_ || op2_) {
		Container opcont1;
		Container opcont2;
		if (op_ && !(op2_)) {
			opcont1 = op_->act(input);
			opcont2 = input;
		} else if (!(op_) && op2_) {
			opcont1 = input;
			opcont2 = op2_->act(input);
		} else {
			opcont1 = op_->act(input);
			opcont2 = op2_->act(input);
		}
		char* tempstr = opcont1.first();
		char* tempstr2 = opcont2.first();
		while (tempstr) {
			tempcont.add(tempstr);
			tempcont.add(tempstr2);
			tempstr = opcont1.next();
			tempstr2 = opcont2.next();
		}
	} else {
		return input;
	}
	return tempcont;
}

Interleave::Interleave(Actor* op1, Actor* op2) : Binary(op1, op2) {

}

Interleave::~Interleave() {
}

Container Sequence::act(const Container& input) const {
	Container in = input;
	Container tempcont;

	Container opcont1;
	Container opcont2;
	char* tempstr;
	char* tempstr2;
	if (op_ && !(op2_)) {
		opcont1 = op_->act(in);
		tempstr = opcont1.first();
		while (tempstr) {
			tempcont.add(tempstr);
			tempstr = opcont1.next();
		}
		return tempcont;
	} else if (!(op_) && op2_) {
		opcont2 = op2_->act(in);
		tempstr2 = opcont2.first();
		while (tempstr2) {
			tempcont.add(tempstr2);
			tempstr2 = opcont2.next();
		}
		return tempcont;

	} else if (!(op_) && !(op2_)) {
		return in;
	} else {
		opcont1 = op_->act(in);
		tempstr = opcont1.first();
		opcont2 = op2_->act(in);
		tempstr2 = opcont2.first();
		while (tempstr) {
			tempcont.add(tempstr);
			tempstr = opcont1.next();
		}
		while (tempstr2) {
			tempcont.add(tempstr2);
			tempstr2 = opcont2.next();
		}
		return tempcont;
	}

}

Sequence::Sequence(Actor* op1, Actor* op2) : Binary(op1, op2) {

}

Sequence::~Sequence() {
}

/*
 * Actor.cpp
 *
 *  Created on: Nov 7, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Computer Science
 *      University of Leicester
 */

