/*
 * TwoTypes.h
 *
 *  Created on: 22 Nov 2016
 *      Author: np183
 *      		Department of Informatics
 *      		University of Leicester
 */

#ifndef TWOTYPES_H_
#define TWOTYPES_H_

#include <iosfwd>
#include <vector>
#include <stdexcept>
#include <tuple>
#include <typeinfo>
#include <string>
#include <cstring>
#include <iostream>
#include <sstream>
#include <type_traits>

/*
 * In this exercise you will use templates to implement a container
 * that supports two types of objects inside it.
 *
 * In addition you should use the STL data structures for storing
 * information. This should help you implement this class.
 *
 * The container accepts objects of the two types and stores them. 
 * It can then tell you which type of object was stored in which
 * location in the container and using this knowledge actually
 * retrieve the item that was stored there.
 * 
 * It is assumed that the types sent to the container have copy
 * constructors and assignment operators implemented for them.
 * So it is OK to store the values of the information sent to you.
 *
 * It may be a good idea to start from an implementation of concrete
 * two types (e.g., int and double) and after completing this
 * extending this to a template implementation.
 *
 * Comments about the role of each function are given below.
 */

enum Types {
	TYPE1, TYPE2
};

template<typename T1, typename T2>
class TwoTypes {
public:

	// Default constructor.
	// Initializes an empty container
	TwoTypes();

	// Initializes the container to be ready to accept at size elements
	// withtout the need for reallocation
	TwoTypes(unsigned int size);

	// No need to implement copy or move constructors
	TwoTypes(const TwoTypes& other) = delete;
	TwoTypes(TwoTypes&& other) = delete;

	// Clean
	~TwoTypes();

	// Add an object of the specified type
	void push_back(const T1&);
	void push_back(const T2&);

	std::string reCast(T1 other);
	std::string reCast(T2 other);
	// Access the object at location loc
	// If the access is out of range throw an out_of_range exception
	// If the object in that location does not match the requested type
	// throw an invalid_argument exception
	const T1& operator()(unsigned int loc, T1) const;
	const T2& operator()(unsigned int loc, T2) const;
	T1& operator()(unsigned int loc, T1);
	T2& operator()(unsigned int loc, T2);

	// Return the type of the object in the location
	Types type(unsigned int loc) const;

	// Set the object in location loc to the given object
	// The location has to be already included in the container
	// (otherwise throw an out_of_range exception).
	// The object in that location before the call does not have
	// to match the type of the new object
	void set(unsigned int loc, const T1&);
	void set(unsigned int loc, const T2&);

	// Return how many objects are in the container
	unsigned int size() const;

	// Return if the container is empty
	bool empty() const;

	// Erase all objects in the container
	void clear();

	// output the container to the ostream
	// separate every two objects in the container by the fill value
	// that is set for the ostream.
	// You can assume that operator<< is implemented for the objects in
	// the container.
	template<typename U1, typename U2>
	friend std::ostream& operator<<(std::ostream&, const TwoTypes<U1, U2>&);

	// No need to implement assignment operators
	TwoTypes& operator=(const TwoTypes& other) = delete;
	TwoTypes& operator=(TwoTypes&& other) = delete;

private:
	//This implementation uses a vector of tuples as 
	//the container. 
	//	tuple<0> - T1 value 0 if null
	//	tuple<1> - T2 value 0 if null
	//	tuple<2> - bool true = T2  false = T1
	//	tuple<3> - bool true = entry input after ctor
	//		   	false = entry created by ctor
	std::vector<std::tuple<T1, T2, bool, bool>> cont;

	std::string t1type; //T1 Type
	std::string t2type; //T2 Type
	bool setSize;	    //Has container size been set	
	bool isNumericT1;   //Is T1 numeric type
	bool isNumericT2;   //Is T2 numeric type
	T1 valt1;	    //Current T1 value	
	T2 valt2;	    //Current T2 value
	T1 nullt1;	    //Empty T1 to represent null
	T2 nullt2;	    //Empty T2 to represent null
};

template<typename T1, typename T2>
TwoTypes<T1, T2>::TwoTypes() {
	t1type.clear();
	t2type.clear();
	t1type = typeid(T1).name();
	t2type = typeid(T2).name();
	setSize = false;
	isNumericT1 = false;
	isNumericT2 = false;
	if (std::is_arithmetic<T1>::value) {
		isNumericT1 = true;
	}
	if (std::is_arithmetic<T2>::value) {
		isNumericT2 = true;
	}
	cont.clear();
	if (cont.size() > 0) {
		cont.resize(0);
	}
};

