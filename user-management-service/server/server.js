const express = require("express");
const app = express();
const port = process.env.PORT | 8081;
require("./database/db");
const userRouter = require("./router/user");


// // --- Eureka config ---
// const Eureka = require("eureka-js-client").Eureka;

// const eureka = new Eureka({
//   instance: {
//       app: 'user-management-service',
//       instanceId: 'user-management-service',
//       hostName: 'localhost',
//       ipAddr: '127.0.0.1',
//       port:  {
//           '$': 8081,
//           '@enabled': 'true',
//       },
//       vipAddress: 'user-management-service',
//       statusPageUrl: 'http://localhost:8081',
//       dataCenterInfo:  {
//           '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
//           name: 'MyOwn',
//       },
//       registerWithEureka: true,
//       fetchRegistry: true
//   },
//   eureka: {
//       host: 'eureka-server-258207.appspot.com',
//       port: 80,
//       servicePath: '/eureka/apps/'
//   }
// });
// eureka.logger.level('debug');
// eureka.start(function(error){
//   console.log(error || 'complete');
// });

// Router
app.use(express.json());
app.use(userRouter);

// Create server
app.listen(port, () => {
  console.log("Start on port " + port);
});