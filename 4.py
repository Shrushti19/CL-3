class LoadBalancer:
    def __init__(self, servers):
        self.servers = servers
        self.current_server_index = 0

    def get_next_server(self):
        server = self.servers[self.current_server_index]
        self.current_server_index = (self.current_server_index + 1) % len(self.servers)
        return server

class Client:
    def __init__(self, name):
        self.name = name

    def send_request(self, server):
        print(f" {self.name} sends request to Server {server}")

# Prompt the user to enter the servers
servers = input("Enter server names (separated by commas): ").split(',')

# Create a load balancer with the list of servers
load_balancer = LoadBalancer(servers)

# Simulate requests from clients based on user input
num_clients = int(input("Enter the number of clients: "))
for i in range(num_clients):
    client_name = f'Client{i + 1}'
    client = Client(client_name)
    server = load_balancer.get_next_server()
    client.send_request(server)

	
