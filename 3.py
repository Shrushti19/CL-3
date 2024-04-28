import numpy as np

# Union of Fuzzy Sets
def fuzzy_union(set1, set2):
    return np.maximum(set1, set2)

# Intersection of Fuzzy Sets
def fuzzy_intersection(set1, set2):
    return np.minimum(set1, set2)

# Complement of a Fuzzy Set
def fuzzy_complement(set):
    return 1 - set

# Difference of Fuzzy Sets
def fuzzy_difference(set1, set2):
    return fuzzy_intersection(set1, fuzzy_complement(set2))

# Fuzzy Relations by Cartesian Product
def fuzzy_relation(cartesian_product, membership_function):
    return np.array([membership_function(pair[0], pair[1]) for pair in cartesian_product])

# Max-Min Composition of Fuzzy Relations
def max_min_composition(relation1, relation2):
    composition = np.zeros((len(relation1), len(relation2)))
    for i in range(len(relation1)):
        for j in range(len(relation2)):
            composition[i][j] = np.max(np.minimum(relation1[i], relation2[j]))
    return composition

# Example of membership function (just a simple example)
def membership_function(x, y):
    return min(x, y)

# Example usage:
set1 = np.array([0.2, 0.5, 0.8])
set2 = np.array([0.3, 0.6, 0.9])

# Union
print("Union:", fuzzy_union(set1, set2))

# Intersection
print("Intersection:", fuzzy_intersection(set1, set2))

# Complement
print("Complement of set1:", fuzzy_complement(set1))

# Difference
print("Difference of set1 and set2:", fuzzy_difference(set1, set2))

# Fuzzy Relations
cartesian_product = [(x, y) for x in set1 for y in set2]
relation = fuzzy_relation(cartesian_product, membership_function)
print("Fuzzy Relation:", relation)

# Max-Min Composition of Fuzzy Relations
relation1 = np.array([[0.2, 0.5], [0.4, 0.7]])
relation2 = np.array([[0.3, 0.6], [0.1, 0.8]])
composition = max_min_composition(relation1, relation2)
print("Max-Min Composition:", composition)
