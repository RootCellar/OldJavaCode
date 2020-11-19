To open the server on windows, launch server.bat. The server will open to the port 25565.
To have clients connect, they need to know the ip address and port, and need to put them
in client.bat as arguments so that they will connect to the server.

In client.bat, there is a line that looks like:

java Client <ip> <port>

If you are launching the client on the same machine as the server,
then you can use "localhost" as the ip. This will also allow you to 
connect to the server on the same machine while offline.

For the server you need:

java Server <port>

The port is set to 25565, but you can change it, and should be changed if you plan
on hosting more than 1 server, because you can't bind 2 servers to the same port.

To launch the server in bluej, type "<port number>" as the argument.
To launch the client in bluej, enter "<address>","<port>"