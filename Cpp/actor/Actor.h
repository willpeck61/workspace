/*
 * Actor.h
 *
 *  Created on: Nov 7, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Computer Science
 *      University of Leicester
 */

#ifndef ACTOR_H_
#define ACTOR_H_

#include <string>
#include "Container.h"

// The idea of this exercise is to create a more complex class hierarchy
// and at the same time include some more involved reasoning about
// what the classes actually do.
//
// You are asked to implemented the following hierarchy:
//
//                         Actor
//                           |
//           ---------------------------------
//           |                               |
//         Masked                           Binary
//           |                               |
//           |                        ------------------
//   -------------------------        |                |
//   |            |          |        |                |
//  Unary       ToUC        ToLC    Sequence         Interleave
//   |
//  Shorten
//
// Even though Unary has only one object inheriting from it
// you should try to think about them as the base class of many
// possible derived classes and try to push as much of the implementation
// of common features for all Unary functions into them.
//
// All types of Actors support:
// name - a pretty print into string of the name of the functions (details below)
// toString - print the structure of the entire actor
// act - apply the action to a given input
//
// The actions are by manipulating c-strings.
// In order to help you with your impelementation I created a
// dynamic container of c-strings that you can (must) use
// for getting the input to the act function and for returning the
// (modified) input.
//
// The explanations regarding the different types of actors are given
// below.
//
// Some points will be awarded for NOT implementing all functions at the leaf
// level. In particular, this holds for toString.
// You might have to add additional functions and think carefully about what
// private variables are needed and where to put them.
//
// All the following are your role:
// 1. Decide which functions should be virtual.
// 2. Decide which functions should be pure virtual.
// 3. Decide which classes should be abstract classes.
// 4. Decide where to implement the required functions.
//
// Notice that all pointers that are passed on to you (for operands of
// functions that you are building) are dynamic memory and become your
// responsibility once they are sent to the constructor.

// This is the base class of the entire hierarchy

class Actor {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	Actor(); // required for compilation of EMPTY version. REMOVE IF YOU DONT NEED
	Actor(const Actor&) = delete;
	Actor(Actor&&) = delete;

	// Destructor
	~Actor();

	// Act on the given input.
	//
	// Individual explanations to what act should do are given
	// before each class for which this is defined.
	virtual Container act(const Container& input) const = 0;

	// Return a string representing the Actor.
	// This should start with the name of the function and be followed
	// by it's operands.
	// For example:
	// A ToUC will be return ToUC(X)
	// A ToLC will be return ToLC(X)
	// A Sequence will return Sequence(sub1,sub2)
	// If subexpressions don't exist just remove them
	// So if a Sequence has just one subexpression it should
	// return Sequence(sub1). If it has none it should return Sequence(X).
	// Similarly for Sequence and Shorten
	std::string toString() const;

	// Return the body of the name of the Actor
	// A ToUC will return ToUC
	// A Sequence will return Sequence
	// and so on.
	std::string name() const;

	// No need to implement assignment operator
	Actor& operator=(const Actor& other) = delete;
	Actor& operator=(Actor&& other) = delete;

protected:

private:

};

// Every masked Actor applies an operation using
// an array of Boolean values that is used as a mask.
// When the masked object has to operate on a sequence
// it will consult the mask on whether to apply its
// operation to the input.
// If the mask is too short it should be used again
// (and again) to extend it's lengh.
// For example, if the mask is ttft and it should operate
// on an input of length 10 then the mask applied will
// be ttftttfttt - that is
// use the whole mask twice and then the first two elements in
// the mask.
//
// Notice that the mask can be empty.
// So an empty mask and a mask of ffffff create
// the same effect.
//
// The mask can be used to act on the characters in the words
// in the container (ToUC, ToLC) or on the entire words (Shorten).
// For ToUC and ToLC a t in the mask means to change the character
// (to upper case or to lower case) and a t in the mask means not to
// change it.
// For Shorten a t in the mask means to keep the entire word and a f in the
// mask means to drop the entire word.
// This is explained further below.
class Masked : public Actor {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	Masked(const Masked&) = delete;
	Masked(Masked&&) = delete;

	Masked(); // required for compilation of EMPTY version. REMOVE IF YOU DONT NEED

	~Masked();

	// A masked object returns the size
	// of the mask and allows access to the
	// bits of the mask using array notation
	unsigned int size() const;
	bool operator[](unsigned int) const;


