var SerialPort = require("serialport");
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var inString1;
var inString2;
var inString3;
var inString4;
var sum = 0.00;
var i = 0;

var portName = process.argv[2],
portConfig = {
	baudRate: 9600,
	parser: SerialPort.parsers.readline("\n")
};

var sp;

sp = new SerialPort.SerialPort(portName, portConfig);

app.get('/', function(req, res){
  res.sendfile('index.html');
});

io.on('connection', function(socket){
  console.log('a user connected');
  socket.on('disconnect', function(){
  });
  socket.on('chat message', function(msg){
    io.emit('chat message', msg);
    sp.write(msg + "\n");
  });
});

http.listen(3000, function(){
  console.log('listening on *:3000');
});

sp.on("open", function () {
  console.log('open');
  sp.on('data', function(data) {
    console.log('data received: ' + data);
		inString1 = data.slice(7);
		sum += (parseInt)(inString1);
    while(i==4){
      io.emit("chat message", "Temperature: " + data);
				sum = sum / 4;
				io.emit("chat message", "Average Temperature: " + String(sum));
				i=0;
		    sum = 0.00;
    }
    i++;


    /*
    io.emit("chat message", "Temperature: " + data);
    inString1 = data.slice(7);

      sum += parseInt(inString1);
      i++;
      if(i==1){
         io.emit("chat message", "Average Temperature: " + inString1);
         i=0;
      }
      */

  });
});