template<typename T1, typename T2>
TwoTypes<T1, T2>::TwoTypes(unsigned int size) {
	t1type.clear();
	t2type.clear();
	t1type = typeid(T1).name();
	t2type = typeid(T2).name();
	setSize = true;
	isNumericT1 = false;
	isNumericT2 = false;

	//create empty T1 based on general type
	if (std::is_array<T1>::value) { nullt1 = {}; }
	else if (std::is_arithmetic<T1>::value) {
		isNumericT1 = true;
		nullt1 = {};
	}

	//create empty T2 based on general type
	if (std::is_array<T2>::value) { nullt2 = {}; }
	else if (std::is_arithmetic<T2>::value) {
		isNumericT2 = true;
		nullt2 = {};
	}

	//insert empty tuples into container to required size
	for (int i = 0; i < size; i++) {
		cont.emplace_back(std::make_tuple(nullt1, nullt2, false, false));
	}
};

template<typename T1, typename T2>
TwoTypes<T1, T2>::~TwoTypes() {
	t1type.clear();
	t2type.clear();
	cont.clear();
	cont.resize(0);
};


template<typename T1, typename T2>
void TwoTypes<T1, T2>::push_back(const T1& v) {
	//check for existing entries
	if (cont.size() > 0) {
		if (t1type.compare(typeid(v).name()) == 0) {
			//check that container has been initialised by
			//size ctor.
			if (setSize) {
				for (int i = 0; i < cont.size(); ++i) {
					if (std::get<3>(cont[i]) == false) {

						//replace empty tuple
						cont.at(i) = std::make_tuple(v, nullt2, false, true);
						break;
					}
				}
			} else {
				//no size ctor but existing elements
				//add new tuple to container
				cont.emplace_back(std::make_tuple(v, nullt2, false, true));
			}
		}
	} else {
		//no existing elements and no ctor size initialisation
		//add new tuple, set new type for T1
		std::string t1type = typeid(v).name();
		cont.emplace_back(std::make_tuple(v, nullt2, false, true));
	}
};


template<typename T1, typename T2>
void TwoTypes<T1, T2>::push_back(const T2& v) {
	//check for existing entries
	if (cont.size() > 0) {
		//Get T2 type and check value matches
		if (t2type.compare(typeid(v).name()) == 0) {
			//check that container has been initialised by
			//size ctor.
			if (setSize) {
				for (int i = 0; i < cont.size(); ++i) {
					if (std::get<3>(cont[i]) == false) {

						//replace 1st empty tuple where flag is false
						cont.at(i) = std::make_tuple(nullt1, v, true, true);
						break;
					}
				}
			} else {
				//no size ctor but existing elements
				//add new tuple to container
				cont.emplace_back(std::make_tuple(nullt1, v, true, true));
			}
		}
	} else {
		//no existing elements and no ctor size initialisation
		//add new tuple, set new type for T1
		std::string t2type = typeid(v).name();
		cont.emplace_back(std::make_tuple(nullt1, v, true, true));
	}
};

template<typename T1, typename T2>
const T1& TwoTypes<T1, T2>::operator()(unsigned int loc, T1) const {
	if (loc > (cont.size() - 1) || cont.size() == 0) {
		throw std::out_of_range("out of range");
	} else {
		//get the tuple at location.
		auto tup = cont[loc];

		//get value type
		std::string t = typeid(T1).name();

		valt1 = std::get < 0 > (tup);
		valt2 = std::get < 1 > (tup);

		std::string v;
		//which type is the location requested
		bool t1ort2 = std::get < 2 > (tup);
		if (t1ort2 == true) {
			v = typeid(std::get < 1 > (tup)).name();
		} else {
			v = typeid(std::get < 0 > (tup)).name();
		}

		//does value type match location
		if (t.compare(v) == 0) {
			return std::get < 0 > (tup);
		} else {
			throw std::invalid_argument("Does not match requested type.");
		}
	}
};

template<typename T1, typename T2>
const T2& TwoTypes<T1, T2>::operator()(unsigned int loc, T2) const {
	if (loc > (cont.size() - 1) || cont.size() == 0) {
		throw std::out_of_range("out of range");
	} else {
		//get the tuple at location.
		auto tup = cont[loc];

		//get value type
		std::string t = typeid(T2).name();

		valt1 = std::get < 0 > (tup);
		valt2 = std::get < 1 > (tup);

		std::string v;
		//which type is the location requested
		bool t1ort2 = std::get < 2 > (tup);
		if (t1ort2 == false) {
			v = typeid(std::get < 0 > (tup)).name();
		} else {
			v = typeid(std::get < 1 > (tup)).name();
		}

		//does value type match location
		if (t.compare(v) == 0) {
			return std::get < 1 > (tup);
		} else {
			throw std::invalid_argument("Does not match requested type.");
		}
	}
};



template<typename T1, typename T2>
T1& TwoTypes<T1, T2>::operator()(unsigned int loc, T1 other) {
	if (loc > (cont.size() - 1) || cont.size() == 0) {
		throw std::out_of_range("out of range");
	} else {
		//get the tuple at location.
		auto tup = cont[loc];

		//get value type
		std::string t = typeid(other).name();

		valt1 = std::get < 0 > (tup);
		valt2 = std::get < 1 > (tup);

		std::string v;
		//which type is the location requested
		bool t1ort2 = std::get < 2 > (tup);
		if (t1ort2 == true) {
			v = typeid(std::get < 1 > (tup)).name();
		} else {
			v = typeid(std::get < 0 > (tup)).name();
		}

		//does value type match location
		if (t.compare(v) == 0) {
			return std::get < 0 > (cont[loc]);
		} else {
			throw std::invalid_argument("Does not match requested type.");
		}
	}
};