	// No need to implement assignment operator
	Masked& operator=(const Masked& other) = delete;
	Masked& operator=(Masked&& other) = delete;
protected:
  bool* mask_;
  int size_;
private:

};

// To Lower Case
//
// The action of a lower case is to take each
// word in the container that it gets as input
// and treat that word according to the mask.
//
// For example, if the input Container contains two
// words:
// "aBsEsFFF" and "asdSDLKJslksdj"
// and the mask is ttfft then the result of ToLC's action
// would be the container with the two words:
// "absEsffF" and "asdSdlkJslksdj"
class ToLC : public Masked {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	ToLC(const ToLC&) = delete;
	ToLC(ToLC&&) = delete;

	ToLC() = delete;
	ToLC(bool* mask, unsigned int size);
	Container act(const Container& input) const;
	~ToLC();

	// No need to implement assignment operator
	ToLC& operator=(const ToLC& other) = delete;
	ToLC& operator=(ToLC&& other) = delete;
};

// To Upper Case
//
// The action of an upper case is to take each
// word in the container that it gets as input
// and treat that word according to the mask.
//
// For example, if the input Container contains two
// words:
// "aBsEsFFF" and "asdSDLKJslksdj"
// and the mask is ttfft then the result of ToLC's action
// would be the container with the two words:
// "ABsESFFF" and "ASdSDLKJsLKSdj"
class ToUC : public Masked {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	ToUC(const ToUC&) = delete;
	ToUC(ToUC&&) = delete;

	ToUC() = delete;
	ToUC(bool* mask, unsigned int size);
  Container act(const Container& input) const;
	~ToUC();

	// No need to implement assignment operator
	ToUC& operator=(const ToUC& other) = delete;
	ToUC& operator=(ToUC&& other) = delete;
};

// A Unary operator potentially accepts another
// Actor as part of its construction.
// It gets the Actor that it should operate on
// as a dynamic memory that it claims as its own.
//
// The action of an Unary operator starts by calling
// calling the Actor operand on the input and then
// applying the Unary operator's own action.
// So for example, if a Unary operator has a ToLC as
// it's operand, it will first apply the ToLC on the
// input and then do whatever its own action is on the
// result of that.
class Unary : public Masked {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	Unary(const Unary&) = delete;
	Unary(Unary&&) = delete;

	Unary(Actor* op); // required for compilation of EMPTY version. REMOVE IF YOU DONT NEED
	~Unary();
	Actor* act(const Actor& input) const;

	// No need to implement assignment operator
	Unary& operator=(const Unary& other) = delete;
	Unary& operator=(Unary&& other) = delete;
protected:
	Actor* op_;
private:

};

// Shorten is a Unary operator
//
// It does not change individual words but rather
// chooses some of the words in the container and drops
// others (using the mask).
// So for example, if the input container contains
// the words:
// "asdlkfj", "qoweiur", "xc,.vm|", "xcoixclkje", "239sadlij390"
// and the mask is ft
// then the result of shortening this Container would be
// "qoweiur", "xcoixclkje"
// If the internal operation of the Shorten is ToUC with its
// own mask of t then the result would be:
// "QOWEIUR", "XCOIXCLKJE"
//bool opflag_ = true;
// If there is no internal operation (i.e., the constructor
// gets a nullptr) then the Shorten acts directly on the input.
class Shorten : public Unary {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	Shorten(const Shorten&) = delete;
	Shorten(Shorten&&) = delete;
  Container act(const Container& input) const;
	Shorten() = delete;
	Shorten(bool* mask, unsigned int size, Actor* op);
	~Shorten();

	// No need to implement assignment operator
	Shorten& operator=(const Shorten& other) = delete;
	Shorten& operator=(Shorten&& other) = delete;
private:
};


// A Binary operator potentially accepts two other
// Actors as part of its construction.
// It gets the Actors that it should operate on
// as a dynamic memory that it claims as its own.
//
// The action of an Binary operator starts by calling
// the Actor operands on the input (both on same input)
// and then applying the Binary operator's own action.
// So for example, if a Binary operator has a ToLC as one
// operand and Shorten as another operand, it will apply the
// ToLC on the input and separately apply the Shorten on
// the input and then do whatever is its own action
// on the results of the two.
class Binary : public Actor {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	Binary(const Binary&) = delete;
	Binary(Binary&&) = delete;

