# Computer-Networks-HW01
In this assignment, you will develop a simple Web server that is capable of processing
only one request. Specifically, your Web server will (i) create a connection socket when
contacted by a client (browser); (ii) receive the HTTP request from this connection; (iii)
parse the request to determine the specific file being requested; (iv) get the requested
file from the server’s file system; (v) create an HTTP response message consisting of
the requested file preceded by header lines; and (vi) send the response over the TCP
connection to the requesting browser. If a browser requests a file that is not present in
your server, your server should return a “404 Not Found” error message.

Please create two webpages on the server. Each page has a link to the other page. Use
web browser to show one of the page. Click the link and the other page should be
retrieved and shown on the browser.