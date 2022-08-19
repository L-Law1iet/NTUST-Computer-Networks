# Computer-Networks-HW02
Iperf is a common tool used to measure network bandwidth. You will write your own version
of this tool using sockets. You will then use your tools to measure the performance of certain
networks. Your tool, called Iperfer, will send and receive TCP packets between a pair of hosts using
sockets.

When operating in client mode, Iperfer will send TCP packets to a specific host for a specified
time window and track how much data was sent during that time frame; it will calculate and display
the bandwidth based on how much data was sent in the elapsed time. When operating in server mode,
Iperfer will receive TCP packets and track how much data was received during the lifetime of a
connection; it will calculate and display the bandwidth based on how much data was received and
how much time elapsed between received the first and last byte of data