import numpy as np
import random

def distance(city1, city2):
    return np.linalg.norm(city1 - city2) 

num_cities = 4
num_ants = 4
evaporation_rate = 0.05
alpha = 1
beta = 1

cities = np.random.rand(num_cities, 2)
# cities = [[1,2], [3,4], [5,6], [7,8]]

distances = []
# distances = [[0, 2, 3, 4],      #0
#              [2, 0, 3, 4],      #1
#              [3, 1, 0, 4],      #2
#              [1, 2, 3, 0]]      #3
for city1 in cities:
    temp = []
    for city2 in cities: 
        temp.append(distance(city1, city2))
    distances.append(temp)

phermones = np.random.rand(num_cities, num_cities)
# phermones = [[0, 2, 3, 4],
#              [2, 0, 3, 4],
#              [3, 1, 0, 4],
#              [1, 2, 3, 0]]

tour_lengths = []
tour_paths = []

for _ in range(num_ants):
    randomly_selected_city = random.randint(0, num_cities-1)
    selected_city = randomly_selected_city
    tour_length = 0
    tour_path = []
    for j in range(num_cities): 
        tour_path.append(selected_city)
        dist = distances[selected_city]
        phero = phermones[selected_city]
        stochastic_gradient_descent = []
        for i in range(len(dist)):
            if dist[i] != 0: stochastic_gradient_descent.append(((phero[i])**alpha)*((1/dist[i])**beta))
            else: stochastic_gradient_descent.append(0)
        
        total_eta = sum(stochastic_gradient_descent)
        for i in range(len(stochastic_gradient_descent)):
            stochastic_gradient_descent[i]= stochastic_gradient_descent[i] / total_eta
        
        # eta = [1, 2, 3, 4] solution_set = [1, 3, 6, 10]
        solution_set, temp = [], 0
        for i in range(len(stochastic_gradient_descent)):
            temp += stochastic_gradient_descent[i]
            solution_set.append(temp)

        random_value = random.random()    # 0 to 1
        for i in range(len(solution_set)): 
            if random_value <= solution_set[i]:
                if i not in tour_path:
                    selected_city = i
                    break
        tour_length += dist[selected_city]

        for i in range(len(phero)): 
            phero[i] = (1 - evaporation_rate)*phero[i]
        phermones[selected_city] = phero
        
    tour_length += distances[randomly_selected_city][selected_city]
    tour_path.append(randomly_selected_city)   

    tour_lengths.append(tour_length)
    tour_paths.append(tour_path)

minimum_index = tour_lengths.index(min(tour_lengths))
print("Shortest Distance: ", tour_lengths[minimum_index])
print("Shortest Path: ",  tour_paths[minimum_index])
