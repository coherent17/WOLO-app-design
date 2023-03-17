import socket
import time
import random

HOST = '0.0.0.0'
PORT = 7000

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((HOST, PORT))
s.listen(5)

print('server start at: %s:%s' % (HOST, PORT))
print('wait for connection...')


# class names
names =  [ 'pedestrian', 'car', 'motorcycle', 'bicycle', 'bus', 'truck', 'emergency', 'trailer', 'barrier', 'trafficcone']


while True:
    conn, addr = s.accept()
    print('connected by ' + str(addr))

    while True:
        idx = random.randint(0,len(names) - 1)
        msg = names[idx] + '\r'
        conn.send(msg.encode())
        time.sleep(0.1)



s.close()