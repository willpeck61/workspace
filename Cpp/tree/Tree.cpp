/*
 * Tree.cpp
 *
 *  Created on: 20 Oct 2016
 *      Author: np183
 */

#include "Tree.h"

//#include <iostream>
//#include <utility>

typedef unsigned int uint;

using std::cout;
using std::endl;
using std::move;


Tree::Tree()
{
  node_ = nullptr;
  arr_ = nullptr;
  root = 0;
  node_count_ = 0;
}

Tree::Tree(const Tree& other)
{
  node_ = nullptr;
  arr_ = nullptr;
  node_count_ = 0;
  root = 0;
  copy(other.node_);
}

Tree::Tree(Tree&& other) : node_{other.node_},
    node_count_{other.node_count_}, arr_{other.arr_},
    root{other.root}
{
  other.arr_ = nullptr;
  other.node_ = nullptr;
  other.root = 0;
  other.node_count_ = 0;
}

Tree::~Tree()
{
  delete [] node_;
}

bool Tree::addToExisting(Node* node, int val)
{
  if (node->value == val) {
    return false;
  } else if (node->value > val) {
    if (nullptr == node->left_node) {
      node->left_node = new Node(val);
      cout << node->left_node->value << " added left " << endl;
      return true;
    } else {
      if (node->left_node->value == val) {
        return false;
      } else {
        addToExisting(node->left_node, val);
      }
    }
  } else {
    if (nullptr == node->right_node) {
      node->right_node = new Node(val);
      cout << node->right_node->value << " added right " << endl;
      return true;
    } else {
      if (node->right_node->value == val) {
        return false;
      } else {
        addToExisting(node->right_node, val);
      }
    }
  }
}

bool Tree::add(uint val)
{
  if (nullptr == node_) {
    node_ = new Node(val);
    node_count_++;
    root = val;
    cout << node_->value << " root " << endl;
  } else {
    node_count_++;
    return addToExisting(node_, val);
  }
}

bool Tree::nodeCrawler(Node* node, int val)
{
  if (nullptr == node) {
    return false;
  } else if (val < node->value) {
    if (node->left_node->value == val) {
      return true;
    } else {
      nodeCrawler(node->left_node, val);
    }
  } else {
    if (node->right_node->value == val) {
      return true;
    } else {
      nodeCrawler(node->right_node, val);
    }
  }
}

bool Tree::find(uint val)
{
  if (node_->value == val) {
    return true;
  } else if (nodeCrawler(node_, val)) {
    return true;
  } else {
    return false;
  }
}

uint Tree::size() const
{
  return node_count_;
}

Tree Tree::subtree(uint lb) const
{
  Tree sub;
  sub.node_ = new Node(node_, lb, false);
  sub.node_count_ = sub.node_->index;
  return sub;
}

Tree Tree::subtree(uint ub, bool overload) const
{
  Tree sub;
  sub.node_ = new Node(node_, ub, true);
  sub.node_count_ = sub.node_->index;
  return sub;
}

uint* Tree::preorder(uint& size, Node* node) const
{
  if (nullptr == arr_) {
    arr_ = new uint[node_count_];
    node = node_;
  }
  if (node) {
    arr_[size] = node->value;
    size++;
    preorder(size, node->left_node);
    preorder(size, node->right_node);
  }
  return arr_;
}

uint* Tree::inorder(uint& size, Node* node) const
{
  if (nullptr == arr_) {
    arr_ = new uint[node_count_];
    node = node_;
  }
  if (node) {
    inorder(size, node->left_node);
    arr_[size] = node->value;
    size++;
    inorder(size, node->right_node);
  }
  return arr_;
}

uint* Tree::postorder(uint& size, Node* node) const
{
  if (nullptr == arr_) {
    arr_ = new uint[node_count_];
    node = node_;
  }
  if (node) {
    postorder(size, node->left_node);
    postorder(size, node->right_node);
    arr_[size] = node->value;
    size++;
  }
  return arr_;
}

uint* Tree::to_array(uint& size, OrderType order) const
{
  if (order == PREORDER) {
      return preorder(size, node_);
    } else if (order == INORDER) {
      return inorder(size, node_);
    } else if (order == POSTORDER) {
      return postorder(size, node_);
    }
}

void Tree::copy(Node* other) {
  if (nullptr == other) {
    return;
  } else {
    node_count_++;
    node_ = new Node(other->value);
    if (!(nullptr == other->left_node)){
      copy(other->left_node);
    }
    if (!(nullptr == other->right_node)){
      copy(other->right_node);
    }
  }
}

Tree& Tree::operator=(const Tree& other)
{
  if (!(this == &other)) {
    delete [] node_;
    copy(other.node_);
  }
  return *this;
}

void Tree::moveNode(Node* other) {
  Node* temp_node = nullptr;
  if (nullptr == other) {
    return;
  } else {
    node_count_++;
    cout << "Move " << node_count_;
    temp_node = other;
    node_ = other;
    other = temp_node;
    if (!(nullptr == other->left_node)){
      temp_node = other->left_node;
      node_->left_node = other->left_node;
      other->left_node = temp_node;
      moveNode(other->left_node);
    }
    if (!(nullptr == other->right_node)){
      temp_node = other->right_node;
      node_->left_node = other->right_node;
      other->right_node = temp_node;
      moveNode(other->right_node);
    }
  }
}

Tree& Tree::operator=(Tree&& other)
{
  if (!(this == &other)) {
    delete [] node_;
    moveNode(other.node_);
  }
  return *this;
}
