/*
 * Tree.h
 */

#ifndef Tree_H_
#define Tree_H_

#include <iostream>
#include <utility>
using std::cout;
using std::endl;

enum OrderType { PREORDER, INORDER, POSTORDER };

// In this exercise you will create a binary tree data structure.
// The tree contains unsigned integers and enables (relatively) fast
// search and addition. 
// In order to enable fast search the insertion policy is very strict:
// 1. Values do not move in the tree after they are inserted
// 2. For every node all nodes in the left subtree are smaller than
//    the value of the node
// 3. For every node all nodes in the right subtree are larger than
//    the value of the node
//
// You are asked to implement a binary tree with some access and
// manipulation functions. 

class Tree {
public:
	// Build an empty Tree
	Tree();

	// Copy and move constructors
	Tree(const Tree&);
	Tree(Tree&&);

	// Destructor
	~Tree();

	//
  struct Node {

    int value;
    int index;
    Node* left_node;
    Node* right_node;

    Node(int val) {
      value = val;
      index++;
      left_node = nullptr;
      right_node = nullptr;
    }

    void makeSubtree(Node* other, const int val, bool upper) {
      if ((!(upper)) && (!(nullptr == other))) {
        if (val >= other->value) {
          value = other->value;
          index++;
          left_node = other->left_node;
          makeSubtree(other->left_node, val, false);
          right_node = other->right_node;
        }
        if (val <= other->value) {
          makeSubtree(other->left_node, val, false);
        }
        makeSubtree(other->right_node, val, false);
      }
      if (upper && (!(nullptr == other))){
        if (val <= other->value) {
          value = other->value;
          index++;
          left_node = other->left_node;
          makeSubtree(other->left_node, val, true);
          right_node = other->right_node;

        }
        if (val >= other->value) {
          makeSubtree(other->left_node, val, true);
        }
        makeSubtree(other->right_node, val, true);
      }
    }

    Node(Node* other, int val, bool upper) {
      if (!(nullptr == other)) {
        value = val;
        makeSubtree(other, val, upper);
      } else {
        left_node = nullptr;
        right_node = nullptr;
      }
    }
  };

  //Helper functions to enable tree traversal.
  uint* preorder(uint& size, Node* node) const;
  uint* inorder(uint& size, Node* node) const;
  uint* postorder(uint& size, Node* node) const;

  //Function to enable deep copy tree.
  void copy(Node* other);

  //Function to enable move tree.
  void moveNode(Node* other);

  //Helper function for add() to enable adding elements
  //the tree.
	bool addToExisting(Node* node, int val);

	//Helper function for find() to enable finding
	//elements in the tree.
	bool nodeCrawler(Node* node, int val);

	// Add the value to the collection.
	// If the value already exists in the collection the
	// collection does not change and return false
	// If the value does not exist add it to the collection
	// maintaining the rules about the structure of the tree.
	bool add(unsigned int val);

	// Check if a certain value exists in the tree
	bool find(unsigned int val);

	// How many elements are in the tree
	unsigned int size() const;

	// Create a deep copy of the subtree rooted
	// at the least element above the lowerbound
	Tree subtree(unsigned int lowerbound) const;

	// Create a deep copy of the subtree rooted at
	// the greatest element below the upperbound
	Tree subtree(unsigned int upperbound, bool overload) const;

	// Retrun a dynamic memory array holding all the values in
	// the tree according to:
	// PREORDER - an element in the tree appears before all the
	//            elements appearing below it in the tree.
	// INORDER - an element appears after all the elements in its
	//           left subtree (that are smaller than it) and before
	//           all the elements in its right subtree (that are
	//           larger than it).
	// POSTORDER - an element in the tree appears after all the
	//             elements appearing below it in the tree
	//
	// The reference to size is updated with the length of the array
	unsigned int* to_array(unsigned int& size, OrderType order) const;

	// Copy and move assignment operators
	Tree& operator=(const Tree&);
	Tree& operator=(Tree&&);

private:
	// TODO: Implement me
	// Add private data and function members (also public function
	// members are allowed)
	Node* node_;
	mutable uint* arr_;
	int root;
	mutable int node_count_;
};

#endif /* TREE_H_ */
