import socket
import time

HOST = '0.0.0.0'
PORT = 7000

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((HOST, PORT))
s.listen(5)

print('server start at: %s:%s' % (HOST, PORT))
print('wait for connection...')

while True:
    conn, addr = s.accept()
    print('connected by ' + str(addr))

    while True:
        msg = 'test'
        conn.send(msg.encode())
        time.sleep(0.1)


s.close()