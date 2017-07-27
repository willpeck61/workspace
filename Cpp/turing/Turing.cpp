/*
 * Turing.cpp
 * Created on: 23/11/2016
 *     Author: np183
 * 			   Department of Informatics
 * 			   University of Leicester
 */

#include <exception>
#include <iostream>
#include <tuple>
#include "Turing.h"

//using std::vector;
using std::set;
using std::ostream;
using std::string;

typedef unsigned int uint;

const string ALPHABET("abcdefghijklmnopqrstuvwxyz");
//constexpr char MINWLETTER = 'a';
//constexpr char MINRLETTER = ('a' < ' ' ? 'a' : ' ');

Rule::Rule(uint source, char letterR,
		uint target, char letterW, Direction d)
: source_(source), letterR_(check_(letterR)),
  target_(target), letterW_(check_(letterW,false)),
  dir_(d)
{}

Rule::Rule(const Rule& other)
: source_(other.source_), letterR_(other.letterR_),
  target_(other.target_), letterW_(other.letterW_), dir_(other.dir_)
{}

Rule& Rule::operator =(const Rule& other)
{
	source_ = other.source_;
	letterR_ = other.letterR_;
	target_ = other.target_;
	letterW_ = other.letterW_;
	dir_ = other.dir_;
	return *this;
}

bool Rule::operator<(const Rule& other) const
{
	return std::tie(source_,letterR_,target_,letterW_, dir_) <
			std::tie(other.source_,other.letterR_,other.target_,
					other.letterW_,other.dir_);
}

Rule Rule::make_rule(uint s, char lR, uint t, char lW, Direction d)
{
	return Rule(s,lR,t,lW,d);
}

char Rule::check_(char c,bool blank) const
{
	if (ALPHABET.find(c) != string::npos ||
			(' ' == c && blank)) {
		return c;
	} else {
		throw std::invalid_argument("letter is not valid");
	}
}

State::State(const string& tape, uint head, uint state)
{
	bool valid = true;
	for (char c : tape) {
		if (!(ALPHABET.find(c))) {
			throw std::invalid_argument("string contains illegal chars");
			valid = false;
		}
	}
	if (!(tape[head])) {
		throw std::out_of_range("out of range");
	} else {
		head_ = head;
		state_ = state;
		tape_ = tape;
	}
}

State::State(const State& other)
{
	state_ = other.state_;
	head_ = other.head_;
	tape_ = other.tape_;
}

State::State(State&& other)
{
	other.head_ = head_;
	other.state_ = state_;
	other.tape_.assign(tape_, 0, tape_.length());
	tape_.clear();
	delete this;
}

State& State::operator=(const State& other)
{
	head_ = other.head_;
	state_ = other.state_;
	tape_ = other.tape_;
	return *this;
}

State& State::operator=(State&& other)
{
	unsigned int h = other.head_;
	unsigned int s = other.state_;
	tape_.assign(other.tape_, 0, other.tape_.length());
	other.tape_.clear();
	head_ = h;
	state_ = s;
	return *this;
}

char State::letter() const
{
	// TODO: implement me
	return tape_[head_];
}

void State::apply(const Rule& r)
{
	int pos = head_ + r.source_;
	if (tape_[pos] == r.letterR_) {
		if (r.dir_ == RIGHT) {
			pos = head_ + r.target_;
		} else {
			pos = head_ - r.target_;
		}
		tape_[pos] = r.letterW_;
	} else {
		throw std::invalid_argument("letter did not match");
	}

}


ostream& operator<<(ostream& o, const State& s)
{
	// TODO: implement me
	return o;
}

State State::make_state(const string& t, uint h, uint s)
{
	return State(t,h,s);
}

Turing::Turing()
{
	src_ = 0;
	lR_ = 0;
	tgt_ = 0;
	lW_ = 0;
	d_ = Direction();
	final_ = 0;
}

Turing::~Turing()
{

}

//return Rule(s,lR,t,lW,d);
//source, read letter, target, write letter, direction

void Turing::add(const Rule& r)
{
	src_ = r.source_;
	lR_ = r.letterR_;
	tgt_ = r.target_;
	lW_ = r.target_;
	d_ = r.dir_;
	tup_ = std::make_tuple(src_, lR_, tgt_, lW_, d_);
	ruleset_.emplace_back(tup_);
}

void Turing::addNondet(const Rule& r)
{
	// TODO: implement me
}

void Turing::setFinal(uint f)
{
	final_ = f;
}

const set<uint>& Turing::states() const
{
	// TODO: implement me
	set<uint> remove_me;
	return remove_me;
}

const set<char>& Turing::alphabet() const
{
	// TODO: implement me
	set<char> remove_me;
	return remove_me;
}

const set<Rule>& Turing::transition() const
{
	// TODO: implement me
	set<Rule> remove_me;
	return remove_me;
}

const uint Turing::final() const
{
	// TODO: implement me
	return 0;
}

bool Turing::run(const string& input) const {
	// TODO: implement me
	return true;
}

Turing::iterator::iterator(const Turing& t, const string& input)
{
	// TODO: implement me
}

Turing::iterator::iterator(const iterator& other)
{
	// TODO: implement me
}

Turing::iterator& Turing::iterator::operator++()
		{
	// TODO: implement me
	return *this;
		}

Turing::iterator Turing::iterator::operator++(int)
		{
	// TODO: implement me
	return *this;
		}

bool Turing::iterator::last() const
{
	// TODO: implement me
	return false;
}

State Turing::iterator::operator*() const
{
	// TODO: implement me
	State remove_me("starttapecontent",0,0);
	return remove_me;
}

const State* Turing::iterator::operator->() const
{
	// TODO: implement me
	return nullptr;
}

Turing::iterator Turing::begin(const string& input) const
{
	// This could be the final implementation
	return iterator(*this,input);
}

/*
 * Turing.cpp
 * Created on: 23/11/2016
 *     Author: np183
 * 			   Department of Informatics
 * 			   University of Leicester
 */