	Binary(Actor* op1, Actor* op2); // required for compilation of EMPTY version. REMOVE IF YOU DONT NEED
	~Binary();

	// A Binary allows access to its operands
	Actor* op1() const;
	Actor* op2() const;

	// No need to implement assignment operator
	Binary& operator=(const Binary& other) = delete;
	Binary& operator=(Binary&& other) = delete;
protected:
	Actor* op_;
	Actor* op2_;
private:

};

// Interleave is a Binary operator
//
// It does not change individual words but rather
// combines the inputs (in the case it has two operands)
// by interleaving the words form the result
// of each of its operands' actions.
// If it has only one operand it just mirrors
// the result of that operand's action.
// If it has no operands it mirrors the input.
// So for example, if the input container contains
// the words:
// "asDlKfj", "qOweIur", "xC,.Vm|", "xcoIXclKJe", "239sadlij390"
// and the two operands are ToLC (with mask t) and ToUC (with mask t)
// then the result of interleaving would be
// "asdlkfj", "ASDLKFJ", "qoweiur", "QOWEIUR", "xc,.vm|",
// "XC,.VM|", "xcoixclkje", "XCOIXCLKJE", "239sadlij390", "239SADLIJ390"
//
// If one container contains more words than the other then
// interleave as much as possible and at the end add all
// the words from the longer one.
// So for example:
// Interleave(ToUC(X),Shorten(ToLC(X))) with input
// "asDlKfj", "qOweIur", "xC,.Vm|", "xcoIXclKJe", "239sadlij390"
// Where the mask for the LC and UC are t and the mask for the
// Shoretn is tf will output
// Output of the ToUC:
// "ASDLKFJ", "QOWEIUR", "XC,.VM|", "XCOIXCLKJE", "239SADLIJ390"
// Output of the ToLC:
// "asdlkfj", "qoweiur", "xc,.vm|", "xcoixclkje", "239sadlij390"
// Output of the Shorten:
// "asdlkfj", "xc,.vm|", "239sadlij390"
// Output of the interleave:
// "ASDLKFJ", "asdlkfj", "QOWEIUR", "xc,.vm|", "XC,.VM|", "239sadlij390" "XCOIXCLKJE",
// "239SADLIJ390"
class Interleave : public Binary {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	Interleave(const Interleave&) = delete;
	Interleave(Interleave&&) = delete;
	Container act(const Container& input) const;
	Interleave() = delete;
	Interleave(Actor* op1, Actor* op2);
	~Interleave();

	// No need to implement assignment operator
	Interleave& operator=(const Interleave& other) = delete;
	Interleave& operator=(Interleave&& other) = delete;
private:
};

// Sequence is a Binary operator
//
// It does not change individual words but rather
// combines the inputs (in the case it has two operands)
// by taking the all the words from the first operand's
// action and then taking all the words from the second
// operand's action.
// If it has only one operand it just mirrors
// the result of that operand's action.
// If it has no operands it mirrors the input.
// So for example, if the input container contains
// the words:
// "asDlKfj", "qOweIur", "xC,.Vm|", "xcoIXclKJe", "239sadlij390"
// and the two operands are ToLC (with mask t) and ToUC (with mask t)
// then the result of interleaving would be
// "asdlkfj", "qoweiur", "xc,.vm|", "xcoixclkje", "239sadlij390"
// "ASDLKFJ", "QOWEIUR", "XC,.VM|", "XCOIXCLKJE", "239SADLIJ390"
class Sequence : public Binary {
public:
	// No need to implement copy, or move constructors
	// Create a constructor that matches your needs
	// Add specialized constructors as you need them
	Sequence(const Sequence&) = delete;
	Sequence(Sequence&&) = delete;
	Container act(const Container& input) const;
	Sequence() = delete;
	Sequence(Actor* op1, Actor* op2);
	~Sequence();


	// No need to implement assignment operator
	Sequence& operator=(const Sequence& other) = delete;
	Sequence& operator=(Sequence&& other) = delete;
private:
};

#endif /* ACTOR_H_ */


/*
 * Actor.h
 *
 *  Created on: Nov 27, 2015
 *      Author: np183
 *      Module: CO7104
 *      Department of Computer Science
 *      University of Leicester
 */

