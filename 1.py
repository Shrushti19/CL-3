#Client

import xmlrpc.client

n = int(input("Enter an integer value: "))
proxy = xmlrpc.client.ServerProxy('http://localhost:8000/')
result = proxy.factorial(n)
print(f"The factorial of {n} is {result}")



#Server

import xmlrpc.server

def factorial(n):
    if n == 0:
        return 1
    else:
        return n * factorial(n-1)

server = xmlrpc.server.SimpleXMLRPCServer(('localhost', 8000))
server.register_function(factorial)
print("Server started on http://localhost:8000")
server.serve_forever()
