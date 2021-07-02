# TrieFreq
Typeahead suggestions enable users to search for known and frequently searched terms. As the user types into the search box, it tries to predict the query based on the characters the user has entered and gives a list of suggestions to complete the query. Typeahead suggestions help the user to articulate their search queries better. It’s not about speeding up the search process but rather about guiding the users and lending them a helping hand in constructing their search query. What data structure would be used for the same and how?
 
 
 solution 1: in src/trie folder 
 data structure used:ternary trie with max heap using priority queue.
 create two methods :
 1) insert 
 2) search based on prefix
 
 insert -> add character in trie along wiith freq at last end of word 
 search ->search the element along with freq every time we create max heap (max  priority queue).
 
 2.  Design an API Rate limiting system that monitors the number of requests per a window time(i.e. could be second/hour etc) a service agrees to allow. IF the number of requests exceeds the allowed limit the rate limiter should block all excess calls.
 
System should be designed considering the following:

  a)  Rate limiting should work for a distributed set up as the APIs are available through a group of API Gateways -> use redis and sorted set in redis along with sliding window counter  in this approch we will store timestamp of window in array but in counter window t is more than slindng window in which if we have two same time stamp like 11:29:30 and 11:29:30 ,so we will store in array like timestamp with counter.
  b) What database would be used and the rationale behind the choice 
  c) how would throttling be done  :using sliding window counter algorithm 
  d) the system should be highly available :redis
  
  
 solution 2: doc file at root directory .
  
  
  3.   How would you design a garbage collection system? The idea here is to design a system that recycles unused memory in the program. Here the key is to find which piece of memory is unused. What data structure would be chosen and how would it be used to ensure garbage collection does not halt the main process?

solution :3: src/customGarbageCollector
java Garbage collector

data structure : we used graph for containing the reference also using mark ans sweep algorithm.

Identification of unused references and finalization of object happens concurrently.

it is divided into three parts -

GC: This is the main entry point of the module and performs following operations 
- a) get(Object) : Add new object to the reference graph
- b) release(Object) : To indicate that object is no more required 
- c) gc() : Request to start the garbage collection

GCTask: This class is to traverse through graph and identify unused references. It also push collected objects(unused references) to finalize  queue, which is taken care by FinalizeTask

FinalizeTask: FinalizeTask concurrently processes(calls finalize) objects pushed to finalize queue.

Reference: Basic reference implementation is node of graph.

Use cases covered: avoiding cyclic references during traversal. works for both objects with or without finalize method. non blocking implementation of finalize.