template<typename T1, typename T2>
T2& TwoTypes<T1, T2>::operator()(unsigned int loc, T2) {
	if (loc > (cont.size() - 1) || cont.size() == 0) {
		throw std::out_of_range("out of range");
	} else {
		//get the tuple at location.
		auto tup = cont[loc];

		//get value type
		std::string t = typeid(T2).name();
		valt1 = std::get < 0 > (tup);
		valt2 = std::get < 1 > (tup);

		std::string v;
		//which type is the location requested
		bool t1ort2 = std::get < 2 > (tup);
		if (t1ort2 == false) {
			v = typeid(std::get < 0 > (tup)).name();
		} else {
			v = typeid(std::get < 1 > (tup)).name();
		}

		//does value type match location
		if (t.compare(v) == 0) {
			return std::get < 1 > (cont[loc]);
		} else {
			throw std::invalid_argument("Does not match requested type.");
		}
	}
};

template<typename T1, typename T2>
Types TwoTypes<T1, T2>::type(unsigned int loc) const {
	if (loc <= (cont.size() - 1) && cont.size() > 0) {
		auto tup = cont[loc];
		std::string loctype1 = typeid(std::get < 0 > (tup)).name();
		std::string loctype2 = typeid(std::get < 1 > (tup)).name();

		//return correct type by checking type flag and location value
		//against expected types.
		if (loctype1.compare(t1type) == 0 && std::get < 2 > (tup) == false) {
			return TYPE1;
		} else if (loctype2.compare(t2type) == 0
				&& std::get < 2 > (tup) == true) {
			return TYPE2;
		}
	} else {
		throw std::out_of_range("out of range.");
	}

};

template<typename T1, typename T2>
void TwoTypes<T1, T2>::set(unsigned int loc, const T1& v) {
	//Sets T1 value to 1st element in tuple and type flag
	//to false
	if (loc <= (cont.size() - 1) && cont.size() > 0) {
		cont.at(loc) = std::make_tuple(v, nullt2, false, true);
	} else {
		throw std::out_of_range("out of range");
	}
};

template<typename T1, typename T2>
void TwoTypes<T1, T2>::set(unsigned int loc, const T2& v) {
	//Sets T2 value to 1st element in tuple and type flag
	//to true
	if (loc <= (cont.size() - 1) && cont.size() > 0) {
		cont.at(loc) = std::make_tuple(nullt1, v, true, true);
	} else {
		throw std::out_of_range("out of range");
	}
};

template<typename T1, typename T2>
unsigned int TwoTypes<T1, T2>::size() const {
	return cont.size();
};

template<typename T1, typename T2>
bool TwoTypes<T1, T2>::empty() const {
	if (!(cont.empty())) {
		return false;
	} else {
		return true;
	}
};

template<typename T1, typename T2>
void TwoTypes<T1, T2>::clear() {
	cont.clear();
};


//Cast any T value to string via
//string stream.
template<typename T1, typename T2>
T1 lexical_cast(const T2& val) {
	std::stringstream stream;
	T1 retval;
	stream << val;
	stream >> retval;
	return retval;
};

template<typename T>
std::string to_str(const T& val) {
	return lexical_cast<std::string>(val);
};

template<typename U1, typename U2>
std::ostream& operator<<(std::ostream& o, const TwoTypes<U1, U2>& tt) {
	int c = 0;
	//each element in container
	for (int i = 0; i < tt.size(); ++i) {
		//get next tuple
		auto tup = tt.cont[i];

		//if tuple contains T1 object
		if (std::get < 2 > (tup) == false) {
			U1 t1val = std::get < 0 > (tup);

			//cast to string
			std::string valstr = to_str(t1val);

			//get length of string to set field width
			int len = strlen(valstr.c_str());
			if (i == (tt.size() - 1)) {
				o.width(len);
			} else {
				o.width(++len);
			}

			//align value string left of field
			o << std::left << valstr;
		} else { //is T2 object
			U2 t2val = std::get < 1 > (tup);

			//cast to string
			std::string valstr = to_str(t2val);

			//get length of string to set field width
			int len = strlen(valstr.c_str());
			if (i == (tt.size() - 1)) {
				o.width(len);
			} else {
				o.width(++len);
			}
			o << std::left << valstr;
		}
	}
	return o;
};

#endif /* TWOTYPES_H_ */

/*
 * TwoTypes.h
 *
 *  Created on: 22 Nov 2016
 *      Author: np183
 *      		Department of Informatics
 *      		University of Leicester
 */
