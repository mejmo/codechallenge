package com.develmagic.codechallenge.repository.treemap;

import com.develmagic.codechallenge.domain.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/31/16.
 */

public class TreemapStorage<K, T> {

    private Set<TreeNode> rootNodes = Collections.synchronizedSet(new HashSet<>());
    private ConcurrentHashMap<K, TreeNode<T>> idIndices = new ConcurrentHashMap<>();

    public void addSubNode(K parentId, K id, T t) {
        TreeNode<T> parent = idIndices.get(parentId);
        TreeNode<T> child = parent.addChild(t);
        idIndices.put(id, child);
    }

    public void addRootNode(K rootId, T t) {
        TreeNode<T> rootNode = new TreeNode<>(t);
        rootNodes.add(rootNode);
        idIndices.put(rootId, rootNode);
    }

    public boolean exists(Long id) {
        return idIndices.containsKey(id);
    }

    public T getNode(Long id) {
        TreeNode<T> node = idIndices.getOrDefault(id, null);
        return node != null ? node.getData() : null;
    }

    public Iterable<T> getAll() {
        return idIndices.values()
                .stream()
                .map(treenode -> treenode.getData())
                .collect(Collectors.toList());
    }

    public long size() {
        return idIndices.size();
    }

    protected void clear() {
        rootNodes.clear();
        idIndices.clear();
    }

    public void remove(Long id) {
        TreeNode<T> node = idIndices.get(id);
        if (node.getParent() != null)
            node.getParent().getChildren().remove(node);
        else
            rootNodes.remove(id);
        idIndices.remove(id);
    }


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
}
