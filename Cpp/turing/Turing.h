/*
 * Turing.h
 * Created on: 23/11/2016
 *     Author: np183
 * 			   Department of Informatics
 * 			   University of Leicester
 */

#ifndef Turing_H_
#define Turing_H_

//#include <vector>
#include <set>
#include <tuple>
#include <vector>
#include <string>
#include <iosfwd>

/*
 * In this exercise you will do some advanced usage of the STL.
 * You will have to implement a class supporting Turing machines and running them.
 * 
 * If you don't know what are Turing machines, then a good place to
 * start is:
 * https://en.wikipedia.org/wiki/Turing_machine
 * 
 * The main class to implement is called, surprisingly enough
 * Turing. But there are two more classes: State and Rule.
 * State - represents the state of a Turing machine (contents of the
 *         tape (string), location of the reading head (unsigned int),
 *         and the  state of the Turing machine (unsigned int).
 * Rule - represents a transition rule of the Turing machine.
 *        a rule has the source state, read letter, target state,
 *        written letter, and the direction that the reading head
 *        should go in.
 * Most of the boiler plate code for these two classes is already
 * completed.
 * But the more interesting functions were left for you to implement.
 * 
 * Comments about the role of each function are given below.
 */

// Moving directions for rules
enum Direction { LEFT, RIGHT, STAY };

// All data members are public to enable easy getting (no setting
// after constructor will be required).
// Rule is completely impelemented
// You are allowed to add things that you need but do not change existing
// implementation
class Rule {
public:
	// The source state of the transition rule
	unsigned int source_;
	// The read letter by the transition rule ('a'-'z' or ' ')
	char letterR_;
	// The target state of the transition rule
	unsigned int target_;
	// The written letter by the transition rule ('a'-'z')
	char letterW_;
	// The direction of move
	Direction dir_;

	// No need for default constructor
	Rule() = delete;
	// Initialize all the data members with the given parameters
	// Throws invalid_argument exception in case the read letter
	// is not in range or the written letter is not in range
	Rule(unsigned int, char, unsigned int, char, Direction);
	// Copy constructor
	Rule(const Rule& other);
	// Assignment operator
	Rule& operator=(const Rule& other);

	// Lexcographic ordering comparison
	bool operator<(const Rule& other) const;

	// Return a rule composed of the given parameters
	// Throws invalid_argument exception in case the read letter
	// is not in range or the written letter is not in range
	static Rule make_rule(unsigned int, char, unsigned int, char, Direction);
private:
	char check_(char, bool blank=true) const;
};

// All data members are public to enable easy getting and setting
// You have to implement almost all functions.
class State { //configuration
public:
	// Public Data Members!
	// DO NOT CHANGE!
	// DO NOT CHANGE!
	// DO NOT CHANGE!
	std::string tape_;
	unsigned int head_;
	unsigned int state_;

	State() = delete;

	// Construct a state from the given parameters
	// If the string contains chars out of 'a'-'z' you should
	// throw an invalid_argument exception
	// If the location of the reading head is out of range throw
	// an out_of_range exception
	State(const std::string&, unsigned int loc, unsigned int turingstate);

	// Copy constructor 
	State(const State& other);
	// Move constructor (notice the treatment of string!)
	State(State&& other);
	// Copy assignment operator
	State& operator=(const State& other);
	// Move assignment operator (notice the treatment of string!)
	State& operator=(State&& other);

	// Return the letter read by the reading head
	// This coudl be 'a'-'z' or ' ' if the head is at the end of
	// the tape
	char letter() const;

	// Apply the transition rule on the state:
	// If the state, read letter does not match, or the movement
	// is impossible throw an invalid_argument exception
	// Otherwise, write the new letter, move the reading head,
	// and change the state
	void apply(const Rule&);

	// output the state.
	// The reading head location should be printed like (c,s),
	// where c is the letter and s is the state
	// In case the head is at the end of the type it should be
	// (_,s), where s is the state
	// For example, this could be:
	// abcedfg(_,5)
	// or
	// a(b,5)cedfg
	friend std::ostream& operator<<(std::ostream&, const State&);
	static State make_state(const std::string&, unsigned int, unsigned int);
private:
	// TODO: add private things
};


// The main class of the Turing machine
// The alphabet is a subset of 'a'-'z' (but some rules may read the
// blank symbol ' ' at the end of the tape).
// States are unsinged integers
// The initial state is always state 0
// It is fine not to have any transition rules from a state, this
// means that the machine is stuck from that state.
class Turing {
public:
	// Build an empty Turing
	Turing();

	// Copy and move constructors
	// No need to implement
	Turing(const Turing&) = delete;
	Turing(Turing&&) = delete;

	// Destructor
	// Clean
	~Turing();

	// Add a rule to the Turing machine
	// The states and letters mentioned in the rule are
	// automatically added to the machine.
	// Simple add cannot create nondeterminism. If it does throw
	// an invalid_argument exception
	// addNondet could create nondeterministic transitions (but
	// can add also deterministic transitions).
	void add(const Rule&);
	void addNondet(const Rule&);

	// setFinal sets one of the existing states as final.
	// If the state does not exist throw an invalid_argument
	// exception.
	// If the function is called multiple times the last state to
	// be declared as final is the final state
	void setFinal(unsigned int);

	// Retrun the set of states
	const std::set<unsigned int>& states() const;
	// Return the alphabet
	const std::set<char>& alphabet() const;
	// Return the transition rules
	const std::set<Rule>& transition() const;
	// Return the final state
	const unsigned int final() const;

	// Run the Turing machine on the given input
	// If you do not support running nondeterministic Turing
	// machines then throw an logic_error exception
	// The return value should be true if there is a run
	// of the Turing machine that reaches the final state
	// It should be false if all runs terminate in non final
	// states
	// This function could lead to infinite runs
	bool run(const std::string& input) const;

	// No need to implement assignment operators
	Turing& operator=(const Turing&) = delete;
	Turing& operator=(Turing&&) = delete;

	// An iterator allows to run the Turing machine step by step
	// In case that the Turing machine is nondeterministic the
	// iterator should allow some transition of the Turing machine
	class iterator {
	public:

		iterator() = delete;
		// Initialize the iterator with a reference to the
		// Turing machine that is working and the input string
		iterator(const Turing& t, const std::string& input);

		// Copy constructor
		iterator(const iterator& other);

		// Compute a transition of the Turing machine
		// If no transition is possible the iterator should
		// throw a logic_error exception
		iterator& operator++();

		// Post increment (that is the returned iterator
		// should be the state of the iterator prior to the
		// increment).
		// The parameter is not used. It is just to allow the
		// overriding of the ++ name.
		iterator operator++(int);

		// Is it possible to take a transition
		bool last() const;

		// Return the state of the Turing machine
		State operator*() const;

		// Return a pointer to the state of the Turing machine
		const State* operator->() const;
	private:
		// TODO: add private members
	};

	// Return an iterator setting the machine to start in the
	// initial state from the initial location in the string (0)
	// with the contents of the string on the input tape
	iterator begin(const std::string&) const;
private:
	std::vector<std::tuple<unsigned int, char, unsigned int, char, Direction>> ruleset_;
	std::tuple<unsigned int, char, unsigned int, char, Direction> tup_;
	unsigned int src_;
	char lR_;
	unsigned int tgt_;
	char lW_;
	Direction d_;
	unsigned int final_;

};

#endif /* TURING_H_ */


/*
 * Turing.h
 * Created on: 23/11/2016
 *     Author: np183
 * 			   Department of Informatics
 * 			   University of Leicester
 */
