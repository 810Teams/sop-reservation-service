const express = require('express')
const app = express()
const port = process.env.PORT | 3001
require('./database/db')
const shopRouter = require('./router/shop')
const cors = require('cors')

// const Eureka = require("eureka-js-client").Eureka;

// const eureka = new Eureka({
//   instance: {
//       app: 'shop-service',
//       instanceId: 'shop-service',
//       hostName: 'localhost',
//       ipAddr: '127.0.0.1',
//       port:  {
//           '$': 8080,
//           '@enabled': 'true',
//       },
//       vipAddress: 'shop-service',
//       statusPageUrl: 'https://shop-service.appspot.com',
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
app.use(cors());
app.use(shopRouter)

// Create server
app.listen(port, ()=>{
    console.log("Start on port " + port)
})