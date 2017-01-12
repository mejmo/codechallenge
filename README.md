# CodeChallenge

## Problem

This assignement was given on 1st round of Number26 Hiring process. Let's have some transaction tree and we must implement CRUD operations and one method from business logic - find a sum of the transactions which are located under the node in transaction tree. Simple.

## Solution 

Technologies used:
* spring boot, neo4j, spring data, Java8, SDN4
* Mockito, JUnit

Two approaches has been implemented.

1. Neo4j stores all transactions and has BELONGS_TO relationships between nodes
    * finding sum of children nodes is quite straightforward in Cypher language:
    `MATCH (root:Transaction)-[BELONGS_TO*]-(child) WHERE root.transactionId = {transactionId} WITH sum(child.amount)+root.amount AS total RETURN total` where transactionId is holding the input parameter for the sum query.
    * embedded Neo4J server is used with impermanent storage (if you intend to use your custom neo4j installation, adjust 
      `Neo4jConfiguration` bean)
2. To show also some data structure skills, I solve the problem with a custom multi-leaf tree as well which is indexed:
    * tree is holding two types of indices - `IdIndices` and `typeIndices`. This is due to the fact, that traversing the whole
      (sub)tree just in order to find type or id could be time consuming. Since there will be no update probably (in banking just `SELECT`, `INSERT` and no `DELETE`, `UPDATE`), we need not to cope with index updates.
    * sum for the given transaction is done by non-recursive traversal: 
```java

        public List<T> getAllChildrenNodes(K id) {

        TreeNode<T> current = idIndices.get(id);
        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(current);
        List<T> list = new ArrayList<>();

        while (!stack.empty()) {
            TreeNode<T> top = stack.pop();
            for (TreeNode<T> child : top.getChildren())
                stack.push(child);
            list.add(top.getData());
        }

        return list;
    }
```

* both implementations share as much code as possible, including service layer `TransactionService`. They implement just a different `TransactionRepository` interface.
 
## How to run
Two different start classes are provided. `Neo4jCodeChallengeApplication` and `TreemapCodeChallengeApplication`.

## Testing
The most important features are tested, including unit tests of the storage types and 2 integration tests (neo4j, treemap). Tests can be executed with maven `mvn test`. The solution is using **Mockito** for mocking of `TransactionStorage`. 

## Known limitations
* needs more documentation
* no logging

## License
The source code is copyrighted.
