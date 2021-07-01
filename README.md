# TrieFreq
Typeahead suggestions enable users to search for known and frequently searched terms. As the user types into the search box, it tries to predict the query based on the characters the user has entered and gives a list of suggestions to complete the query. Typeahead suggestions help the user to articulate their search queries better. It’s not about speeding up the search process but rather about guiding the users and lending them a helping hand in constructing their search query. What data structure would be used for the same and how?
 
 
 solution 1: in src/trie folder 
 2.  Design an API Rate limiting system that monitors the number of requests per a window time(i.e. could be second/hour etc) a service agrees to allow. IF the number of requests exceeds the allowed limit the rate limiter should block all excess calls.
 
System should be designed considering the following:

  a)  Rate limiting should work for a distributed set up as the APIs are available through a group of API Gateways  
  b) What database would be used and the rationale behind the choice 
  c) how would throttling be done 
  d) the system should be highly available
  
  
 solution 2: doc file at root directory .
  
  
  3.   How would you design a garbage collection system? The idea here is to design a system that recycles unused memory in the program. Here the key is to find which piece of memory is unused. What data structure would be chosen and how would it be used to ensure garbage collection does not halt the main process?

solution :3: src/customGarbageCollector
