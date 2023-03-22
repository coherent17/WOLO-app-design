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
        num_bbox = random.randint(1,6)
        for i in range(num_bbox):
            idx = random.randint(0,len(names) - 1)
            msg = names[idx] + '\r'
            conn.send(msg.encode())
            print(msg)
            time.sleep(0.001)
        time.sleep(2)   #the image display time for 1 frame
        conn.send("finish\r".encode())
        print("----------")

s.close()